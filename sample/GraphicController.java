package sample;

import java.util.ArrayList;

import static java.lang.Math.*;

public class GraphicController {
    private Graphic firstGraphic;
    private Graphic secondGraphic;
    private Boolean isFirstGraphic = false;
    private Boolean isSecondGraphic = false;

    GraphicController(){
        this.firstGraphic = new Graphic(new ArrayList<>());
        this.secondGraphic = new Graphic(new ArrayList<>());
    }

    public double firstFunction(double x){
        firstGraphic.addElement(new Dot(x,x+5));
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
        secondGraphic.addElement(new Dot(x,y));
        return y;
    }

    private double factorial(int i){
        if(i == 1) return 1;
        else return i*factorial(i-1);
    }

    public Boolean IsFirstGraphic() {
        return isFirstGraphic;
    }

    public Boolean IsSecondGraphic() {
        return isSecondGraphic;
    }

    public void setFirstGraphicActive(Boolean bool) {
        isFirstGraphic = bool;
        isSecondGraphic = !bool;
    }

    public void setSecondGraphicActive(Boolean bool) {
        isSecondGraphic = bool;
        isFirstGraphic = !bool;
    }

    public void clear(){
        firstGraphic.clear();
        secondGraphic.clear();
    }

    public Dot getFirstGraphicLastDot(){
        int i = firstGraphic.getDatabase().size();
        if(i == 0) return new Dot(0,0);
        else return firstGraphic.getDatabase().get(i-1);
    }

    public Dot getSecondGraphicLastDot(){
        int i = secondGraphic.getDatabase().size();
        if(i == 0) return new Dot(0,0);
        else return secondGraphic.getDatabase().get(i-1);
    }

//    public ArrayList<DotItem> dotsIntoItems(){
//        ArrayList<Dot> temp = firstGraphic.getDatabase();
//        ArrayList<DotItem> res = new ArrayList();
//        for (Dot dot: temp){
//            res.add(firstGraphic.dotIntoItem(dot));
//        }
//        return res;
//    }

    public int getFirstGraphicCount(){
        return firstGraphic.getCount();
    }
    public int getSecondGraphicCount(){
        return secondGraphic.getCount();
    }
}
