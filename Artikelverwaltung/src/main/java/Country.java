import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public class Country implements Serializable {
	@Id
	private int countryID;

	private String name;

	private char code;
	
    @OneToMany(fetch= FetchType.EAGER)
    private List<CO2Emission> co2Emissionen = new ArrayList<>();
	

	public Country() {
	}

	public int getCountryID() {
		return countryID;
	}

	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getCode() {
		return code;
	}

	public void setCode(char code) {
		this.code = code;
	}

    public List<CO2Emission> getCO2Emissionen() {
        return co2Emissionen;
    }

    public void addCO2Emission(CO2Emission co2Emission) {
    	co2Emissionen.add(co2Emission);
    }
}
