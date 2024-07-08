import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.checkerframework.checker.units.qual.Current;

@Named
@ApplicationScoped
public class UserCredentials implements Serializable
{


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
        for(String[] user: users) {	//jeder user vom String-Array users (oben) hat eine Liste bestehend aus name, hash und status (s.o.): Abgleich aller Positionen
            if(user[0].equals(name)) {
                if(user[1].equals(passHash)) {
                    if(user[2].equals("publisher")) {
                        currentUser.publisher = true; return;
                    } else if(user[2].equals("scientist")) {
                        currentUser.scientist = true; return;
                    }
                    else throw new RuntimeException("Benutzer " + name + " ist falsch angelegt."); //wenn der user weder admin noch client ist
                }
            }
        }
    }
}
