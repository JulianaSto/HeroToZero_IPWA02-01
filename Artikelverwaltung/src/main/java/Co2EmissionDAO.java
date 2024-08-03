import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;


@Named
@ApplicationScoped
public class Co2EmissionDAO {

	private EntityManager entityManager;

	public Co2EmissionDAO() {
		entityManager = Persistence.createEntityManagerFactory("herotozero").createEntityManager();
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
