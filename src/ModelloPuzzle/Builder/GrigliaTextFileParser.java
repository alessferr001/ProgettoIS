package ModelloPuzzle.Builder;
import ModelloPuzzle.Builder.NC.GrigliaBuilderNOComposite;
import ModelloPuzzle.Componenti.Posizione;
import ModelloPuzzle.State.Operatore;
import ModelloPuzzle.State.TipologiaBlocco;

import java.io.*;
import java.util.StringTokenizer;

public class GrigliaTextFileParser {

    private final GrigliaBuilderNOComposite builder;
    private BufferedReader br;
    private String token;
    private String max_cifra;

    public GrigliaTextFileParser(File file, GrigliaBuilderNOComposite builder) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(file));
        this.builder=builder;
    }

    public String nextElement() throws IOException {
        String nuova_riga = br.readLine();
        if (nuova_riga != null) {
            StringTokenizer st1 = new StringTokenizer(nuova_riga);
            token = st1.nextToken();
        }
        return nuova_riga;
    }

    public void doParse(String nuova_riga) throws IOException {
        if(nuova_riga!=null) {
            switch (token) {
                case "Blocco:" -> readBlocco(nuova_riga);
                case "Cella:" -> readCella(nuova_riga);
                case "Griglia:" -> readStruttura(nuova_riga);
            }
        }
    }

    private void readStruttura(String string) throws IOException {

        String dimensione = null;
        StringTokenizer st=new StringTokenizer(string);
        while(st.hasMoreTokens()) {
            String token=st.nextToken();
            if(token.equals("Dimensione:")){
                dimensione = st.nextToken();
            }
            if(token.equals("MaxCifra:")){
                max_cifra=st.nextToken();
            }
        }
        assert dimensione != null;
        assert max_cifra != null;
        builder.creaStruttura(Integer.parseInt(dimensione),Integer.parseInt(max_cifra));
        String prossima_riga = nextElement();
        while(prossima_riga!=null){
            doParse(prossima_riga);
            prossima_riga=nextElement();
        }
        builder.closeStruttura();
    }

    private void readBlocco(String string) throws IOException {

        TipologiaBlocco tb;
        Operatore op;

        String id = null;
        String dimensione=null;
        String operatore=null;
        String risultato_atteso=null;

        StringTokenizer st=new StringTokenizer(string);
        while(st.hasMoreTokens()) {
            String token=st.nextToken();
            switch (token) {
                case "Blocco:" -> id = st.nextToken();
                case "Dimensione:" -> dimensione = st.nextToken();
                case "Operazione:" -> operatore = st.nextToken();
                case "Risultato_Atteso:" -> risultato_atteso = st.nextToken();
            }
        }
        assert dimensione != null;
        tb = switch (dimensione) {
            case "2" -> TipologiaBlocco.BLOCCO_DUE;
            case "3" -> TipologiaBlocco.BLOCCO_TRE;
            default -> TipologiaBlocco.BLOCCO_QUATTRO;
        };

        assert operatore != null;
        op = switch (operatore) {
            case "+" -> Operatore.ADDIZIONE;
            case "-" -> Operatore.SOTTRAZIONE;
            case "x"-> Operatore.MOLTIPLICAZIONE;
            default -> Operatore.DIVISIONE;
        };

        assert id != null;
        assert risultato_atteso != null;
        builder.creaBlocco(Integer.parseInt(id),tb,op,Integer.parseInt(risultato_atteso),Integer.parseInt(max_cifra));
        int i=0;
        while(i<tb.getDim()){
            doParse(nextElement());
            i++;
        }
        builder.closeBlocco();
    }

    private void readCella(String string){
        String id = null;
        String posizione_x=null;
        String posizione_y=null;
        String valore=null;

        StringTokenizer st = new StringTokenizer(string,"<> ");
        while(st.hasMoreTokens()) {
            String token=st.nextToken();
            switch (token) {
                case "Cella:" -> id = st.nextToken();
                case "Posizione:" -> {
                    posizione_x = st.nextToken();
                    posizione_y = st.nextToken();
                }
                case "Valore:" -> valore = st.nextToken();
            }
        }
        assert posizione_x != null;
        int x = Integer.parseInt(posizione_x);
        int y = Integer.parseInt(posizione_y);

        Posizione pos = new Posizione(x,y);
        Integer value=null;
        assert id != null;
        assert valore != null;
        if(!(valore.equals("null"))){ value=Integer.parseInt(valore);}
        builder.aggiungiCella(Integer.parseInt(id),value,pos);
    }
}
