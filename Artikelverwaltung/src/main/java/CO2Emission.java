import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class CO2Emission implements Serializable  {

    @Id
	private int id;
	private int year;
    private float emission;
    
    @ManyToOne
    @JoinColumn(name = "countryID", referencedColumnName = "countryID")	//name = "countryID": Dies ist der Name der Spalte in der Tabelle CO2Emission, die den Fremdschlüssel zur Country-Tabelle darstellt. referencedColumnName = "countryID": Dies ist der Name der Spalte in der Tabelle Country, auf die die Spalte countryID in der Tabelle CO2Emission verweist.
    private Country country;
    

    
	
	public CO2Emission() {

    }

    public CO2Emission (int year, float emission) {
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

	public void setEmission(int emission) {
		this.emission = emission;
	}
    
    
}