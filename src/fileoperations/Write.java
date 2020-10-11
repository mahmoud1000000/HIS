package src.fileoperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.UUID;
import src.input.CmdInput;
import src.management.NetworkManage;
import src.management.StoreManage;
import src.models.Doctor;
import src.models.MedicalHistory;
import src.models.Medication;
import src.models.Patient;
import src.models.PersonMH;
import src.models.Prescription;
import src.models.SystemMedication;
import src.models.TreatmentData;
import src.output.Prompt;
import src.system.System1;
import src.validation.ValidInput;
import src.management.*;

public class Write {
    private static final String slash = File.separator;
    private static CmdInput input = new CmdInput();

    private static UUID genretaUuid() {
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (System1.Ids.contains(id));
        System1.Ids.add(id);
        return id;
    }

    private static boolean createFolder(String path, String name) {
        File Folder = new File(path + slash + name);
        return Folder.mkdir();
    }

    private static boolean creatFile(String path, String name) {
        boolean result = false;
        try {
            File file = new File(path + slash + name);
            result = file.createNewFile();
        } catch (Exception e) {
            System.out.println("Exception has been occured !!");
        }
        return result;
    }

    private static boolean writeInFile(String filePath, String strToWrite) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
            bw.write(strToWrite);
            bw.flush();
            bw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void updateStorageFile(String path, String[] medData) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            String line = br.readLine();
            String[] linearray;
            String stream = "";
            int q = 0;
            while (line != null) {
                linearray = Read.readCSVLine(line);
                if (line.contains(medData[1])) {
                    q = Integer.parseInt(linearray[2]);
                    q += Integer.parseInt(medData[2]);
                    linearray[2] = String.valueOf(q);
                    stream += (arrToCSV(linearray) + System.lineSeparator());
                } else {
                    stream += (line + System.lineSeparator());
                }
            }
            bw.write(stream);
        } catch (Exception e) {
            System.out.println("Exception has been occured");
        }
    }

    private static String arrToCSV(String[] arr) {
        String CSVLine = "";
        for (String str : arr) {
            if (str.contains(","))
                CSVLine += ",\"" + str + "\"";
            else
                CSVLine += "," + str;
        }
        CSVLine = CSVLine.substring(1);
        return CSVLine;
    }

    public static Patient addNewPatient() throws Exception {
        String[] patientData = input.getPatientInput(), ICUData = ICUManage.getEmptyBed();
        String patientDataPath = "." + slash + "data" + slash + "patientsdata";
        patientData[0] = genretaUuid().toString();
        Patient patient = new Patient(patientData);
        addMedicalHistory(patient);
        addMedicalStatus(patient);
        createFolder(patientDataPath, patient.getId().toString());
        writeInFile(patientDataPath, arrToCSV(patientData));
        NetworkManage.addPatient(patient);
        return null;
    }

    public static void addMedicalHistory(Patient patient) throws Exception {

    }

    public static void addPersonMH(MedicalHistory medicalHistory) {

    }

    public static void addFamilyMH(MedicalHistory medicalHistory) {

    }

    public static void addPmhMap(PersonMH personMH) {
    }

    public static void addMedicalStatus(Patient patient) {

    }

    public static Doctor addNewDoctor() throws Exception {
        return null;
    }

    public static void addSystemMedication() {
        String path = "." + slash + "data" + slash + "storage" + slash + "medcationStorage.csv";
        String[] medData = input.getSysMedInput();
        if (ValidInput.isExistSysMed(medData[1])) {
            updateExistedSysMed(path, medData);
        } else {
            addNewSysMed(path, medData);
        }
    }

    public static TreatmentData addNewTreatmentData() {
        String[] info = input.getTdinfo();
        Patient patient = (Patient) NetworkManage.PersonsNames.get(info[0]);
        Doctor doctor = (Doctor) NetworkManage.PersonsNames.get(info[1]);
        String path = "." + slash + "data" + slash + "treatmentdata";
        String tdFolderPath;
        if (NetworkManage.hasTreatmentData(patient, doctor)) {
            Prompt.hasRelationship(patient, doctor);
            tdFolderPath = path + patient.getId().toString() + "_" + doctor.getId().toString();
           try{
            if (CmdInput.getchoose()){
                String tdpath = tdFolderPath + slash + "td.csv";
                









            }
        }catch(Exception e){
           System.out.println("Exception has been occured !!"); 
        }
        }

        return null;
    }

    public static void addTDFile(UUID patientID, UUID doctorID, double WTH) {
    }

    public static Prescription addNewPrescription() {
        return null;
    }

    public static List<Medication> addPrMedications() {
        return null;
    }

    private static void updateExistedSysMed(String path, String[] medData) {
        Prompt.addedExistedSysMed();
        StoreManage.medicationStorage.get(medData[1]).addQuantity(medData[2]);
        updateStorageFile(path, medData);
    }

    private static void addNewSysMed(String path, String[] medData) {
        UUID id = genretaUuid();
        medData[0] = id.toString();
        SystemMedication newmed = new SystemMedication(medData);
        StoreManage.medicationStorage.put(newmed.getName(), newmed);
        StoreManage.storageid.add(id);
        writeInFile(path, arrToCSV(medData));
    }
}
