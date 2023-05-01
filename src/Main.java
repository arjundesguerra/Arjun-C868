import Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;

/**
 * The Main class is the entry point for the application.
 */
public class Main extends Application {

    /**
     * The start method is called when the application is launched.
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if an error occurs during the startup of the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLViews/LoginForm.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            primaryStage.setTitle("Connexion");
        } else {
            primaryStage.setTitle("Login");
        }
    }

    /**
     * Main method for the Java Virtual Machine
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
