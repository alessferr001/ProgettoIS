package ModelloPuzzle.Comandi;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.GrigliaIF;


public class ComandoInserimento implements Comando {

    private GrigliaIF g;
    private Cella c;
    private Blocco b;
    private Integer vecchio_valore;
    private Integer nuovo_valore;

    public ComandoInserimento(GrigliaIF g,Blocco blocco, Cella c, Integer nuovo_valore){
        this.g=g;
        this.vecchio_valore=c.getValue();
        this.c=c;
        this.b=blocco;
        this.nuovo_valore=nuovo_valore;
    }

    @Override
    public boolean doIt() {
        return g.effettuaInserimento(b,c,nuovo_valore,false);
    }

    @Override
    public boolean undoIt() {
        g.effettuaInserimento(b,c,vecchio_valore,false);
        return true;
    }

}
