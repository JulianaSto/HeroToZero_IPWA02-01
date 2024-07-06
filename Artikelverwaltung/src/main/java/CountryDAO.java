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
    
    /*public List<Object[]> getAllData() {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT c, e FROM Country c JOIN c.co2Emissionen e", Object[].class);
        return query.getResultList();
    }*/
    
  /* WORKS
    public List<Country> getAllCountriesWithEmissions() {			
   
	   //In dieser Methode verwenden wir JOIN FETCH, um sicherzustellen, dass die CO2Emissionen zusammen mit den Country-Objekten abgerufen werden. Dadurch erhalten wir eine Liste von Country-Objekten, von denen jedes eine Liste von CO2Emission-Objekten enthält.
        TypedQuery<Country> query = entityManager.createQuery(
            "SELECT DISTINCT c, e JOIN FETCH c.co2Emissionen", Country.class);
        return query.getResultList();
    }
    
    */
    public List<Country> getAllCountries() {
        TypedQuery<Country> query = entityManager.createQuery("SELECT c FROM Country c", Country.class);
        return query.getResultList();
    }

   

}
