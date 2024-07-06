import javax.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
@ViewScoped
public class CO2EmissionBean implements Serializable {

    private CountryDAO countryDAO;
    

    private CO2EmissionDAO co2EmissionDAO;
    
    private List<CO2Emission> co2Emissions;
    
    // Konstruktor
    public CO2EmissionBean() {
       
        // Hier können weitere Initialisierungen vorgenommen werden
        
    }
    
    @PostConstruct
    public void init() {
        co2Emissions = co2EmissionDAO.getAllCO2Emissions();
        System.out.println("Anzahl der CO2-Emissionen: " + co2Emissions.size()); // Debug-Ausgabe
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"); // Debug-Ausgabe

    }
    
    // Getter
    public List<CO2Emission> getCO2Emissions() {
        co2Emissions = co2EmissionDAO.getAllCO2Emissions();
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"); // Debug-Ausgabe
        return co2Emissions;
    }
}