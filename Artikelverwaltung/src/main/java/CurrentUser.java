import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class CurrentUser implements Serializable {

    boolean publisher, scientist;	//admin wurde zu publisher, client wurde zu scientist

    void reset() {
        publisher = false; scientist = false;
    }

    boolean isPublisher() {
        return publisher;
    }

    boolean isScientist() {
        return scientist;
    }

    boolean isValid() {
        return isPublisher() || isScientist();
    }

}
