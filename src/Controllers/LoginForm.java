package Controllers;

import Database.UserHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents a login form that allows a user to sign in with a username and password.
 */
public class LoginForm {
    @FXML
    private Text userLoginText;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text locationText;
    @FXML
    private Button signInButton;
    private ResourceBundle bundle;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    int failedAttempts = 0;


    /**
     * Initializes the login form by setting focus to the sign-in button and checking the current location.
     * If the default locale is French, this method loads the French language resource bundle and sets
     * the text to French.
     */
    public void initialize() {
        if (Locale.getDefault().getLanguage().equals("fr")) {
            bundle = ResourceBundle.getBundle("ResourceBundles/fr_login");
            setFrench();
        }

        signInButton.setFocusTraversable(true);
        Platform.runLater(() -> signInButton.requestFocus());

        checkLocation();
    }

    /**
     * Checks the current location and displays it in the locationText. If the default locale is
     * French, the location is displayed in French.
     */
    public void checkLocation() {
        ZoneId zone = ZoneId.systemDefault();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            locationText.setText(bundle.getString("Location") + ": " + zone);
        } else {
            locationText.setText("Location: " + zone);
        }
    }

    /**
     * Attempts to sign in the user with the provided username and password. If the validation is successful,
     * the homepage is opened and the login attempt is logged. If the validation fails, an error message is displayed.
     *
     * @throws IOException  if there is an error loading the homepage FXML file
     * @throws SQLException if there is an error validating the user's credentials
     */
    public void signIn() throws IOException, SQLException {
        int maxAttempts = 4;

        String username = usernameField.getText();
        String password = passwordField.getText();
        String loginStatus;
        System.out.println(failedAttempts);

        boolean isValidUser = UserHelper.validateUser(username, password);
        if (isValidUser) {
            loginStatus = "Success";
        } else {
            loginStatus = "Failure";
            failedAttempts++;
        }

        String logMessage = String.format("[%s] Login attempt by user '%s': %s\n", LocalDateTime.now().format(formatter), username, loginStatus);
        try (FileWriter writer = new FileWriter("login_activity.txt", true)) {
            writer.write(logMessage);
        }

        if (isValidUser) {
            Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/Homepage.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Homepage");
            newStage.setScene(scene);
            newStage.show();
            Homepage.appointmentNotification();
            Stage currentStage = (Stage) locationText.getScene().getWindow();
            currentStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (Locale.getDefault().getLanguage().equals("fr")) {
                alert.setTitle(bundle.getString("InvalidAlertTitle"));
                alert.setContentText(bundle.getString("InvalidAlertDescription"));
            } else if (maxAttempts - failedAttempts == 0) {
                alert.setTitle("Sign-In Failed");
                alert.setContentText("Too many failed attempts. Application now closing.");
                Stage currentStage = (Stage) locationText.getScene().getWindow();
                currentStage.close();
            } else {
                alert.setTitle("Sign-In Failed");
                alert.setContentText("Incorrect username or password. You have " + (maxAttempts - failedAttempts) + " attempt(s) left.");
            }
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }


    /**
     * Sets the language of elements to French.
     */
    public void setFrench() {
        userLoginText.setText(bundle.getString("UserLogin"));
        usernameField.setPromptText(bundle.getString("Username"));
        passwordField.setPromptText(bundle.getString("Password"));
        signInButton.setText(bundle.getString("SignIn"));
    }
}
