/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import java.lang.Exception;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.w3c.dom.Document;

/**
 *
 * @author Asif Towheed
 */

@FacesValidator("SPValidator")
public class same_password_validator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      String Value = value.toString();
      //Document pass1 = pass1.getElementById;
      /*
      CHANGE THE VALIDATOR TO INCORPORATE CHECKING IF PASS1 AND PASS2 HAVE THE SAME TEXT OR NOT
      */

      try {
         if (Value.contains("2"));
         else throw new Exception();
      } catch (Exception e) {
         FacesMessage msg = new FacesMessage("URL validation failed","Invalid URL format");
         msg.setSeverity(FacesMessage.SEVERITY_ERROR);
         throw new ValidatorException(msg);
      }
    }
    
}
