import jakarta.faces.application.FacesMessage;

import jakarta.faces.application.NavigationHandler;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable; 
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.io.IOException;


@Named
@ViewScoped
public class PublisherController implements Serializable {

	public PublisherController() {

	}

	@Inject
	CountryDAO countryDAO;

	@Inject
	Co2EmissionDAO co2EmissionDAO;
	

	private String countryToBeChanged;
	private int newEmissionYear;
	private float newEmissionValue;


	List<Country> countryList = new ArrayList<>();

	public void searchCountry() {
		this.countryList = countryDAO.getCountry(countryToBeChanged); // Zugriff auf das Country-Objekt. 
	    if (countryList == null || countryList.isEmpty()) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	                                            "No countries found for the specified criteria.", 
	                                            null);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	}

	public List<Country> getAktuelleCountry() { // nur für Testausgabe
		return this.countryList;

	}

	public String getCountryToBeChanged() {
		return countryToBeChanged;
	}

	public void setCountryToBeChanged(String countryToBeChanged) { // setzen des Landnamens aus der Benutzereingabe
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

	public void addEmission() {
		if (!countryList.isEmpty()) {
			Country country = countryList.get(0); // Assuming we want to add the emission to the first country in the
													// list
			Co2Emission newEmission = new Co2Emission(newEmissionYear, newEmissionValue);
			newEmission.setApproved(false);
			
			newEmission.setCountry(country); // Set the country for the new emission
			country.addCo2Emission(newEmission); // Add the new emission to the country

			co2EmissionDAO.save(newEmission); // Save the new emission

			country.getCo2Emissionen().remove(newEmission);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Emission added successfully.", "Success"));

			// Refresh the country list to reflect the new emission
			this.searchCountry();

		}
	}

	/*public void searchCountryAddEmission() {		//Rausgenommen, Nutzer muss immer zuerst Ländernamen oben eingeben, bevor Änderungen möglich sind
		this.searchCountry();
		this.addEmission();
	}*/
	
    public void changeEmission(Co2Emission emission) {
        try {
            if (emission != null) {
                // Stellen Sie sicher, dass die Emission korrekt geladen wird
                Co2Emission existingEmission = co2EmissionDAO.findById(emission.getId());
                if (existingEmission != null) {
                    existingEmission.setYear(emission.getYear());
                    existingEmission.setEmission(emission.getEmission());
                    existingEmission.setApproved(false);

                    // Aktualisierung der Emission
                    co2EmissionDAO.update(existingEmission);
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Emission updated successfully.", "Success"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating emission: Emission not found.", "Error"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating emission: Emission is null.", "Error"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating emission: " + e.getMessage(), "Error"));
            e.printStackTrace();
        }
    }
    
    public void loadCountry() {
    	this.newEmissionYear=0;	//Beim Laden eines neuen Landes werden die Benutzereingaben in den Feldern gelöscht
    	this.newEmissionValue=0;
    	searchCountry();
    	
    }
	


}
