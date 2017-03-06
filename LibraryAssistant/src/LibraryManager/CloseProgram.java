package LibraryManager;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class CloseProgram {
    
   
    static boolean returnValue;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button no = new Button("No");
        Button yes = new Button("Yes");

        no.setOnAction(e -> {
            returnValue = false;
            window.close();
        });
       
        yes.setOnAction(e -> {
            returnValue = true;
            window.close();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return returnValue;
    }
}