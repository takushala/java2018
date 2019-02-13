package Gui;

import Controller.Controller;
import Entity.Individual;
import Entity.Lecturer;
import Entity.Student;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;


public class ViewMenu extends Scenes {

    static TreeView<String> tree;

    static Scene getView() {

        Button backBtn, deleteBtn, updateBtn;
        Scene viewMenu;

        getTree(Controller.Read());
        TextField searchBox = new TextField();
        searchBox.setOnKeyReleased(e ->
        {
            if (searchBox.getText() == null || searchBox.getText().trim().isEmpty()) {
                getTree(Controller.Read());
            } else
                getTree(Controller.SearchByName(searchBox.getText()));


            StackPane treePane = (StackPane) searchBox.getScene().lookup("#treepane");
            treePane.getChildren().clear();
            treePane.getChildren().add(tree);
        });

        backBtn = new Button("Back");
        backBtn.setPrefSize(btnw, btnh);
        backBtn.setOnAction(e ->
                {
                    Stage stage = (Stage) backBtn.getScene().getWindow();
                    if (Controller.getMode() == Controller.Mode.lecturer)
                        stage.setTitle("Lecturer manager");
                    else
                        stage.setTitle("Student manager");
                    stage.setScene(ManagerMenu.getManager());
                }
        );

        deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(btnw, btnh);
        deleteBtn.setOnAction(e ->
        {
            Scene scene = deleteBtn.getScene();
            StackPane pane = (StackPane) scene.lookup("#treepane");
            TreeView treeView = (TreeView) pane.getChildrenUnmodifiable().get(0);
            TreeItem selectedItem = (TreeItem) treeView.getSelectionModel().getSelectedItem();
            if (selectedItem == null)
                ErrorBox.display("Please choose an item");
            else {
                Button yesBtn = new Button("Yes");
                yesBtn.setOnAction(event ->
                {

                    try {
                        if (selectedItem.getChildren().size()==0)
                            Controller.Delete(selectedItem.getParent().getValue().toString());
                        else
                            Controller.Delete(selectedItem.getValue().toString());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (TransformerException e1) {
                        e1.printStackTrace();
                    }
                    Stage window = (Stage) pane.getScene().getWindow();
                    window.setScene(getView());
                    Stage yesStage =(Stage)yesBtn.getScene().getWindow();
                    yesStage.close();
                });
                String id = selectedItem.getValue().toString();
                if (selectedItem.getChildren().size()==0)
                    id = selectedItem.getParent().getValue().toString();
                ConfirmationBox.display("Are you sure you want to delete " + id + "?", yesBtn);
            }
        });

        updateBtn = new Button("Update");
        updateBtn.setPrefSize(btnw, btnh);
        updateBtn.setOnAction(e ->
                {
                    Scene scene = deleteBtn.getScene();
                    StackPane pane = (StackPane) scene.lookup("#treepane");
                    TreeView treeView = (TreeView) pane.getChildrenUnmodifiable().get(0);
                    TreeItem selectedItem = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    if (selectedItem == null)
                        ErrorBox.display("Please choose an item");
                    else
                        if (selectedItem.getChildren().size() == 0)
                            UpdateMenu.display(selectedItem.getParent().getValue().toString(), pane);
                        else
                            UpdateMenu.display(selectedItem.getValue().toString(), pane);
                });


        VBox layout = new VBox();
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(backBtn, deleteBtn, updateBtn);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(15);
        StackPane treePane = new StackPane();
        treePane.setId("treepane");
        treePane.getChildren().add(tree);
        layout.getChildren().addAll(searchBox, treePane, buttonBox);
        layout.setSpacing(15);
        layout.setStyle(background);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(padding);
        viewMenu = new Scene(layout, w, h);

        return viewMenu;
    }

    static void getTree(ArrayList<Individual> list) {
        TreeView<String> newTree = new TreeView<>();
        TreeItem<String> root = new TreeItem<>();
        root.isExpanded();
        for (int i = 0; i < list.size(); i++) {
            Lecturer lecturer;
            Student student;
            if (Controller.getMode() == Controller.Mode.student) {
                student = (Student) list.get(i);

                TreeItem<String> id = new TreeItem<>(student.getId());
                TreeItem<String> name = new TreeItem<>(student.getName());
                TreeItem<String> dob = new TreeItem<>(student.getDob());
                TreeItem<String> email = new TreeItem<>(student.getEmail());
                TreeItem<String> phone = new TreeItem<>(student.getPhone());
                TreeItem<String> address = new TreeItem<>(student.getAddress());
                TreeItem<String> Class = new TreeItem<>(student.getclass());

                id.getChildren().addAll(name, dob, email, phone, address, Class);
                root.getChildren().add(id);
                newTree = new TreeView<>(root);
            } else {
                lecturer = (Lecturer) list.get(i);
                TreeItem<String> id = new TreeItem<>(lecturer.getId());
                TreeItem<String> name = new TreeItem<>(lecturer.getName());
                TreeItem<String> dob = new TreeItem<>(lecturer.getDob());
                TreeItem<String> email = new TreeItem<>(lecturer.getEmail());
                TreeItem<String> phone = new TreeItem<>(lecturer.getPhone());
                TreeItem<String> address = new TreeItem<>(lecturer.getAddress());
                TreeItem<String> Class = new TreeItem<>(lecturer.getDepartment());
                id.getChildren().addAll(name, dob, email, phone, address, Class);
                root.getChildren().add(id);
                newTree = new TreeView<>(root);
            }

        }

        newTree.setShowRoot(false);

        tree = newTree;
    }

}
