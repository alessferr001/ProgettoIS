package ModelloPuzzle.Componenti;

import Grafica.GrigliaElementListener;

public interface CompositeGrigliaElement extends GrigliaElement,
        Iterable<GrigliaElement> {

        GrigliaElement getChild(int i);

        void addChild(GrigliaElement el);

        int getChildrenCount();

        CompositeGrigliaElement asComposite();

        @Override
        default void addObjectListener(GrigliaElementListener l){}
}
