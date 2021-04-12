package sample.ui;

import javafx.scene.control.TextField;

public class LogInController {
    public TextField emailField;
    public TextField passwordField;

    public void logIn(){
        System.out.println(this.emailField.getText());
        System.out.println(this.passwordField.getText());
    }
}
