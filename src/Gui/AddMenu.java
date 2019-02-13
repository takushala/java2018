package Gui;

import Controller.Controller;
import Entity.Lecturer;
import Entity.Student;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddMenu extends Scenes {

    static Scene getAdd() {

        Button backBtn, submitBtn;
        Scene addMenu;

        Label Id = new Label("ID:");
        Id.setPrefSize(50, 20);
        TextField idField = new TextField();
        idField.setId("idField");
        if (controller.getMode() == Controller.Mode.student)
            idField.setPromptText("GTxxxxx  or  GCxxxxx  (x:  is  a  digit)");
        else
            idField.setPromptText("xxxxxxxx (x:  is  a  digit)");
        idField.setFocusTraversable(false);
        HBox idbox = new HBox();
        idbox.getChildren().addAll(Id, idField);
        HBox.setHgrow(idField, Priority.ALWAYS);
        idbox.setSpacing(20);

        Label Name = new Label("Name:");
        Name.setPrefSize(50, 20);
        TextField nameField = new TextField();
        nameField.setId("nameField");
        HBox namebox = new HBox();
        namebox.getChildren().addAll(Name, nameField);
        HBox.setHgrow(nameField, Priority.ALWAYS);
        namebox.setSpacing(20);

        Label Dob = new Label("DoB:");
        Dob.setPrefSize(50, 20);
        TextField dobField = new TextField();
        dobField.setId("dobField");
        HBox dobbox = new HBox();
        dobbox.getChildren().addAll(Dob, dobField);
        HBox.setHgrow(dobField, Priority.ALWAYS);
        dobbox.setSpacing(20);

        Label Email = new Label("Email:");
        Email.setPrefSize(50, 20);
        TextField emailField = new TextField();
        emailField.setId("emailField");
        HBox emailbox = new HBox();
        emailbox.getChildren().addAll(Email, emailField);
        HBox.setHgrow(emailField, Priority.ALWAYS);
        emailbox.setSpacing(20);

        Label Phone = new Label("Phone:");
        Phone.setPrefSize(50, 20);
        TextField phoneField = new TextField();
        phoneField.setId("phoneField");
        HBox phonebox = new HBox();
        phonebox.getChildren().addAll(Phone, phoneField);
        HBox.setHgrow(phoneField, Priority.ALWAYS);
        phonebox.setSpacing(20);

        Label Address = new Label("Address:");
        Address.setPrefSize(50, 20);
        TextField addressField = new TextField();
        addressField.setId("addressField");
        HBox addressBox = new HBox();
        addressBox.getChildren().addAll(Address, addressField);
        HBox.setHgrow(addressField, Priority.ALWAYS);
        addressBox.setSpacing(20);

        Label Class = new Label("Department:");
        Class.setPrefSize(50, 20);
        if (controller.getMode() == Controller.Mode.student)
            Class.setText("Class:");
        TextField classField = new TextField();
        classField.setId("classField");
        HBox classbox = new HBox();
        classbox.getChildren().addAll(Class, classField);
        HBox.setHgrow(classField, Priority.ALWAYS);
        classbox.setSpacing(20);

        backBtn = new Button("Back");
        backBtn.setPrefSize(btnw, btnh);
        backBtn.setOnAction(e ->
                {
                    Stage stage = (Stage) backBtn.getScene().getWindow();
                    if (controller.getMode() == Controller.Mode.lecturer)
                        stage.setTitle("Lecturer manager");
                    else
                        stage.setTitle("Student manager");
                    stage.setScene(ManagerMenu.getManager());
                }
        );

        submitBtn = new Button("Submit");
        submitBtn.setPrefSize(btnw, btnh);
        submitBtn.setOnAction(e ->
        {
            Scene scene = submitBtn.getScene();

            Stage stage = (Stage) scene.getWindow();
            if (controller.getMode() == Controller.Mode.lecturer)
                stage.setTitle("Lecturer manager");
            else
                stage.setTitle("Student manager");

            if (controller.getMode() == Controller.Mode.student) {
                Student student = new Student();

                TextField field = (TextField) scene.lookup("#idField");
                student.addId(field.getText());

                field = (TextField) scene.lookup("#nameField");
                student.addName(field.getText());

                field = (TextField) scene.lookup("#dobField");
                student.addDob(field.getText());

                field = (TextField) scene.lookup("#emailField");
                student.addEmail(field.getText());

                field = (TextField) scene.lookup("#phoneField");
                student.addPhone(field.getText());

                field = (TextField) scene.lookup("#addressField");
                student.addAddress(field.getText());

                field = (TextField) scene.lookup("#classField");
                student.addClass(field.getText());

                controller.Create(student);
                stage.setScene(ManagerMenu.getManager());
            } else {
                Lecturer lecturer = new Lecturer();

                TextField field = (TextField) scene.lookup("#idField");
                lecturer.addId(field.getText());

                field = (TextField) scene.lookup("#nameField");
                lecturer.addName(field.getText());

                field = (TextField) scene.lookup("#dobField");
                lecturer.addDob(field.getText());

                field = (TextField) scene.lookup("#emailField");
                lecturer.addEmail(field.getText());

                field = (TextField) scene.lookup("#phoneField");
                lecturer.addPhone(field.getText());

                field = (TextField) scene.lookup("#addressField");
                lecturer.addAddress(field.getText());

                field = (TextField) scene.lookup("#classField");
                lecturer.addDepartment(field.getText());

                controller.Create(lecturer);
                stage.setScene(ManagerMenu.getManager());
            }

        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(backBtn, submitBtn);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(10);

        VBox layout = new VBox();
        layout.getChildren().addAll(idbox, namebox, dobbox,
                emailbox, phonebox, addressBox, classbox, buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(10);
        layout.setStyle(background);
        layout.setPadding(padding);
        addMenu = new Scene(layout, w, h);
        return addMenu;
    }
}
