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
	private int tmpId;
	private int tmpYear;
    private float tmpEmission;
    
    Country country;
    List<Country> countryList= new ArrayList<>();
    
    public void searchCountry() {
    	this.countryList = countryDAO.getCountry(countryToBeChanged);
    	
    }
    public List<Country> getAktuelleCountry() {	//nur für Testausgabe
    	return this.countryList;
    	
    }

	public String getCountryToBeChanged() {
		return countryToBeChanged;
	}

	public void setCountryToBeChanged(String countryToBeChanged) {	//setzen des Landnamens aus der Benutzereingabe
		this.countryToBeChanged = countryToBeChanged;
	}
    
}
