import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable; 


@Named
@ViewScoped
public class PublisherController implements Serializable {
	
	@Inject
	Co2EmissionDAO co2EmissionDAO;
	
	public void approve(Co2Emission emission) {
	    if (emission == null) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Error releasing emission: Emission not found.", "Error"));
	     // Beenden der Methode, wenn das CO2Emission-Objekt null ist
	        return;  
	    }

	    try {
	        // Synchronisation mit der Datenbank zur Verifikation der aktuellen CO2Emission
	        Co2Emission existingEmission = co2EmissionDAO.findById(emission.getId());
	        if (existingEmission != null) {
	        	// Freigabe: approved=true
	            existingEmission.setApproved(true);

	            // Methode zur Persistierung der aktualisierten Emission
	            co2EmissionDAO.update(existingEmission);
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Emission released successfully.", "Success"));
	        } else {
	        	// Fehlermeldung, wenn die Emission in der Datenbank nicht gefunden wird
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                    "Error releasing emission: Emission not found.", "Error"));
	        }
	        
	    } catch (Exception e) {
	    	// Fehlermeldung und Stack Trace bei einer Exception (wenn Persistierung nicht erfolreich war)
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Error updating emission: " + e.getMessage(), "Error"));
	        e.printStackTrace();
	    }
	    
	}
}