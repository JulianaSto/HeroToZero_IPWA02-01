import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public class Country implements Serializable {
	@Id
	private int countryID;

	private String name;

	private String countryCode;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.ALL)	//"country" bezieht sich auf das Attribut in CO2Emission
    private List<CO2Emission> co2Emissionen = new ArrayList<>(); //Bei einer OneToMany-Beziehung wird immer eine Collection benötigt. co2Emissionen ist das Attribut in der Country-Entität, das die Beziehung zu CO2Emission darstellt. Dieses Attribut wird in der JPQL-Abfrage verwendet, um die Beziehung zwischen Country und CO2Emission zu definieren.
	

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

	public String getCode() {
		return countryCode;
	}

	public void setCode(String code) {
		this.countryCode = code;
	}

    public List<CO2Emission> getCO2Emissionen() {
        return co2Emissionen;
    }

    public void addCO2Emission(CO2Emission co2Emission) {
    	co2Emissionen.add(co2Emission);
    }
}
