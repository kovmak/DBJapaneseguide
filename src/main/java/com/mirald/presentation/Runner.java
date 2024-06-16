package com.mirald.presentation;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import com.mirald.persistence.ApplicationConfig;
import com.mirald.persistence.util.ConnectionManager;
import com.mirald.persistence.util.DatabaseInitializer;
import com.mirald.presentation.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import  javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner extends Application {

    public static AnnotationConfigApplicationContext springContext;

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
        var fxmlLoader = new SpringFXMLLoader(springContext);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        var mainFxmlResource = Runner.class.getResource("view/signIn.fxml");
        Parent parent = (Parent) fxmlLoader.load(mainFxmlResource);
        Scene scene = new Scene(parent, 400, 370);
        scene.setFill(Color.web("#8d642c"));
        stage.setTitle("Story manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        springContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var connectionManager = springContext.getBean(ConnectionManager.class);
        var databaseInitializer = springContext.getBean(DatabaseInitializer.class);

        try {
            databaseInitializer.init();
            launch(args);
        } finally {
            connectionManager.closePool();
        }
    }
}
