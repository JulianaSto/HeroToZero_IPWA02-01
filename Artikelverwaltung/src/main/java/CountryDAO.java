import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.ArrayList;

@Named
@ApplicationScoped
public class CountryDAO {

    EntityManager entityManager;

    public CountryDAO() {
            entityManager = Persistence.createEntityManagerFactory("herotozero").createEntityManager();
   }
   
    
    public List<Country> getApprovalCountriesWithEmissions() {		
	   //Nur die Länder zurückgeben, bei denen ein Approval für eine neue/ geänderte Emission aussteht. 
        TypedQuery<Country> query = entityManager.createQuery(
            "SELECT DISTINCT c FROM Country c ", Country.class);
        List<Country> tempCountries = query.getResultList();
        List<Country> resultCountries = new ArrayList<>();
        //Für jedes Land überprüfen, ob es eine Emission mit approved=false besitzt
    	for (Country c : tempCountries) {
    		List<Co2Emission> tempCo2Emissionen = c.getCo2Emissions();
    		for (Co2Emission e : tempCo2Emissionen) {
    	   		if (e.isApproved() == false) {	
    	   			resultCountries.add(c);
    	   			break;
    	   		}
    		}
    	}
        // Jede Entität aktualisieren
        for (Country country : resultCountries) {
            entityManager.refresh(country);
        }
    	return resultCountries;
    }
      
    public List<Country> getAllCountriesWithEmissions() {				
        // Abfrage erstellen
        TypedQuery<Country> query = entityManager.createQuery(
            "SELECT DISTINCT c FROM Country c ", Country.class);
        // Ergebnisse abrufen
        List<Country> countries = query.getResultList();
        
        // Jede Entität aktualisieren
        for (Country country : countries) {
            entityManager.refresh(country);
        }
        
        return countries;
    }

    public int getMaxYearForAny(String name) throws CountryNotFoundException{				
        List<Country> countr = new ArrayList();
    	TypedQuery<Country> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Country c " +
                "WHERE c.name = :name", Country.class);
    	query.setParameter("name", name);
        countr = query.getResultList();
        
        if(countr.isEmpty()) {
        	throw new CountryNotFoundException("Country not found: " + name);
        }
        
        int size = (countr.get(0).getApprovedCo2Emissions().size())-1;
        int i = countr.get(0).getApprovedCo2Emissions().get(size).getYear();
        return i;
    }
    
    public float getMaxYearEmissionForAny(String name) throws CountryNotFoundException{		
        List<Country> countr = new ArrayList();
    	TypedQuery<Country> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Country c " +
                "WHERE c.name = :name", Country.class);
    	query.setParameter("name", name);
    	countr = query.getResultList();
        if(countr.isEmpty()) {
        	throw new CountryNotFoundException("Country not found: " + name);
        }
        int size = (countr.get(0).getApprovedCo2Emissions().size())-1;
        float i = countr.get(0).getApprovedCo2Emissions().get(size).getEmission();
        return i;
    }
    
    public List<Country> getCountry(String name){								
        // Abfrage erstellen
        TypedQuery<Country> query = entityManager.createQuery(
            "SELECT c FROM Country c WHERE c.name = :name", Country.class);
        query.setParameter("name", name);  // Parameter setzen
        
        // Ergebnisse abrufen
        List<Country> countries = query.getResultList();
        
        // Jede Entität aktualisieren
        for (Country country : countries) {
            entityManager.refresh(country);
        }
        
        return countries;
    }    

}
