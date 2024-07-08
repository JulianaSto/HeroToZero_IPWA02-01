import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.ArrayList;

@Named
@ApplicationScoped
public class CountryDAO {

    EntityManager entityManager;

    CriteriaBuilder criteriaBuilder;



    public CountryDAO() {

            entityManager = Persistence.createEntityManagerFactory("herotozero").createEntityManager();
            criteriaBuilder = entityManager.getCriteriaBuilder();

   }
   
    public List<String> getAllIDs() {
        return entityManager.createQuery("SELECT a.countryCode FROM Country a", String.class).getResultList();
    }
    
    
  
    public List<Country> getAllCountriesWithEmissions() {		//WORKS	
   
	   //In dieser Methode verwenden wir JOIN FETCH, um sicherzustellen, dass die CO2Emissionen zusammen mit den Country-Objekten abgerufen werden. Dadurch erhalten wir eine Liste von Country-Objekten, von denen jedes eine Liste von CO2Emission-Objekten enthält.
        TypedQuery<Country> query = entityManager.createQuery(
            "SELECT DISTINCT c FROM Country c ", Country.class);
        return query.getResultList();
    }
    
    public List<Country> getFilteredCountries() {	//WORKS
        TypedQuery<Country> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Country c " +
                "WHERE c.name = 'Germany'", Country.class);
        return query.getResultList();
    }
    
    

    public int getMaxYearForAny(String name) throws CountryNotFoundException{	//WORKS
        List<Country> countr = new ArrayList();
    	TypedQuery<Country> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Country c " +
                "WHERE c.name = :name", Country.class);
    	query.setParameter("name", name);
        countr = query.getResultList();
        
        if(countr.isEmpty()) {
        	throw new CountryNotFoundException("Land nicht gefunden: " + name);
        }
        
        int size = (countr.get(0).getCo2Emissionen().size())-1;
        int i = countr.get(0).getCo2Emissionen().get(size).getYear();
        return i;
    }
    
    public float getMaxYearEmissionForAny(String name) throws CountryNotFoundException{	//WORKS
        List<Country> countr = new ArrayList();
    	TypedQuery<Country> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Country c " +
                "WHERE c.name = :name", Country.class);
    	query.setParameter("name", name);
    	countr = query.getResultList();
        if(countr.isEmpty()) {
        	throw new CountryNotFoundException("Land nicht gefunden: " + name);
        }
        int size = (countr.get(0).getCo2Emissionen().size())-1;
        float i = countr.get(0).getCo2Emissionen().get(size).getEmission();
        return i;
    }
    
    public List<Country> getAllCountries() {
        TypedQuery<Country> query = entityManager.createQuery("SELECT c FROM Country c", Country.class);
        return query.getResultList();
    }
    
    //Aufgabe 2
    
    public List<Country> getCountry(String name){	
        TypedQuery<Country> query = entityManager.createQuery(
                "SELECT c " +
                "FROM Country c " +
                "WHERE c.name = :name", Country.class);
        return query.getResultList();
    }
    
   

}
