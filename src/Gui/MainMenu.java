package Gui;

import Controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenu extends Scenes {

    public static Scene getMain() {

        Button studentBtn, lecturerBtn, exitBtn;
        Scene mainMenu;

        studentBtn = new Button("Student manager");
        studentBtn.setPrefSize(btnw, btnh);
        studentBtn.setOnAction(e -> {
                    Stage stage = (Stage) studentBtn.getScene().getWindow();
                    stage.setTitle("Student manager");
                    stage.setScene(ManagerMenu.getManager());
                    try {
                        controller.Initiate(Controller.Mode.student);
                    } catch (Exception exception) {
                    }
                }
        );

        lecturerBtn = new Button("Lecturer manager");
        lecturerBtn.setPrefSize(btnw, btnh);
        lecturerBtn.setOnAction(e -> {
                    Stage stage = (Stage) lecturerBtn.getScene().getWindow();
                    stage.setTitle("Lecturer manager");
                    stage.setScene(ManagerMenu.getManager());
                    try {
                        controller.Initiate(Controller.Mode.lecturer);
                    } catch (Exception exception) {
                    }
                }
        );


        exitBtn = new Button("Exit");
        exitBtn.setPrefSize(btnw, btnh);
        exitBtn.setOnAction(e ->
                {
                    Stage stage = (Stage) exitBtn.getScene().getWindow();
                    stage.close();
                }
        );

        VBox layout = new VBox();
        layout.getChildren().addAll(studentBtn, lecturerBtn, exitBtn);
        layout.setSpacing(15);
        layout.setStyle("-fx-background-color: #DDDDDD;");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(padding);
        mainMenu = new Scene(layout, w, h);

        return mainMenu;
    }

}
