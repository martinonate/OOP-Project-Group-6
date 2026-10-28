package ec.edu.espe.cosmoclick.model;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class DiagnosticRecord {
    
    private int id;
    private String skinType;
    private String healthHistory;
    private String observations;
    private PatientHistory history;

   
    public DiagnosticRecord() {
    }

    public DiagnosticRecord(int id, String skinType, String healthHistory, String observations, PatientHistory history) {
        this.id = id;
        this.skinType = skinType;
        this.healthHistory = healthHistory;
        this.observations = observations;
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkinType() {
        return skinType;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public String getHealthHistory() {
        return healthHistory;
    }

    public void setHealthHistory(String healthHistory) {
        this.healthHistory = healthHistory;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public PatientHistory getHistory() {
        return history;
    }

    public void setPatientHistory(PatientHistory history) {
        this.history = history;
    }


    public void createRecord(PatientHistory history) {
        this.history = history;
        history.getRecords().add(this);
    }

    public void updateRecord(String skinType, String healthHistory, String observations) {
        this.skinType = skinType;
        this.healthHistory = healthHistory;
        this.observations = observations;
    }

    public PatientHistory viewHistory() {
        return history;
    }

    
}
