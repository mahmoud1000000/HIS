package src.input;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import src.output.Prompt;

public class CmdInput implements Input {
    private static final InputStreamReader r = new InputStreamReader(System.in);
    private static final BufferedReader br = new BufferedReader(r);
    @Override
    public String[] getPatientInput() {
        Prompt.showTitle("Patient Data");
        String[] patientData = new String[7];
        String[] patientDataStrs = {"Name","Address","Phone","Birth Date","Sex","Complain"};
        try{
            for(int i = 0; i < 6; i++){
                Prompt.askToInput("Patient "+patientDataStrs[i]);
                patientData[i+1] = br.readLine();
            }
        }
        catch(Exception e){
        }
        Prompt.printDashline();
        return patientData;
    }
    @Override
    public String[] getMsInput(){ return null; }
    @Override
    public String getCRDisMap(){ return null; }
    @Override
    public String getDisMap(){ return null; }
    @Override
    public String getHospMap(){ return null; }
    @Override
    public String getMedMap(){ return null; }
    @Override
    public String[] getDoctorInput(){ return null; }
    @Override
    public String[] getMedInput(){ return null; }
    @Override
    public String[] getPRInput(){ return null; }
    private String getStringDate() { return ""; }
    private String getmed() { return ""; }
    private String getHosp() { return ""; }
    private String getDisease() { return ""; }
    private String getCRDisease() { return ""; }

    public String[] getSysMedInput() {
        String[] medData = new String[3];
        try {
            System.out.print("Please,Enter the drug name \"First Character of each word must be CAPITAL !!\" : ");
            medData[1] = br.readLine();
            System.out.print("Please,Enter drug quantity : ");
            medData[2] = br.readLine();
        } catch (Exception e) {
            System.out.println("exception has been occured");
        }
        return medData;
    }
        
}

