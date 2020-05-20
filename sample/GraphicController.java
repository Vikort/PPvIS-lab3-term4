package sample;

import java.util.ArrayList;

import static java.lang.Math.*;

public class GraphicController {
    private Graphic graphic;
    private Boolean firstGraphic = false;
    private Boolean secondGraphic = false;

    GraphicController(Graphic graphic){
        this.graphic = graphic;
    }

    public double firstFunction(double x){
        graphic.addElement(new Dot(x,x+5));
        return x + 5;
    }

    public double secondFunction(double x, double a){
        double y = 0;
        double temp = 1;
        double e = 0.0001;
        int i = 1;
        while(temp > e){
            double dividend  = sin(i*(pow(x,2)+a));
            double divider = factorial(i);
            temp = dividend/divider;
            y += temp;
            ++i;
        }
        graphic.addElement(new Dot(x,y));
        return y;
    }

    private double factorial(int i){
        if(i == 1) return 1;
        else return i*factorial(i-1);
    }

    public Boolean getFirstGraphic() {
        return firstGraphic;
    }

    public Boolean getSecondGraphic() {
        return secondGraphic;
    }

    public void clear(){
        System.out.println(graphic.getCount());
        graphic.clear();
    }

    public Dot getLastDot(){
        int i = graphic.getDatabase().size();
        if(i == 0) return new Dot(0,0);
        else return graphic.getDatabase().get(i-1);
    }
    public void addDot(Dot dot){
        graphic.addElement(dot);
    }

    public ArrayList<DotItem> dotsIntoItems(){
        ArrayList<Dot> temp = graphic.getDatabase();
        ArrayList<DotItem> res = new ArrayList();
        for (Dot dot: temp){
            res.add(graphic.dotIntoItem(dot));
        }
        return res;
    }

    public int getCount(){
        return graphic.getCount();
    }
}
