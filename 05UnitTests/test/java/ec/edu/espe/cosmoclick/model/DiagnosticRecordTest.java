
package ec.edu.espe.cosmoclick.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Martin Onate <Team 6, espe>
 */
public class DiagnosticRecordTest {
    
    /**
     * Test of createRecord method, of class DiagnosticRecord.
     */
    @Test
    public void testCreateRecord() {
        System.out.println("createRecord");
        PatientHistory history = null;
        DiagnosticRecord instance = new DiagnosticRecord();
        instance.createRecord(history);
    }

    @Test
    public void testCreateRecordCorreclty() {
        System.out.println("Test create record with the correct information");
        PatientHistory history = new PatientHistory();
        DiagnosticRecord instance = new DiagnosticRecord();
        
        instance.createRecord(history);
        
        assertEquals(history, instance.getHistory());
        assertTrue(history.getRecords().contains(instance));
    }

    @Test
    public void testCreateRecordMultiple() {
        System.out.println("Test create a record multiple");
        PatientHistory history = new PatientHistory();
        DiagnosticRecord record1 = new DiagnosticRecord();
        DiagnosticRecord record2 = new DiagnosticRecord();
        
        record1.createRecord(history);
        record2.createRecord(history);
        
        assertEquals(2, history.getRecords().size());
        assertTrue(history.getRecords().contains(record2));
    }

    @Test
    public void testCreateRecordReassign(){
        System.out.println("Test create a record with reassign");
        PatientHistory history1 = new PatientHistory();
        PatientHistory history2 = new PatientHistory();
        DiagnosticRecord instance = new DiagnosticRecord();
        
        instance.createRecord(history1);
        instance.createRecord(history2);
        
        assertEquals(history2, instance.getHistory());
        assertTrue(history2.getRecords().contains(instance));
    }

    @Test
    public void testCreateRecordNullHistory() {
        System.out.println("Test create a record with null history");
        DiagnosticRecord instance = new DiagnosticRecord();
        
        assertNotNull(instance.getHistory(), "Error de negocio: Se intentó crear un registro con un historial nulo.");
    }
    
    
    
    
    
    /**
     * Test of updateRecord method, of class DiagnosticRecord.
     */
    @Test
    public void testUpdateRecord() {
        System.out.println("updateRecord");
        String skinType = "";
        String healthHistory = "";
        String observations = "";
        DiagnosticRecord instance = new DiagnosticRecord();
        instance.updateRecord(skinType, healthHistory, observations);
    }
    
    @Test
    public void testUpdateRecordSuccess() {
        System.out.println("Test update record correctly");
        DiagnosticRecord instance = new DiagnosticRecord(1, "Grasa", "Ninguno", "Normal", null);
        
        instance.updateRecord("Seca", "Alergias", "Requiere hidratación");
        
        assertEquals("Seca", instance.getSkinType());
        assertEquals("Alergias", instance.getHealthHistory());
        assertEquals("Requiere hidratación", instance.getObservations());
    }

    @Test
    public void testUpdateRecordNewInstance() {
        System.out.println("Test updateRecord new instance");
        DiagnosticRecord instance = new DiagnosticRecord();
        
        instance.updateRecord("Mixta", "Sin antecedentes", "Ninguna");
        
        assertEquals("Mixta", instance.getSkinType());
        assertEquals("Sin antecedentes", instance.getHealthHistory());
    }

    @Test
    public void testUpdateRecordPartial() {
        System.out.println("Test update record partial update");
        DiagnosticRecord instance = new DiagnosticRecord(1, "Grasa", "Ninguno", "Normal", null);
        
        instance.updateRecord("Sensible", instance.getHealthHistory(), instance.getObservations());
        
        assertEquals("Sensible", instance.getSkinType());
    }

    @Test
    public void testUpdateRecordEmptySkinType() {
        System.out.println("Test update when record a empty skin type");
        DiagnosticRecord instance = new DiagnosticRecord(1, "Grasa", "Ninguno", "Normal", null);
        
        instance.updateRecord("", "Ninguno", "Observación");
        
        assertFalse(instance.getSkinType().isEmpty(), "Error de negocio: Se permitió actualizar el registro con un tipo de piel vacío.");
    }
    
    
    
    
       
    /**
     * Test of viewHistory method, of class DiagnosticRecord.
     */
    @Test
    public void testViewHistory() {
        System.out.println("viewHistory");
        DiagnosticRecord instance = new DiagnosticRecord();
        PatientHistory expResult = null;
        PatientHistory result = instance.viewHistory();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testViewHistorySuccess() {
        System.out.println("Test view history when it works successfully");
        PatientHistory history = new PatientHistory();
        DiagnosticRecord instance = new DiagnosticRecord(1, "Grasa", "Ninguno", "Normal", history);
        
        PatientHistory result = instance.viewHistory();
        
        assertEquals(history, result);
    }

    @Test
    public void testViewHistoryNull() {
        System.out.println("Test view when history is null");
        DiagnosticRecord instance = new DiagnosticRecord();
        
        PatientHistory result = instance.viewHistory();
        
        assertNull(result);
    }

    @Test
    public void testViewHistoryUpdate() {
        System.out.println("Test view history updated");
        PatientHistory history1 = new PatientHistory();
        PatientHistory history2 = new PatientHistory();
        DiagnosticRecord instance = new DiagnosticRecord();
        
        instance.createRecord(history1);
        instance.setPatientHistory(history2);
        
        PatientHistory result = instance.viewHistory();
        
        assertEquals(history2, result);
    }

    @Test
    public void testViewHistoryMandatoryNotNull() {
        System.out.println("Test view history mandatory not null");
        DiagnosticRecord instance = new DiagnosticRecord();
        
        PatientHistory result = instance.viewHistory();
        
        assertNotNull(result, "Error de negocio: El registro no cuenta con un historial asociado.");
    }
    
}
