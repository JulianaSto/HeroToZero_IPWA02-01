import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public class Country implements Serializable {
	@Id
	@GeneratedValue
	private int countryID;

	private String name;

	private char code;
	
	
	

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

}
