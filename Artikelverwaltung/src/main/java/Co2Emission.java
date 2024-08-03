import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Co2Emission implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int year;
    private float emission;
    private boolean approved; 
    
    @ManyToOne
    @JoinColumn(name = "countryID", referencedColumnName = "countryID")			
    private Country country;
    
    
	public Co2Emission() {

    }

    public Co2Emission (int year, float emission) {	
        this.year = year;
        this.emission = emission;
    }

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public float getEmission() {
		return emission;
	}

	public void setEmission(float emission) {
		this.emission = emission;
	}
	
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}  
	
	public void setCountry(Country country) {	
	    this.country = country;
	    if (!country.getCo2Emissions().contains(this)) {
	        country.addCo2Emission(this);
	    }
	}
    
}
