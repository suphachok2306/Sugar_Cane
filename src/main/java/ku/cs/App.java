package ku.cs;

import com.github.saacsos.FXRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this,stage,"Sugarcane");
        configRoute();
        FXRouter.goTo("owner");

    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void configRoute() {
        FXRouter.when("login","ku/cs/login.fxml","Sugarcane");
        FXRouter.when("register","ku/cs/register.fxml","Sugarcane");
        FXRouter.when("employee","ku/cs/employee.fxml","Sugarcane");
        FXRouter.when("owner","ku/cs/owner.fxml","Sugarcane");
        FXRouter.when("check","ku/cs/checkWork.fxml","Sugarcane");
    }

    public static void main(String[] args) {
        launch();
    }

}