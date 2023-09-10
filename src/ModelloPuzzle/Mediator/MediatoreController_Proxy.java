package ModelloPuzzle.Mediator;

import ModelloPuzzle.Componenti.GrigliaProxy;
import ModelloPuzzle.controller.Controller;

class MediatoreController_Proxy implements Mediatore {

    private Controller controller;
    private GrigliaProxy proxy;

    public MediatoreController_Proxy(Controller sc, GrigliaProxy gp){
        controller=sc;
        proxy=gp;
    }

    @Override
    public void notifica(Componente c, MessaggioIF messaggio) {
        if(c instanceof GrigliaProxy){
            controller.esitoInserimento(messaggio);
        }
    }
}
