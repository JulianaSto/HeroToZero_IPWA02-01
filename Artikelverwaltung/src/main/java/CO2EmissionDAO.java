import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import java.util.List;


/*@Stateless*/
public class CO2EmissionDAO {

    private EntityManager entityManager;
    
    public CO2EmissionDAO() {

        entityManager = Persistence.createEntityManagerFactory("herotozero").createEntityManager();

}
    
    public List<CO2Emission> getAllCO2Emissions() {
        TypedQuery<CO2Emission> query = entityManager.createQuery("SELECT ce FROM CO2Emission ce", CO2Emission.class);
        return query.getResultList();
    }
}
