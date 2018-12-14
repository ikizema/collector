package ivan.dev.web.ui.views;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ivan.dev.web.ui.grid.Customer;

public class GlobalUI extends UI {
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout globalVerticalLayout = new VerticalLayout();
		
		final HorizontalLayout horizontalLayout1 = new HorizontalLayout();
		horizontalLayout1.addComponent(
				new Label("<b>My global UI!</b>", ContentMode.HTML)
				);
		
		final HorizontalLayout horizontalLayout2 = new HorizontalLayout();
		
		Grid<Customer> grid = new Grid<>();
        grid.setCaption("My Grid");
        grid.setItems(new Customer());
        grid.setSizeFull();
        horizontalLayout2.addComponent(grid);
        horizontalLayout2.setExpandRatio(grid, 1); // Expand to fill
		
		globalVerticalLayout.addComponent(horizontalLayout1);
		globalVerticalLayout.addComponent(horizontalLayout2);
		setContent(globalVerticalLayout);
	}

}
