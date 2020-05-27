package sample;

import java.util.ArrayList;

public class Graphic {
    private ArrayList<Dot> database;

    Graphic(ArrayList<Dot> list){
        database = new ArrayList<>(list);
    }

    public void addElement(Dot dot)
    {
        for(Dot d: database){
            if(d.getX() == dot.getX())
                return;
        }
        database.add(dot);
    }

    public int getCount(){
        return database.size();
    }

    public void clear(){
        database.clear();
    }

    public ArrayList<Dot> getDatabase() {
        return database;
    }

    public DotItem dotIntoItem(Dot dot){
        return new DotItem(dot.getX(),dot.getY());
    }
}
