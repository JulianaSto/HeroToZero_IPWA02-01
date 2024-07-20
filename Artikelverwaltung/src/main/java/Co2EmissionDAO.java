import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

@Named
@ApplicationScoped
/*@Stateless*/
public class Co2EmissionDAO {

    private EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;

    
    public Co2EmissionDAO() {

        entityManager = Persistence.createEntityManagerFactory("herotozero").createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
}
    
    public List<Co2Emission> getAllCO2Emissions() {
        TypedQuery<Co2Emission> query = entityManager.createQuery("SELECT ce FROM CO2Emission ce", Co2Emission.class).setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
    public void save(Co2Emission co2Emission) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(co2Emission);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    public Co2Emission findById(int id) {
        return entityManager.find(Co2Emission.class, id);
    }

    public void update(Co2Emission co2Emission) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(co2Emission);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    

}
