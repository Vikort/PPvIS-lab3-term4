package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Charts {
    private Graphic firstGraphic;
    private Graphic secondGraphic;
    private VBox root;
    private Canvas canvas;
    private GraphicsContext gc;
    private double scale = 1; // увеличение
    private int shiftX = 0;  //смещение по Х
    private int shiftY = 0; // смещение по Y
    private Button plusScale;
    private Button minusScale;
    private Button leftButton;
    private Button upButton;
    private Button rightButton;
    private Button downButton;
    private Text scaleLabel;


    Charts(){
        root = new VBox();

        firstGraphic = new Graphic(new ArrayList<>());
        secondGraphic = new Graphic(new ArrayList<>());

        canvas = new Canvas(600,600);
        gc = canvas.getGraphicsContext2D();

        drawAxis(gc, Color.BLACK);
        drawGrid(gc,600,600, (int) (10*scale));
        drawSign(gc, shiftX,shiftY);

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

        VBox tips = new VBox(firstTip,secondTip);
        tips.setAlignment(Pos.CENTER);
        tips.setSpacing(5);

        scaleLabel = new Text("x"+scale);

        HBox graphicInfo = new HBox(tips, scaleLabel);
        graphicInfo.setAlignment(Pos.CENTER);
        graphicInfo.setSpacing(25);

        root.getChildren().addAll(canvas,graphicInfo,buttons, movementButtons);
        root.setSpacing(5);
    }
    //отрисовка осей координат
    private void drawAxis(GraphicsContext gc, Color color){
        gc.setFont(Font.font(12));
        drawArrow(gc, -shiftX * 10,300,(600-shiftX * 10)/this.scale,300,color);// draw xAxis
        gc.fillText("x",(585-shiftX * 10)/this.scale,315);
        drawArrow(gc,300,(600-shiftY*10)/scale,300,-shiftY*10,color); // draw yAxis
        gc.fillText("y",285,(15-shiftY*10)/this.scale);
    }
    //отрисовка стрелок цвета color от (x1,y1) до (x2,y2) со стрелкой в конечной точке
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

    //отрисовка сетки с конечными координатами x и y и мастштабом между клетками scale
    private void drawGrid(GraphicsContext gc,int x,int y, int scale){
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.1);
        for(double i = -shiftX * 10; i <= (x - shiftX*10)/this.scale; i+=scale){
            gc.strokeLine(i,-shiftY*10,i,(y-shiftY*10)/this.scale);
        }

        for(double i = -shiftY*10; i <= (y - shiftY*10)/this.scale; i+=scale){
            gc.strokeLine(-shiftX*10,i,(x-shiftX*10)/this.scale,i);
        }
    }
    //отрисовка координат
    private void drawSign(GraphicsContext gc, int shiftX, int shiftY){

        gc.setFill(Color.BLACK);

        gc.setFont(Font.font(8));
        for(int i = (int) ((600 - (scale*10)-shiftX*10)/this.scale); i > -shiftX*10; i-=scale*10*4){
            gc.fillText(String.valueOf((i-300)/10==0?"":(i-300)/10),i,310);
        }

        for(int i = (int) (scale*10-shiftY*10); i < (600-shiftY*10)/this.scale; i+=scale*10*4){
            gc.fillText(String.valueOf(-(i-300)/10==0?"":-(i-300)/10),285,i);
        }

        gc.fillText("0",290,310);
    }

    private void buttonsActions(){
        plusScale.setOnAction(e->{
            gc.scale(1.25,1.25);
            scale*= 1.25;
            scaleLabel.setText("x" + round(scale));
            redraw(Color.BLACK);
        });

        minusScale.setOnAction(e->{
            gc.scale(0.8,0.8);
            scale *= 0.8;
            scaleLabel.setText("x" + round(scale));
            redraw(Color.BLACK);
        });

        leftButton.setOnAction(e->{
            gc.translate(10,0);
            shiftX += 1;
            redraw(Color.BLACK);
        });

        rightButton.setOnAction(e->{
            gc.translate(-10,0);
            shiftX -= 1;
            redraw(Color.BLACK);
        });

        upButton.setOnAction(e->{
            gc.translate(0,10);
            shiftY += 1;
            redraw(Color.BLACK);
        });

        downButton.setOnAction(e->{
            gc.translate(0,-10);
            shiftY -= 1;
            redraw(Color.BLACK);
        });

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY){
                    final double[] mouseBeginX = {mouseEvent.getX()};
                    final double[] mouseBeginY = {mouseEvent.getY()};
                    canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseDragEvent) {
                            double deltaX = (mouseDragEvent.getX() - mouseBeginX[0])/10;
                            double deltaY = (mouseDragEvent.getY() - mouseBeginY[0])/10;
                            if (deltaX >= 1){
                                leftButton.getOnAction().handle(new ActionEvent());
                                mouseBeginX[0] += 10;
                            }else if (deltaX <= -1){
                                rightButton.getOnAction().handle(new ActionEvent());
                                mouseBeginX[0] -= 10;
                            }
                            if (deltaY >= 1){
                                upButton.getOnAction().handle(new ActionEvent());
                                mouseBeginY[0] += 10;
                            }else if (deltaY <= -1){
                                downButton.getOnAction().handle(new ActionEvent());
                                mouseBeginY[0] -= 10;
                            }
                        }
                    });
                    canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseReleasedEvent) {
                            double deltaX = (mouseReleasedEvent.getX() - mouseBeginX[0])/10;
                            double deltaY = (mouseReleasedEvent.getY() - mouseBeginY[0])/10;
                            if (deltaX >= 1){
                                leftButton.getOnAction().handle(new ActionEvent());
                                mouseBeginX[0] += 10;
                            }else if (deltaX <= -1){
                                rightButton.getOnAction().handle(new ActionEvent());
                                mouseBeginX[0] -= 10;
                            }
                            if (deltaY >= 1){
                                upButton.getOnAction().handle(new ActionEvent());
                                mouseBeginY[0] += 10;
                            }else if (deltaY <= -1){
                                downButton.getOnAction().handle(new ActionEvent());
                                mouseBeginY[0] -= 10;
                            }
                        }
                    });
                }
            }
        });
    }
    //перерисовка видимой области
    private void redraw(Color color){
        gc.clearRect(-shiftX*10/scale,-shiftY*10/scale,10000,10000);
        drawAxis(gc, color);
        drawGrid(gc,600,600, (int) (10*scale));
        drawSign(gc, shiftX, shiftY);
        redrawGraphics(gc);
    }

    private void redrawGraphics(GraphicsContext gc){
        if(firstGraphic.getCount()!=0){
            ArrayList<Dot> fg = firstGraphic.getDatabase();
            for(int i = 0; i < firstGraphic.getCount()-1; i++){
                Dot firstDot = fg.get(i);
                Dot secondDot = fg.get(i+1);
                firstMoveTo(firstDot.getX(),firstDot.getY());
                firstLine(firstDot.getX(),firstDot.getY(),secondDot.getX(),secondDot.getY());
            }
        }
        if(secondGraphic.getCount()!=0){
            ArrayList<Dot> fg = secondGraphic.getDatabase();
            for(int i = 0; i < secondGraphic.getCount()-1; i++){
                Dot firstDot = fg.get(i);
                Dot secondDot = fg.get(i+1);
                secondMoveTo(firstDot.getX(),firstDot.getY());
                secondLine(firstDot.getX(),firstDot.getY(),secondDot.getX(),secondDot.getY());
            }
        }
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
        firstGraphic.addElement(new Dot(endX,endY));
    }

    public void secondLine(double startX, double startY, double endX, double endY){
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.strokeLine((startX+30)*10,(startY+30)*10,(endX+30)*10,(endY+30)*10);
        secondGraphic.addElement(new Dot(endX,endY));
    }

    public void clear(){
        firstGraphic.clear();
        secondGraphic.clear();
        this.scale = 1;
        this.shiftY = 0;
        this.shiftX = 0;
        gc.clearRect(-shiftX*10,-shiftY*10,10000,10000);
        drawAxis(gc, Color.BLACK);
        drawGrid(gc,600,600, (int) (10*scale));
        drawSign(gc, shiftX, shiftY);
    }

    private EventHandler<ScrollEvent> scroll = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent scrollEvent) {
            if(scrollEvent.getDeltaY() > 0){
                plusScale.getOnAction().handle(new ActionEvent());
            } else if (scrollEvent.getDeltaY() < 0){
                minusScale.getOnAction().handle(new ActionEvent());
            }
        }
    };

    public void setScrollHandler(){
        canvas.addEventHandler(ScrollEvent.SCROLL,scroll);
    }

    public void removeScrollHandler(){
        canvas.removeEventHandler(ScrollEvent.SCROLL,scroll);
    }

    private double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
