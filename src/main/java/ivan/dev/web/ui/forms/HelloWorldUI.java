package ivan.dev.web.ui.forms;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import java.util.Date;

@Title("Easy Web App")
@Theme("valo")
public class HelloWorldUI extends UI {

    private static final long serialVersionUID = 1L;
    private CssLayout layout;

    protected void init(VaadinRequest request) {
        layout = new CssLayout();
        setContent(layout);
        layout.addComponent(new Label("Hello world"));
        layout.addComponent(new PersonForm());
        
        layout.addComponent(new Button("Click me", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Hello at " + new Date());
                layout.addComponent(new Label("Hello at " + new Date()));
            }
        }));
    }
}