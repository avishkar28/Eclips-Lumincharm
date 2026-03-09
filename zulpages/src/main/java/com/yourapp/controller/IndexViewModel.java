package com.yourapp.controller;  // ← change to your actual package

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;

public class IndexViewModel {

    @Command
    public void bookDemo() {
        // Create and show the modal
        Component comp = Executions.createComponents("/zul/demo-modal.zul", null, null);
        if (comp instanceof Window) {
            Window modal = (Window) comp;
            modal.doModal();  // ← this makes it appear!
        }
    }
    @Command
    public void submitDemo(
            @ContextParam(ContextType.VIEW) Component view) {

        Window modal = (Window) view;

        Textbox nameBox  = (Textbox) modal.getFellow("name");
        Textbox phoneBox = (Textbox) modal.getFellow("phone");
        Textbox cityBox  = (Textbox) modal.getFellow("city");

        String name  = nameBox.getValue().trim();
        String phone = phoneBox.getValue().trim();
        String city  = cityBox.getValue().trim();
        
        //only valid outputs
        if(!name.matches("^[A-Za-z ]{3,}$")) {
        	Clients.showNotification("Please enter a valid name (only letters, minimum 3 charaters)",
        			Clients.NOTIFICATION_TYPE_ERROR,
        			nameBox,"end_center",3000);
        	return;
        }
        
        if(!phone.matches("^[0-9]{10}$")) {
        	Clients.showNotification("Please enter a valid 10- digit phone number",
        			Clients.NOTIFICATION_TYPE_ERROR,phoneBox,"end_center",3000);
        	return;
        }
        
        if(!city.matches("^[A-Za-z]{2,}$")) {
        	Clients.showNotification("Please enter a valid city name",
        			Clients.NOTIFICATION_TYPE_ERROR,
        			cityBox,"end_center",3000);
        	return;
        }
        String message = "Hi Swash Team!\n" +
                         "Demo Request:\n" +
                         "Name: " + name + "\n" +
                         "Phone: " + phone + "\n" +
                         "City: " + city;

        String encodedMsg = URLEncoder.encode(message, StandardCharsets.UTF_8);

        String waUrl = "https://wa.me/917262861974?text=" + encodedMsg;

        Executions.getCurrent().sendRedirect(waUrl, "_blank");

        modal.detach();
    }
    @Command
    public void watchVideo() {
        Executions.getCurrent().sendRedirect("https://www.youtube.com/watch?v=YOUR_VIDEO", "_blank");
    }
}