package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Editor advertis");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }


    public static void main(String[] args) {
        StatusLogger.getLogger().setLevel(Level.OFF);

        //  JPanel pane = new JPanel();
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        Bot bot = new Bot();
        try {
            botApi.registerBot(bot);

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
