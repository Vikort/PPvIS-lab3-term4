package sample;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class MainScene {
    private Scene scene;
    private Charts charts;
    private Table table;
    private GraphicController graphicController;
    private Button startButton;
    private Button stopButton;
    private RadioButton firstGraphic;
    private RadioButton secondGraphic;
    private TextField aParameter;
    private TextField xParameter;
    private ToggleGroup toggleGroup;
    private Timer timer = null;


    MainScene(){
        charts = new Charts();
        table = new Table(FXCollections.observableList(new ArrayList<>()));
        aParameter = new TextField();
        xParameter = new TextField();
        graphicController = new GraphicController(new Graphic(new ArrayList<>()));
        startButton = new Button("start");
        stopButton = new Button("stop");
        startButton.setMinWidth(150);
        stopButton.setMinWidth(150);
        firstGraphic = new RadioButton("f(x)= x+5");
        secondGraphic = new RadioButton();
        toggleGroup = new ToggleGroup();
        firstGraphic.setToggleGroup(toggleGroup);
        secondGraphic.setToggleGroup(toggleGroup);
        secondGraphic.setGraphic(ImageUtil.getImage("/secondGraphic.png"));
        aParameter.setPromptText("enter a");
        xParameter.setPromptText("enter x");
        buttonsActions();
        HBox parameters = new HBox(xParameter,aParameter);
        parameters.setSpacing(5);
        parameters.setAlignment(Pos.CENTER);
        HBox buttons = new HBox(startButton,stopButton);
        buttons.setSpacing(5);
        buttons.setAlignment(Pos.CENTER);
        VBox form = new VBox(parameters,buttons,table.getTable());
        form.setSpacing(10);
        VBox vBox = new VBox(form,firstGraphic,secondGraphic);
        HBox hBox = new HBox(vBox, charts.getGroup());
        hBox.setSpacing(30);
        scene = new Scene(hBox,1050,800);
    }

    private void buttonsActions(){
        startButton.setOnAction(e -> {
            if(startButton.getText().equals("continue")){
                startButton.setText("start");
                stopButton.setText("stop");
            }

            if(toggleGroup.getSelectedToggle() == firstGraphic){
                if(timer != null){
                    timer.cancel();
                    timer = null;
                }
                timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Dot firstDot = graphicController.getLastDot();
                        if(graphicController.getCount() != 0)
                            charts.firstMoveTo(firstDot.getX(),firstDot.getY());
                        double x = (firstDot.getX() == 0 && graphicController.getCount() == 0) ?
                            -31: firstDot.getX();
                        double y = graphicController.firstFunction(x + 1);
                        Dot secondDot = graphicController.getLastDot();
                        if(graphicController.getCount() == 1)
                            firstDot = secondDot;
                        charts.firstLine(firstDot.getX(),-firstDot.getY(),secondDot.getX(),-secondDot.getY());
                        table.addItem(new DotItem(secondDot.getX(),y));
                    }
                };
                timer.schedule(task,0,100L);
            }else if(toggleGroup.getSelectedToggle() == secondGraphic){
                if(timer != null){
                    timer.cancel();
                    timer = null;
                }
                timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Dot firstDot = graphicController.getLastDot();
                        if(graphicController.getCount() != 0)
                            charts.secondMoveTo(firstDot.getX(),firstDot.getY());
                        double temp = (xParameter.getText() != "") ? Double.parseDouble(xParameter.getText()): 0;
                        double x = (firstDot.getX() == 0) ? temp - 0.1: firstDot.getX();
                        double y = graphicController.secondFunction(x + 0.1,Integer.parseInt(aParameter.getText()));
                        Dot secondDot = graphicController.getLastDot();
                        if(graphicController.getCount() == 1)
                            firstDot = secondDot;
                        charts.secondLine(firstDot.getX(),firstDot.getY(),secondDot.getX(),secondDot.getY());
                        table.addItem(new DotItem(secondDot.getX(),y));
                    }
                };
                timer.schedule(task,0,100L);
            }
        });
        stopButton.setOnAction(e -> {
            if(stopButton.getText().equals("stop")){
                if(timer != null){
                    timer.cancel();
                    timer = null;
                }
                stopButton.setText("clear");
                startButton.setText("continue");
            }else if(stopButton.getText().equals("clear")){
                graphicController.clear();
                table.clear();
                stopButton.setText("stop");
                startButton.setText("start");
            }
        });
    }

    public Scene getScene(){
        return scene;
    }
}
