import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

    public List<String> getAllCountryIDs() {
        return entityManager.createQuery("SELECT a.CountryCode FROM laender a", String.class).getResultList();
    }





}
