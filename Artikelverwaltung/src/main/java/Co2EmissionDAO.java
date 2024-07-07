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
        TypedQuery<Co2Emission> query = entityManager.createQuery("SELECT ce FROM CO2Emission ce", Co2Emission.class);
        return query.getResultList();
    }
}
