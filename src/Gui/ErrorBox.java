package Gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorBox extends Scenes {

    private static Button cancelBtn = new Button("Okay");

    public static void display(String text) {
        Stage window = new Stage();
        window.setTitle("Error");

        Text message = new Text(text);

        cancelBtn.setPrefSize(btnw, btnh);
        cancelBtn.setOnAction(e ->
        {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });


        VBox layout = new VBox();
        layout.getChildren().addAll(message, cancelBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        window.setScene(new Scene(layout, 220, 100));
        window.setAlwaysOnTop(true);
        window.setResizable(false);
        window.showAndWait();

    }

}
