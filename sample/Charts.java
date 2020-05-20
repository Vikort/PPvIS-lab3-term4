package sample;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

public class Charts {
    private VBox root;
    private Canvas canvas;
    private GraphicsContext gc;
    private double width = 600;
    private double height = 600;
    private double scale = 1; // увеличение
    private int countX = 0;  //смещение по Х
    private int countY = 0; // смещение по Y
    private Button plusScale;
    private Button minusScale;
    private Button leftButton;
    private Button upButton;
    private Button rightButton;
    private Button downButton;


    Charts(){
        root = new VBox();

        canvas = new Canvas(600,600);
        gc = canvas.getGraphicsContext2D();

        drawAxis(gc, Color.BLACK);
        drawGrid(gc,600,600, (int) (10*scale));
        drawSign(gc, countX,countY);

        plusScale = new Button("+");
        minusScale = new Button("-");

        leftButton = new Button("←");
        upButton = new Button("↑");
        rightButton = new Button("→");
        downButton = new Button("↓");

        upButton.setMinWidth(100);
        downButton.setMinWidth(100);
        leftButton.setMinWidth(50);
        rightButton.setMinWidth(50);

        HBox sideButton = new HBox(leftButton,rightButton);
        sideButton.setAlignment(Pos.CENTER);

        VBox movementButtons = new VBox(upButton,sideButton,downButton);
        movementButtons.setAlignment(Pos.CENTER);

        plusScale.setMinWidth(100);
        minusScale.setMinWidth(100);
        HBox buttons = new HBox(plusScale,minusScale);
        buttons.setAlignment(Pos.CENTER);

        buttonsActions();

        Line firstLine = new Line(0,0,30,0);
        firstLine.setStroke(Color.RED);
        Text firstText = new Text("\t1 graphic");
        HBox firstTip = new HBox(firstLine,firstText);
        firstTip.setAlignment(Pos.CENTER);

        Line secondLine = new Line(0,0,30,0);
        secondLine.setStroke(Color.GREEN);
        Text secondText = new Text("\t2 graphic");
        HBox secondTip = new HBox(secondLine,secondText);
        secondTip.setAlignment(Pos.CENTER);

        root.getChildren().addAll(canvas,firstTip,secondTip,buttons, movementButtons);
        root.setSpacing(5);
    }

    private void drawAxis(GraphicsContext gc, Color color){
        drawArrow(gc, -countX * 10,300,600-countX * 10,300,color);// draw xAxis
        gc.fillText("x",585-countX * 10,315);
        drawArrow(gc,300,600-countY*10,300,-countY*10,color); // draw yAxis
        gc.fillText("y",285,15-countY*10);
    }

    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(1);
        gc.strokeLine(x1,y1,x2,y2);
        if(x1==x2){
            gc.strokeLine(x2,y2,x2-5,y2+10);
            gc.strokeLine(x2,y2,x2+5,y2+10);
        }
        else if(y1==y2){
            gc.strokeLine(x2,y2,x2-10,y2-5);
            gc.strokeLine(x2,y2,x2-10,y2+5);
        }
    }

    private void drawGrid(GraphicsContext gc,int x,int y, int scale){
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.1);
        for(double i = -countX * 10; i <= x - countX*10; i+=scale){
            gc.strokeLine(i,-countY*10,i,y-countY*10);
        }

        for(double i = -countY*10; i <= y - countY*10; i+=scale){
            gc.strokeLine(-countX*10,i,x-countX*10,i);
        }
    }

    private void drawSign(GraphicsContext gc, int countX, int countY){

        gc.setFill(Color.BLACK);

        gc.setFont(Font.font(8));
        for(int i = (int) (width - (scale*10)-countX*10); i > -countX*10; i-=scale*10*4){
            gc.fillText(String.valueOf((i-300)/10),i,height/2 + 10);
        }

        for(int i = (int) (scale*10-countY*10); i < height-countY*10; i+=scale*10*4){
            gc.fillText(String.valueOf(-(i-300)/10),width/2 - 15,i);
        }

        gc.fillText("0",width/2 - 10,height/2 + 10);
    }

    private void buttonsActions(){
        plusScale.setOnAction(e->{
            gc.scale(1.25,1.25);
            scale*= 1.25;
            redraw(Color.DARKBLUE);
        });

        minusScale.setOnAction(e->{
            gc.scale(0.8,0.8);
            scale *= 0.8;
            redraw(Color.DARKBLUE);
        });

        leftButton.setOnAction(e->{
            gc.translate(10,0);
            countX += 1;
            redraw(Color.DARKBLUE);
        });

        rightButton.setOnAction(e->{
            gc.translate(-10,0);
            countX -= 1;
            redraw(Color.DARKBLUE);
        });

        upButton.setOnAction(e->{
            gc.translate(0,10);
            countY += 1;
            redraw(Color.DARKBLUE);
        });

        downButton.setOnAction(e->{
            gc.translate(0,-10);
            countY -= 1;
            redraw(Color.DARKBLUE);
        });
    }

    private void redraw(Color color){
        gc.clearRect(-countX*10,-countY*10,600,600);
        drawAxis(gc, color);
        drawGrid(gc,600,600, (int) (10*scale));
        drawSign(gc, countX, countY);
    }

    public VBox getGroup(){
        return root;
    }

    public void firstMoveTo(double x, double y){
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.moveTo(x,y);
    }

    public void secondMoveTo(double x, double y){
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.moveTo(x,y);
    }

    public void firstLine(double startX, double startY, double endX, double endY){
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeLine((startX+30)*10,(startY+30)*10,(endX+30)*10,(endY+30)*10);
    }

    public void secondLine(double startX, double startY, double endX, double endY){
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.strokeLine((startX+30)*10,(startY+30)*10,(endX+30)*10,(endY+30)*10);
    }

    public void clear(){
        gc.clearRect(0,0,width,height);
    }
}
