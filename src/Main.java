import Gui.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        primaryStage.setTitle("VGU OOP Java2018");
        window.setScene(MainMenu.getMain());
        window.setResizable(false);
        window.show();
    }
}