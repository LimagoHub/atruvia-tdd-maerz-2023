package de.limago.zeiterfassung.controllers.mvccontrollers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocalMessages {
	
	private String message="";
	private boolean isVisible=false;
	private String alertAttribute="";
	
	public void setSuccess(String message) {
		setMessage(message, "alert-success");
	}
	public void setError(String message) {
		setMessage(message, "alert-danger");
		
	}
	public void setWarning(String message) {
		setMessage(message,"alert-warning");
	}
	
	private void setMessage(String message, String alert) {
		setVisible(true);
		setAlertAttribute(alert);
		setMessage(message);
	}
	
	
	public void clear() {
		message ="";
		isVisible = false;
		alertAttribute = "";
	}
}