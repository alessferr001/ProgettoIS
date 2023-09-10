package ModelloPuzzle.Componenti;

import ModelloPuzzle.Visitor.GrigliaVisitorIF;
import Grafica.GrigliaElementListener;
import java.awt.geom.Point2D;

public interface GrigliaElement{

    Integer getValue();

    void accept(GrigliaVisitorIF visitor);

    default CompositeGrigliaElement asComposite(){return null;}

    void addObjectListener(GrigliaElementListener l);

    default boolean contains(Point2D p){
        return false;
    }

}
