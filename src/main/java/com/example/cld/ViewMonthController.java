package com.example.cld;

import java.io.IOException;

public class ViewMonthController {

    private MainController mainController = MainController.getInstance();

    public void switchToMainMenu(javafx.event.Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToMainMenu(event);
    }
}
