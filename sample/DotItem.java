package sample;

import javafx.beans.property.SimpleDoubleProperty;

public class DotItem {
    private SimpleDoubleProperty x;
    private SimpleDoubleProperty y;

    public DotItem(double x, double y){
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
    }

    public double getX() {return x.get();}
    public void setX(double x) {this.x.set(x);}

    public double getY() {return y.get();}
    public void setY(double y) {this.y.set(y);}
}
