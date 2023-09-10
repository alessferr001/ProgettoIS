package ModelloPuzzle.controller;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.GrigliaIF;
import ModelloPuzzle.Mediator.Componente;
import ModelloPuzzle.Mediator.MessaggioIF;

import java.io.File;
import java.io.IOException;

public interface Controller extends Componente {

    GrigliaIF getGriglia();

    void undo();

    void redo();

    void reset();

    void verificaSoluzione();

    void nuovaPartita(int n, int maxCifraInseribile);

    void abilitaControlli();

    void trovaSoluzioni();

    void mostraSoluzione(int idSoluzione);

    void effettuaInserimento(Blocco b, Cella c, Integer valore);

    void caricaDaFile(File selectedFile) throws IOException;

    void salvaSuFile(File selectedFile) throws IOException;

    void esitoInserimento(MessaggioIF esito_verifica_inserimento);

    int getNSoluzioniTrovate();

    int getMaxCifra();
}
