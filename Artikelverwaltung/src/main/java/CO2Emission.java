import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class CO2Emission implements Serializable  {

	@Id
    @GeneratedValue
	private int id;
	private int year;
    private int emission;
	
	public CO2Emission() {

    }

    public CO2Emission (int year, int emission) {
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

	public int getEmission() {
		return emission;
	}

	public void setEmission(int emission) {
		this.emission = emission;
	}
    
    
}