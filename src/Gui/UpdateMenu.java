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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Stack;

public class UpdateMenu extends Scenes {

    private static Scene getUpdate(String id, StackPane treepane) {

        Student student = new Student();
        Lecturer lecturer = new Lecturer();

        Button backBtn, submitBtn;
        Scene updateMenu;

        if (controller.getMode() == Controller.Mode.student)
            student = (Student) controller.Read(id);
        else
            lecturer = (Lecturer) controller.Read(id);

        final Student finalStudent = student;
        final Lecturer finalLecturer = lecturer;

        Label Id = new Label("ID:");
        Id.setPrefSize(50, 20);
        TextField idField = new TextField();
        idField.setId("idField");
        idField.setText(id);
        idField.setEditable(false);
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
        TextField classField = new TextField();
        Class.setPrefSize(50, 20);
        if (controller.getMode() == Controller.Mode.student) {
            Class.setText("Class:");
            nameField.setPromptText(student.getName());
            dobField.setPromptText(student.getDob());
            emailField.setPromptText(student.getEmail());
            phoneField.setPromptText(student.getPhone());
            addressField.setPromptText(student.getAddress());
            classField.setPromptText(student.getclass());
        }
        else {
            nameField.setPromptText(lecturer.getName());
            dobField.setPromptText(lecturer.getDob());
            emailField.setPromptText(lecturer.getEmail());
            phoneField.setPromptText(lecturer.getPhone());
            addressField.setPromptText(lecturer.getAddress());
            classField.setPromptText(lecturer.getDepartment());
        }
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
                    stage.close();
                }
        );

        submitBtn = new Button("Submit");
        submitBtn.setPrefSize(btnw, btnh);
        submitBtn.setOnAction(e ->
        {
            Scene scene = submitBtn.getScene();

            if (controller.getMode() == Controller.Mode.student) {
                Student newStudent = finalStudent;

                TextField field = (TextField) scene.lookup("#idField");
                if (!field.getText().equals(""))
                    newStudent.addId(field.getText());

                field = (TextField) scene.lookup("#nameField");
                if (!field.getText().equals(""))
                    newStudent.addName(field.getText());

                field = (TextField) scene.lookup("#dobField");
                if (!field.getText().equals(""))
                    newStudent.addDob(field.getText());

                field = (TextField) scene.lookup("#emailField");
                if (!field.getText().equals(""))
                    newStudent.addEmail(field.getText());

                field = (TextField) scene.lookup("#phoneField");
                if (!field.getText().equals(""))
                    newStudent.addPhone(field.getText());

                field = (TextField) scene.lookup("#addressField");
                if (!field.getText().equals(""))
                    newStudent.addAddress(field.getText());

                field = (TextField) scene.lookup("#classField");
                if (!field.getText().equals(""))
                    newStudent.addClass(field.getText());

                try {
                    controller.Update(id, newStudent);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (TransformerException e1) {
                    e1.printStackTrace();
                }
            } else {
                Lecturer newLecturer = finalLecturer;

                TextField field = (TextField) scene.lookup("#idField");
                if (!field.getText().equals(""))
                    newLecturer.addId(field.getText());

                field = (TextField) scene.lookup("#nameField");
                if (!field.getText().equals(""))
                    newLecturer.addName(field.getText());

                field = (TextField) scene.lookup("#dobField");
                if (!field.getText().equals(""))
                    newLecturer.addDob(field.getText());

                field = (TextField) scene.lookup("#emailField");
                if (!field.getText().equals(""))
                    newLecturer.addEmail(field.getText());

                field = (TextField) scene.lookup("#phoneField");
                if (!field.getText().equals(""))
                    newLecturer.addPhone(field.getText());

                field = (TextField) scene.lookup("#addressField");
                if (!field.getText().equals(""))
                    newLecturer.addAddress(field.getText());

                field = (TextField) scene.lookup("#classField");
                if (!field.getText().equals(""))
                    newLecturer.addDepartment(field.getText());

                try {
                    controller.Update(id, newLecturer);

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (TransformerException e1) {
                    e1.printStackTrace();
                }
            }
            Stage mainwindow = (Stage) treepane.getScene().getWindow();
            mainwindow.setScene(ViewMenu.getView());
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(backBtn, submitBtn);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(10);

        VBox layout = new VBox();
        layout.getChildren().addAll(idbox, namebox, dobbox, emailbox, phonebox, addressBox, classbox, buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(10);
        layout.setStyle(background);
        layout.setPadding(padding);
        updateMenu = new Scene(layout, w, h);
        return updateMenu;
    }

    static void display(String id, StackPane treepane)
    {
        Stage window = new Stage();

        if (controller.getMode() == Controller.Mode.student)
            window.setTitle("Update student's information");
        else
            window.setTitle("Update lecturere's information");

        window.setScene(getUpdate(id, treepane));
        window.setResizable(false);
        window.show();
    }
}
