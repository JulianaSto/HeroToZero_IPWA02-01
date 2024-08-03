import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@Named
@ViewScoped
public class ScientistController implements Serializable {

	@Inject
	CountryDAO countryDAO;

	@Inject
	Co2EmissionDAO co2EmissionDAO;

	private String countryToBeChanged;
	private int newEmissionYear;
	private float newEmissionValue;

	List<Country> countryList = new ArrayList<>();

	public ScientistController() {

	}

	public String getCountryToBeChanged() {
		return countryToBeChanged;
	}

	public void setCountryToBeChanged(String countryToBeChanged) { 
		this.countryToBeChanged = countryToBeChanged;
	}

	public int getNewEmissionYear() {
		return newEmissionYear;
	}

	public void setNewEmissionYear(int newEmissionYear) {
		this.newEmissionYear = newEmissionYear;
	}

	public float getNewEmissionValue() {
		return newEmissionValue;
	}

	public void setNewEmissionValue(float newEmissionValue) {
		this.newEmissionValue = newEmissionValue;
	}

	public List<Country> getAktuelleCountry() {
		return this.countryList;
	}

	public void addEmission() {
		if (!countryList.isEmpty()) {
			Country country = countryList.get(0);
			Co2Emission newEmission = new Co2Emission(newEmissionYear, newEmissionValue);
			newEmission.setApproved(false);

			newEmission.setCountry(country); // Setzen des Landes für die neue Emission
			country.addCo2Emission(newEmission); // Hinzufügen der Emission zum Land

			FacesContext context = FacesContext.getCurrentInstance();
			try {
				// Aufruf der Methode zur Persistierung
				co2EmissionDAO.save(newEmission);

				// Entfernen der letzten Emission um Duplikate in der Session zu vermeiden
				country.getCo2Emissions().remove(newEmission);

				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Emission added successfully.", "Success"));

				// Aktualisierung der Daten für die Tabelle
				this.searchCountry();
			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to add emission.",
						"Error: " + e.getMessage()));
			}
		}
		searchCountry(); // Aktualisierung der Daten für die Tabelle
	}
	
	public void changeEmission(Co2Emission emission) {
	    if (emission == null) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Error updating emission: Emission not found.", "Error"));
	     // Beenden der Methode, wenn das Co2Emission-Objekt null ist
	        return;  
	    }

	    try {
	        // Synchronisation mit der Datenbank zur Verifikation der aktuellen Co2Emission
	        Co2Emission existingEmission = co2EmissionDAO.findById(emission.getId());
	        if (existingEmission != null) {
	        	// Aktualisierung der Attributwerte
	            existingEmission.setYear(emission.getYear());
	            existingEmission.setEmission(emission.getEmission());
	            existingEmission.setApproved(false);

	            // Methode zur Persistierung der aktualisierten Emission
	            co2EmissionDAO.update(existingEmission);
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Emission updated successfully.", "Success"));
	        } else {
	        	// Fehlermeldung, wenn die Emission in der Datenbank nicht gefunden wird
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                    "Error updating emission: Emission not found.", "Error"));
	        }
	        
	    } catch (Exception e) {
	    	// Fehlermeldung und Stack Trace bei einer Exception (wenn Persistierung nicht erfolreich war)
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Error updating emission: " + e.getMessage(), "Error"));
	        e.printStackTrace();
	    }
	    
	    searchCountry(); // Aktualisierung der Tabelle
	}

	public void loadCountry() {
		this.newEmissionYear = 0; // Löschen der Benutzereingaben beim Laden eines neuen Landes.
		this.newEmissionValue = 0;
		searchCountry();
	}

	public void searchCountry() {
		this.countryList = countryDAO.getCountry(countryToBeChanged); 
		if (countryList == null || countryList.isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No countries found for the specified criteria.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

}
