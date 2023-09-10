package ModelloPuzzle.Componenti;
import ModelloPuzzle.Visitor.GrigliaVisitorIF;
import ModelloPuzzle.Mediator.MessaggioIF;

public interface GrigliaIF extends CompositeGrigliaElement {

    void accept(GrigliaVisitorIF visitor);

    void stampa();
    
    int getMaxCifraInseribile();
    
    void reset();

    boolean esisteRipetizione(Integer valore,Posizione pos);

    MessaggioIF verificaSoluzione();

    void aggiornaGriglia();

    int getNSoluzioni();

    boolean effettuaInserimento(Blocco b, Cella cella, Integer valore,boolean fromTrovaSoluzioni);

    void rappresentaSoluzione(int idSoluzione);

    boolean isValida();

    int getN();

    void aggiungiSoluzione();

    void annullaInserimento(Cella c,boolean fromTrovaSoluzioni);

    void azzeraSoluzioni();
}
