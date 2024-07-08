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

import java.io.Serializable;

@Named
@ViewScoped
public class LoginController implements Serializable {

    @Inject
    UserCredentials userCredentials;

    @Inject
    CurrentUser currentUser;
    
    @Inject
    CountryDAO countryDAO;
    
    @Inject
    Co2EmissionDAO co2EmissionDAO; 
    
    private String currentCountryName;
    private int maxYear;
    private float maxYearEmission;


    // TODO: diese Wert sollte aus einer Konfiguration kommen.
    //       Jede Installation sollte eine Unterschiedlich haben.
    //       Dieser Salt muss geheim bleiben.
    private static final String salt = "vXsia8c04PhBtnG3isvjlemj7Bm6rAhBR8JRkf2z";

    // das sind die text-felder (zB, um zu den Benutzern zu zeigen)
    String user, password;
    // dieses Feld ist für die Lagerung in den Validierungsstufen
    String tempUsername;
    // dieser Feld ist für die Anzeige zu den Benutzern das nächste Mal
    String failureMessage = "";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    // this method should be called early to avoid providing information if the user is not logged in
    public void checkLogin() {
        if(!currentUser.isValid()) {
            failureMessage = "Bitte loggen Sie sich ein.";
            FacesContext fc = FacesContext.getCurrentInstance();
            NavigationHandler nh = fc.getApplication().getNavigationHandler();
            nh.handleNavigation(fc, null, "login.xhtml?faces-redirect=true");
        }

    }

    public String logout() {
        currentUser.reset();
        return "login.xhtml?faces-redirect=true";
    }

    public void postValidateUser(ComponentSystemEvent ev) {
        UIInput temp = (UIInput) ev.getComponent();
        this.tempUsername = (String) temp.getValue();
    }

    public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) value;
        userCredentials.validateUsernameAndPassword(currentUser, tempUsername, password, salt);
        if (!currentUser.isValid())
            throw new ValidatorException(new FacesMessage("Login falsch!"));	//diese message wird im xhtml ausgegeben
    }

    public String login() {
        if (currentUser.isPublisher()) {
            this.failureMessage = "";
            return "publisher.xhtml?faces-redirect=true";
        } else if (currentUser.isScientist()) {
            this.failureMessage = "";
            return "scientist.xhtml?faces-redirect=true";
        } else {
            this.failureMessage = "Passwort und Benutzername nicht erkannt.";
            return "";
        }
    }

    public static void main(String[] args) {
        if(args.length!=2) {
            System.err.println("Usage: java LoginController user pass");
            System.exit(1);
        }
        System.out.println(UserCredentials.hashPassword(args[0], args[1], salt));

    }




//Folgend: Eigene Implementierungen
	public String getCurrentCountryName() {
		return currentCountryName;
	}

	public void setCurrentCountryName(String currentCountryName) {
		this.currentCountryName = currentCountryName;
	}
	

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }
    

    public void loadMaxYearForAny() throws CountryNotFoundException {
        this.maxYear = countryDAO.getMaxYearForAny(currentCountryName);
    }
    

	public float getMaxYearEmission() {
		return maxYearEmission;
	}

	public void setMaxYearEmission(float maxYearEmission) {
		this.maxYearEmission = maxYearEmission;
	}
    public void loadMaxYearEmissionForAny() throws CountryNotFoundException {
        this.maxYearEmission = countryDAO.getMaxYearEmissionForAny(currentCountryName);
    }
    public void loadBoth() {
    	this.maxYearEmission = 0; //Reset
        this.maxYear = 0;
    	try {
    	this.maxYearEmission = countryDAO.getMaxYearEmissionForAny(currentCountryName);
        this.maxYear = countryDAO.getMaxYearForAny(currentCountryName);
    	}catch (CountryNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Das angegebene Land konnte nicht gefunden werden.", e.getMessage()));
        }
    }

}