package ModelloPuzzle.Componenti;

import ModelloPuzzle.Visitor.GrigliaVisitorIF;

import java.awt.*;
import java.awt.geom.Point2D;

public class Cella extends AbstractGrigliaElement{

    private final int id;
    private Integer numero_inserito;
    private Posizione pos;
    private Rectangle rectangle;
    private Color color = Color.white;

    public Cella(int id){
        this.id=id;
    }


    public int getId() { return this.id; }

    public Posizione getPos() { return this.pos; }

    @Override
    public Integer getValue() {return numero_inserito;}

    public Color getColor() {
        return color;
    }


    public void setPos(Posizione posizione) { this.pos = posizione; }

    public void setValore(Integer valore){
        this.numero_inserito=valore;
        notifyListeners();
    }

    public void setColor(Color color) {
        this.color = color;
        notifyListeners();
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }


    @Override
    public boolean contains(Point2D p) {
        if(rectangle==null) return false;
        return rectangle.contains(p);
    }

    @Override
    public void accept(GrigliaVisitorIF visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString(){
        return "Cella: "+this.getId()+" Posizione: "+this.pos+" Valore: "+this.numero_inserito;
    }

}
