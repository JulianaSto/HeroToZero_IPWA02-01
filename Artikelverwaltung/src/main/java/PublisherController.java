import jakarta.faces.application.FacesMessage;

import jakarta.faces.application.NavigationHandler;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable; 
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.io.IOException;


@Named
@ViewScoped
public class PublisherController implements Serializable {
	
	@Inject
	Co2EmissionDAO co2EmissionDAO;

    public void approve(Co2Emission emission) {
        // Setze die Emission als approved
        emission.setApproved(true);
        
        // Speichere die Änderungen in der Datenbank
        co2EmissionDAO.update(emission);
    }


	
}
