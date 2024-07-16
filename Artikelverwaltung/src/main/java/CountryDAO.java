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
    
    
  
    public List<Country> getApprovalCountriesWithEmissions() {		
   
	   //Nur die Countries zurückgeben, bei denen ein Approval für eine neue Emission vorliegt
        TypedQuery<Country> query = entityManager.createQuery(
            "SELECT DISTINCT c FROM Country c ", Country.class);
        List<Country> tempCountries = query.getResultList();
        List<Country> resultCountries = new ArrayList<>();
    	for (Country c : tempCountries) {
    		List<Co2Emission> tempCo2Emissionen = c.getCo2Emissionen();
    		for (Co2Emission e : tempCo2Emissionen) {
    	   		if (e.isApproved() == false) {
    	   			resultCountries.add(c);
    	   			break;
    	   		}
    		}
    	}
    	return resultCountries;
    }
    
    public List<Country> getAllCountriesWithEmissions() {		
    	   
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
        
        int size = (countr.get(0).getApprovedCo2Emissionen().size())-1;
        int i = countr.get(0).getApprovedCo2Emissionen().get(size).getYear();
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
        int size = (countr.get(0).getApprovedCo2Emissionen().size())-1;
        float i = countr.get(0).getApprovedCo2Emissionen().get(size).getEmission();
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
        query.setParameter("name", name);  // Parameter setzen
        return query.getResultList();
    }
    
    /*public void update(Country country) {		//Wird offensichtlich nicht benötigt
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(country);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }*/
    
   

}
