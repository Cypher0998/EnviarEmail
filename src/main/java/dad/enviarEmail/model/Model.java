package dad.enviarEmail.model;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
public class Model {
	public Email correo = new SimpleEmail();

	public Email getCorreo() {
		return correo;
	}

	public void setCorreo(Email correo) {
		this.correo = correo;
	}
	
}
