package ModelloPuzzle.Componenti;

import java.util.ArrayList;
import java.util.Set;

public interface GrigliaImpl {

    int getN();

    int getMaxCifraInseribile();

    void stampa();

    Posizione getPrimaPosizioneLibera();

    void azzeraInserimenti();

    void assegnaBlocco(Posizione pos, int id_blocco);

    Set<Posizione> verificaRipetizioni(Integer valore, Posizione pos);

    void deassegnaBlocco(Posizione pos);

    void annullaInserimento(Posizione pos);

    void effettuaInserimento(Posizione p, Integer valore);

    ArrayList<GrigliaElement> getBlocchi();

    void setBlocchi(ArrayList<GrigliaElement> blocchi);

    void aggiungiSoluzione();

    void aggiorna();

    int getNSoluzioni();

    void rappresentaSoluzione(GrigliaIF grigliaIF, int id_soluzione);

    boolean esisteBlocco(Posizione p);

    void azzeraSoluzioni();
}
