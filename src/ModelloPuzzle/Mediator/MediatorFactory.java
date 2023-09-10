package ModelloPuzzle.Mediator;

import ModelloPuzzle.Componenti.GrigliaProxy;
import ModelloPuzzle.controller.GrigliaControllerFactory;

import java.util.HashMap;
import java.util.Map;

public enum MediatorFactory {
//supporto alla comunicazione con il controller
    FACTORY;
    private final Map<Componente, Mediatore> mediatoreMap = new HashMap<>();

    public Mediatore getMediatore(Componente c){
        if(mediatoreMap.containsKey(c)) return mediatoreMap.get(c);
        installMediatore(c);
        return mediatoreMap.get(c);
    }

    private void installMediatore(Componente c){
        Mediatore m = switch (c.getClass().getSimpleName()) {
            case "GrigliaProxy" -> new MediatoreController_Proxy(new GrigliaControllerFactory().getController(), (GrigliaProxy) c);//il controller è sempre e solo 1
            default -> null;
        };
        mediatoreMap.put(c,m);
    }


}
