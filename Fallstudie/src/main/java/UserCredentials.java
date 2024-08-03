import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UserCredentials implements Serializable{

    private final String[][] users =
            new String[][]{
                    // password hash obtained with java LoginController publ i-am-publ
                    new String[]{"publ",
                            "DyPQfyfmvYMUZTr2RVqXsizwFTFr6ta2bKftPob/1TZlmdJZTpWQ4Mv6kP+sGMQkBTvP+UKXCpa+eUz1AlRhGA==",
                            "publisher"},
                    // password hash obtained with java LoginController scien i-am-scien
                    new String[]{"scien",
                            "fFgU0UFw0393Ve3GAU3QXtLRLXCr3UeOyK7FIQVr9HQWeysa2TQrRB3JxcZFZ6QX7URPGTfpG5P31F6Fk+DdqA==",
                            "scientist"}
            };

    public UserCredentials() {
    }

    static String hashPassword(String name, String pass, String salt) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digester.digest((name + pass + salt)
                    .getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(hashBytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void validateUsernameAndPassword(CurrentUser currentUser, String name,String pass, String salt) { 
        String passHash = hashPassword(name, pass, salt);
        currentUser.reset();
      //Vergleich des Benutzernamens und des passHash  mit allen angelegten Nutzern im
        //String-Array "users" sowie Identifikation der Rolle
        for(String[] user: users) {	
            if(user[0].equals(name)) {
                if(user[1].equals(passHash)) {
                    if(user[2].equals("publisher")) {
                        currentUser.publisher = true; return;
                    } else if(user[2].equals("scientist")) {
                        currentUser.scientist = true; return;
                    }
                  //Exception, wenn Benutzer ohne gültige Rolle angelegt ist
                    else throw new RuntimeException("User " + name + " is not correctly assigned."); 
                }
            }
        }
    }
}
