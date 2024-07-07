import javax.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
@ViewScoped
public class Co2EmissionBean implements Serializable {

    private CountryDAO countryDAO;
    

    private Co2EmissionDAO co2EmissionDAO;
    
    private List<Co2Emission> co2Emissions;
    
    // Konstruktor
    public Co2EmissionBean() {
       
        // Hier können weitere Initialisierungen vorgenommen werden
        
    }
    
    @PostConstruct
    public void init() {
        co2Emissions = co2EmissionDAO.getAllCO2Emissions();
        System.out.println("Anzahl der CO2-Emissionen: " + co2Emissions.size()); // Debug-Ausgabe
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"); // Debug-Ausgabe

    }
    
    // Getter
    public List<Co2Emission> getCo2Emissions() {
    	//CO2EmissionDAO co2EmissionDAO1 = new CO2EmissionDAO();
    	co2Emissions = co2EmissionDAO.getAllCO2Emissions();

        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"); // Debug-Ausgabe
        return co2Emissions;
    }
    
  /*  public static void main (String[] args) {
    	CO2EmissionBean neuuu = new CO2EmissionBean();
    	System.out.println(neuuu.getCO2Emissions().size());
    	
    	System.out.println(neuuu.getCO2Emissions());
    	List<CO2Emission> nadine = neuuu.getCO2Emissions();
    	System.out.println("Emission" + nadine.get(0).getEmission());
    	System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"); // Debug-Ausgabe
    	
    }*/
}