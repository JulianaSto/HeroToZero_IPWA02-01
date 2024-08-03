import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Country implements Serializable {
	@Id
	private int countryID;
	private String name;
	private String countryCode;

	@OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Co2Emission> co2Emissionen = new ArrayList<>();

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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String code) {
		this.countryCode = code;
	}

	public List<Co2Emission> getCo2Emissions() {
		return co2Emissionen;
	}

	public List<Co2Emission> getApprovedCo2Emissions() {
		List<Co2Emission> modifiedCo2Emissions = new ArrayList<>();
		for (Co2Emission emission : co2Emissionen) {
			if (emission.isApproved() == true) {
				modifiedCo2Emissions.add(emission);
			}
		}
		return modifiedCo2Emissions;
	}

	public List<Co2Emission> getNotApprovedCo2Emissions() {
		List<Co2Emission> modifiedCo2Emissions = new ArrayList<>();
		for (Co2Emission emission : co2Emissionen) {
			if (emission.isApproved() == false) {
				modifiedCo2Emissions.add(emission);
			}
		}
		return modifiedCo2Emissions;
	}

	public void addCo2Emission(Co2Emission co2Emission) {
		co2Emissionen.add(co2Emission);
	}

}
