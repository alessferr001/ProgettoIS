import Grafica.BloccoView;
import Grafica.CellaView;
import Grafica.FinestraIniziale;
import Grafica.GrigliaElementViewFactory;
import ModelloPuzzle.Componenti.*;


public class Main {
    public static void main(String[] args) {
        GrigliaElementViewFactory.FACTORY.installView(Blocco.class, new BloccoView());
        GrigliaElementViewFactory.FACTORY.installView(Cella.class, new CellaView());
        FinestraIniziale f = new FinestraIniziale();


    }

}