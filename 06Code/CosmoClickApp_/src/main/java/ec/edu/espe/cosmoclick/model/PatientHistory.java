package ec.edu.espe.cosmoclick.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class PatientHistory {
    
    private int id;
    private int age;
    private String patientName;
    private String skinType;
    private String treatmentHistory;
    private Calendar recordDate;
    private String allergies;
    private String observations;
    private Customer customer;
    private ArrayList<DiagnosticRecord> records;

    public PatientHistory() {
        this.records = new ArrayList<>();
    }

    public PatientHistory(int id, int age, String patientName, String skinType, String treatmentHistory, Calendar recordDate, String allergies, String observations, Customer customer) {
        this.id = id;
        this.age = age;
        this.patientName = patientName;
        this.skinType = skinType;
        this.treatmentHistory = treatmentHistory;
        this.recordDate = recordDate;
        this.allergies = allergies;
        this.observations = observations;
        this.customer = customer;
        this.records = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSkinType() {
        return skinType;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public String getTreatmentHistory() {
        return treatmentHistory;
    }

    public void setTreatmentHistory(String treatmentHistory) {
        this.treatmentHistory = treatmentHistory;
    }

    public Calendar getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Calendar recordDate) {
        this.recordDate = recordDate;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<DiagnosticRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<DiagnosticRecord> records) {
        this.records = records;
    }
    
    public void registerPatientHistory(int age, String skinType, String allergies,String treatmentHistory, String observations) {
        this.age = age;
        this.skinType = skinType;
        this.allergies = allergies;
        this.treatmentHistory = treatmentHistory;
        this.observations = observations;
        this.recordDate = Calendar.getInstance();
    }
    
    public void viewPatientHistory(){
        System.out.println("");
        System.out.println("===== Historial de " + patientName + " =====");
        System.out.println("Edad: " + age);
        System.out.println("Tipo de piel: " + skinType);
        System.out.println("Alergias: " + allergies);
        System.out.println("Tratamientos previos: " + treatmentHistory);
        System.out.println("Observaciones: " + observations);
        System.out.println("Registros de diagnostico: " + records.size());
        for (int i = 0; i < records.size(); i++) {
            DiagnosticRecord r = records.get(i);
            System.out.println("");
            System.out.println("    Registro " + r.getId());
            System.out.println("    Tipo de piel: " + r.getSkinType());
            System.out.println("    Antecedentes de salud: " + r.getHealthHistory());
            System.out.println("    Observaciones: " + r.getObservations());
        }
    }
}
