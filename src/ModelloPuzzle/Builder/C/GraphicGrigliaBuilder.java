package ModelloPuzzle.Builder.C;

import Grafica.GrigliaPanel;
import ModelloPuzzle.Componenti.*;


public class GraphicGrigliaBuilder implements GrigliaBuilderComposite {
//Crea una rappresentazione Grafica della struttura, abbiamo già una struttura composita gerarchica
    private GrigliaPanel grigliaPanel;//Product
    
    public GrigliaPanel getGrigliaPanel() {
        return grigliaPanel;
    }

    @Override
    public void creaStruttura(GrigliaIF griglia) {
        grigliaPanel = new GrigliaPanel(griglia.getN());
    }

    @Override
    public void creaBlocco(Blocco blocco) {
        grigliaPanel.add(blocco);
    }

    @Override
    public void aggiungiCella(Cella c) {
        grigliaPanel.add(c);
    }
}
