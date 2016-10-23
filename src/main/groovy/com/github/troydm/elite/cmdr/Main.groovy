package com.github.troydm.elite.cmdr

import com.github.troydm.elite.cmdr.action.SoundAction
import com.github.troydm.elite.cmdr.event.generator.SpeechEventGenerator

//new KeyPressAction("/").execute()

import javafx.application.Application
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.beans.property.StringProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.stage.WindowEvent

import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.LogRecord
import java.util.logging.Filter
import java.util.logging.StreamHandler
import java.util.logging.SimpleFormatter

public class MainApplication extends Application {

    Button startButton
    TextArea script
    TextArea output
    StringProperty outputProperty = new SimpleStringProperty();
    StreamHandler handler
    String font_size = "-fx-font-size: 25px;";
    String small_font_size = "-fx-font-size: 18px;";
    String foreground = "-fx-text-fill: rgb(240, 123, 5);";
    String background = "-fx-base: rgb(0, 0, 0);";
    String buttonForeground = "-fx-text-fill: rgb(255, 255, 255); -fx-font-family: \"Terminal\";";
    String buttonBackground = "-fx-background-color: rgb(240, 123, 5); -fx-background-radius: 0; ";
    String padding = "-fx-padding: 5px 3px 3px 5px;"
    String textAreaStyle = padding  + small_font_size + "-fx-base: rgb(255,255,255); -fx-text-fill: rgb(0,0,0);-fx-background-radius: 0; "

    public static void main(String[] args) {
        launch(MainApplication.class, args);
    }

    public void startScript(){
        script.setEditable(false)
        System.out.println("Starting script")
        UserScriptManager.instance.start()
        System.out.println("Script running")
    }

    public void stopScript(){
        System.out.println("Stopping script")
        UserScriptManager.instance.stop()
        System.out.println("Script stopped")
        script.setEditable(true)
    }

    public void stopEverything(){
        UserScriptManager.instance.stop()
        TempFileManager.getInstance().deleteAllTempFiles()
    }

    class Output extends OutputStream {

        StringBuilder sb = new StringBuilder()

        public void refresh(){
            final String s = toString()
            Platform.runLater(new Runnable() {
                public void run() {
                    outputProperty.setValue(s)
                }
            });
        }

        public void clear(){
            sb = new StringBuilder()
            refresh()
        }

        @Override
        public void write(int b) throws IOException {
            sb.append((char)b)
            refresh()
        }

        @Override
        public String toString(){
            return sb.toString()
        }

    }

    Output outStream

    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("/icon.jpg")))
        String style = foreground + background
        primaryStage.setTitle("ELITE CMDR")
        Label title = new Label()
        title.setStyle(font_size + padding + style)
        title.setText("Elite CMDR")
        HBox spacer = new HBox()
        HBox.setHgrow(spacer,Priority.ALWAYS)
        startButton = new Button()
        startButton.setStyle(small_font_size + buttonForeground + buttonBackground)
        startButton.setText("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(startButton.getText().equals("Stop")) {
                    stopScript()
                    startButton.setText("Start")
                }else {
                    outStream.clear()
                    startScript()
                    startButton.setText("Stop")
                }
            }
        });

        script = new TextArea()
        script.setStyle(textAreaStyle)
        UserScriptManager.instance.load()
        script.setText(UserScriptManager.instance.text)
        script.textProperty().addListener(new ChangeListener<String>() {
            @Override
            void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                UserScriptManager.instance.save(newValue)
            }
        })
        output = new TextArea()
        output.setEditable(false)
        output.setStyle(textAreaStyle)
        output.textProperty().bind(outputProperty)
        outputProperty.addListener(new ChangeListener<Object>() {
            @Override
            void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                // from stackoverflow.com/a/30264399/1032167
                // for some reason setScrollTop will not scroll properly
                //consoleTextArea.setScrollTop(Double.MAX_VALUE);
                output.selectPositionCaret(output.getLength());
                output.deselect();
            }
        });


        outStream = new Output()
        PrintStream o = new PrintStream(outStream)
        System.setOut(o)
        System.setProperty("java.util.logging.SimpleFormatter.format","%1\$tc %4\$s: %5\$s%6\$s%n")
        handler = new StreamHandler(o,new SimpleFormatter())
        handler.setLevel(Level.ALL)
        handler.setFilter(new Filter() {
            public boolean isLoggable(LogRecord record) {
                System.out.print(handler.getFormatter().format(record))
                return false
            }
        });
        for(String loggerName : LogManager.getLogManager().getLoggerNames()) {
            LogManager.getLogManager().getLogger(loggerName).addHandler(handler)
        }

        HBox hbox = new HBox()
        hbox.setFillHeight(true)
        hbox.setSpacing(10)
        hbox.setPadding(new Insets(10,10,10,10))
        hbox.getChildren().add(title)
        hbox.getChildren().add(spacer)
        hbox.getChildren().add(startButton)
        VBox vbox = new VBox()
        vbox.setFillWidth(true)
        vbox.getChildren().add(hbox)
        vbox.getChildren().add(script)
        vbox.getChildren().add(output)

        StackPane root = new StackPane()
        root.getChildren().add(vbox)
        root.setStyle(style)
        primaryStage.setScene(new Scene(root, 800, 650))
        primaryStage.show()
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            void handle(WindowEvent event) {
                if (startButton.getText().equals("Stop"))
                    stopScript()
                stopEverything()
            }
        });

    }
}

MainApplication.main(null);
/*
Thread.sleep(1000)
gen.stop()
Thread.sleep(5000)

*/
