import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import java.util.List;


/*@Stateless*/
public class Co2EmissionDAO {

    private EntityManager entityManager;
    
    public Co2EmissionDAO() {

        entityManager = Persistence.createEntityManagerFactory("herotozero").createEntityManager();

}
    
    public List<Co2Emission> getAllCO2Emissions() {
        TypedQuery<Co2Emission> query = entityManager.createQuery("SELECT ce FROM CO2Emission ce", Co2Emission.class);
        return query.getResultList();
    }
}
