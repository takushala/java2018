package Gui;

import Controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerMenu extends Scenes {

    static Scene getManager() {

        Scene studentMenu;
        Button backBtn, addBtn, viewBtn;

        backBtn = new Button("Back");
        backBtn.setPrefSize(btnw, btnh);
        backBtn.setOnAction(e ->
        {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setTitle("VGU OOP Java2018");
            stage.setScene(MainMenu.getMain());
        });

        addBtn = new Button("Add");
        addBtn.setPrefSize(btnw, btnh);
        addBtn.setOnAction(e ->
        {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            if (controller.getMode() == Controller.Mode.student)
                stage.setTitle("Add student");
            else
                stage.setTitle("Add lecturer");
            stage.setScene(AddMenu.getAdd());
        });

        viewBtn = new Button("View");
        viewBtn.setPrefSize(btnw, btnh);
        viewBtn.setOnAction(e ->
        {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            if (controller.getMode() == Controller.Mode.student)
                stage.setTitle("View student");
            else
                stage.setTitle("View lecturer");
            stage.setScene(ViewMenu.getView());

        });

        VBox layout = new VBox();
        layout.getChildren().addAll(addBtn, viewBtn, backBtn);
        layout.setSpacing(15);
        layout.setStyle(background);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(padding);
        studentMenu = new Scene(layout, w, h);

        return studentMenu;
    }
}
