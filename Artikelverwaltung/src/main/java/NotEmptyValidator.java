import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;

@Named
@ViewScoped
@FacesValidator("notEmptyValidator")
public class NotEmptyValidator implements Validator<Object>, Serializable {
	
	public NotEmptyValidator() {
		
	}
    
	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || value.toString().trim().isEmpty()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "This entry is required.", "This entry is required.");
            throw new ValidatorException(message);
        }
    }
}