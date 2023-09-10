package ModelloPuzzle.controller;

import ModelloPuzzle.Builder.*;
import ModelloPuzzle.Builder.C.GrigliaBuilderComposite;
import ModelloPuzzle.Builder.C.GrigliaStreamBuilder;
import ModelloPuzzle.Builder.NC.CompositeGrigliaBuilder;
import ModelloPuzzle.Componenti.*;
import ModelloPuzzle.Mediator.MessaggioIF;
import ModelloPuzzle.Visitor.DirectorGrigliaVisitor;
import ModelloPuzzle.Visitor.GrigliaVisitorIF;
import ModelloPuzzle.Comandi.*;
import Grafica.FinestraGioco;
import RisolutorePuzzle.TrovaSoluzioni;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

class GrigliaController implements Controller {

    private static Controller instance;
    private final GrigliaFactory grigliaFactory = new ConcreteGrigliaFactory();
    private GrigliaIF g = getGrigliaRisolvibile(3,9);
    private final GestoreStoriaComandi gestoreStoriaComandi = new GestoreStoriaComandi();

    private GrigliaController(){}

    public static Controller getInstance(){
        if(instance == null){
            instance = new GrigliaController();
        }
        return instance;
    }

    public GrigliaIF getGriglia() {
        return g;
    }

    public void undo() {
        gestoreStoriaComandi.undo();
    }

    public void redo() {
        gestoreStoriaComandi.redo();
    }

    public void reset() {
        g.reset();
        gestoreStoriaComandi.clear();
    }

    public void verificaSoluzione() {
        MessaggioIF esito_verifica_soluzione=g.verificaSoluzione();
        FinestraGioco f = FinestraGioco.getInstance();
        f.mostraMessaggio("Esito Verifica Soluzione",esito_verifica_soluzione.toString());
    }

    public void nuovaPartita(int n, int maxCifraInseribile) {
        g = getGrigliaRisolvibile(n,maxCifraInseribile);
    }

    public void abilitaControlli() { g = new GrigliaProxy(g); }

    public void trovaSoluzioni() {
        g.reset();
        g.azzeraSoluzioni();
        TrovaSoluzioni ts = new TrovaSoluzioni(g,10);
        ts.risolvi();
    }

    public void mostraSoluzione(int idSoluzione) {
        g.rappresentaSoluzione(idSoluzione);
    }

    public void effettuaInserimento(Blocco b, Cella c, Integer valore) {
        gestoreStoriaComandi.gestisci(new ComandoInserimento(g,b,c,valore));
    }


    public void caricaDaFile(File selectedFile) throws IOException {
        CompositeGrigliaBuilder builder = new CompositeGrigliaBuilder();
        GrigliaTextFileParser parser = new GrigliaTextFileParser(selectedFile, builder);
        parser.doParse(parser.nextElement());
        GrigliaIF griglia = builder.getStruttura();
        griglia.aggiornaGriglia();
        g = griglia;
    }

    public void salvaSuFile(File selectedFile) throws IOException {
        GrigliaBuilderComposite builder = new GrigliaStreamBuilder(new PrintStream(selectedFile));
        GrigliaVisitorIF visitor = new DirectorGrigliaVisitor(builder);
        visitor.visit(g);
    }


    public void esitoInserimento(MessaggioIF esito_verifica_inserimento){
        FinestraGioco f = FinestraGioco.getInstance();
        f.mostraMessaggio("Esito Inserimento",esito_verifica_inserimento.toString());
    }

    public int getNSoluzioniTrovate() {
        return g.getNSoluzioni();
    }

    @Override
    public int getMaxCifra() {
        return g.getMaxCifraInseribile();
    }

    private GrigliaIF getGrigliaRisolvibile(int n, int maxCifraInseribile){
        do{
            g=grigliaFactory.creaGriglia(n,maxCifraInseribile,true);
        }while(!g.isValida());
        g.reset();
        return g;
    }
}

