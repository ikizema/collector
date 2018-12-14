package ivan.dev.web.ui.forms;

import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class PersonForm extends FormLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1890176488371548510L;
	// Define the input fields for data.
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	TextField email = new TextField("Email");
	DateField birthday = new DateField("Birthday");

	public PersonForm() {
	     addComponents(firstName, lastName, email, birthday);
	}
}