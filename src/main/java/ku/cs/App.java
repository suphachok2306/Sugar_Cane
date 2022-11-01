package ku.cs;

import com.github.saacsos.FXRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Sugar Cane");
        configRoute();
        scene = new Scene(loadFXML("owner"));

        stage.setScene(scene);
        stage.show();
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void configRoute() {
        FXRouter.when("owner", "ku/cs/owner.fxml","Sugar Cane",900, 700);
        FXRouter.when("employee", "ku/cs/employee.fxml","Sugar Cane",900, 700);
    }

    public static void main(String[] args) {
        launch();
    }

}