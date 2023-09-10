package Grafica;

import ModelloPuzzle.Componenti.GrigliaElement;

import java.util.HashMap;
import java.util.Map;

public enum GrigliaElementViewFactory {

    FACTORY;
    private final Map<Class<? extends GrigliaElement>, GrigliaElementView> viewMap = new HashMap<>();

    GrigliaElementView createView(GrigliaElement se) {
        return viewMap.get(se.getClass());
    }
    public void installView(Class<? extends GrigliaElement> clazz, GrigliaElementView view) {
        viewMap.put(clazz, view);
    }
}
