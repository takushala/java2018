package Gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ConfirmationBox extends Scenes {

    private static Button cancelBtn = new Button("Cancel");

    static void display(String text, Button yesBtn) {
        Stage window = new Stage();
        window.setTitle("Warning");

        Text message = new Text(text);

        yesBtn.setPrefSize(btnw / 2, btnh / 2);

        cancelBtn.setPrefSize(btnw / 2, btnh / 2);
        cancelBtn.setOnAction(e ->
        {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(cancelBtn, yesBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        VBox layout = new VBox();
        layout.getChildren().addAll(message, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        //
        buttonBox.setSpacing(10);

        window.setScene(new Scene(layout, 250, 75));
        window.setAlwaysOnTop(true);
        window.setResizable(false);
        window.showAndWait();

    }

}
