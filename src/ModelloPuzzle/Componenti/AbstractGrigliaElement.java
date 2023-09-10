package ModelloPuzzle.Componenti;

import Grafica.GrigliaElementListener;

import java.util.LinkedList;

abstract class AbstractGrigliaElement implements GrigliaElement{

    private LinkedList<GrigliaElementListener> listeners = new LinkedList<>();

    @Override
    public void addObjectListener(GrigliaElementListener gel) {
        if (listeners.contains(gel))
            return;
        listeners.add(gel);
    }

    protected void notifyListeners() {
        for (GrigliaElementListener gel : listeners)
            gel.graphicChanged();
    }


}
