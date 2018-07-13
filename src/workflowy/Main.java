
package workflowy;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//main application
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(
                Main.class.getResource("open-sans.light.ttf").toExternalForm(),
                10
        );
        //System.out.println( System.getProperties());
        System.out.println("java version: "+System.getProperty("java.version"));
        System.out.println("javafx.version: " + System.getProperty("javafx.version"));
 
        Parent root = FXMLLoader.load(getClass().getResource("/workflowy/dsgn/HomeScreen.fxml"));
        primaryStage.setTitle("Workflowy");
        primaryStage.setScene(new Scene (root , 489, 600));
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
//    @Override
//    public void stop() throws Exception{
//        super.stop();
//    }
    
}
