package ModelloPuzzle.Builder.C;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.GrigliaIF;

import java.io.*;

public class GrigliaStreamBuilder implements GrigliaBuilderComposite {
//Rappresenta come flusso di dati in uscita
    private final PrintWriter pw;

    public GrigliaStreamBuilder(PrintStream stream){
        pw = new PrintWriter(stream);
    }

    @Override
    public void creaStruttura(GrigliaIF struttura) {
        pw.println(struttura);
    }

    @Override
    public void closeStruttura() {
        pw.close();
    }

    @Override
    public void creaBlocco(Blocco blocco) {
        pw.println(blocco);
    }

    @Override
    public void aggiungiCella(Cella c) {
        pw.println(c);
    }

}
