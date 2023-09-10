package ModelloPuzzle.Componenti;

import ModelloPuzzle.State.Operatore;
import ModelloPuzzle.State.StatoBlocco;
import ModelloPuzzle.State.StatoBloccoIF;
import ModelloPuzzle.State.TipologiaBlocco;

import java.util.*;
public class GeneratoreGriglia {


    //Strutture dati di supporto
    private final ArrayList<Blocco> blocchi;
    private final ArrayList<Blocco> assegnati;
    private final ArrayList<GrigliaElement> blocchi_finals;

    private GrigliaImpl impl;

    //Variabili statiche comuni
    public static final Random random = new Random();
    private static final Operatore[] operatores = Operatore.values();

    //Parametri supporto
    private Posizione prima_pos_libera=new Posizione(0,0);//prima posizione libera

    public GeneratoreGriglia(GrigliaImpl impl){
        this.impl=impl;
        blocchi_finals=new ArrayList<>();
        blocchi = new ArrayList<>();
        assegnati = new ArrayList<>();
        create();

    }

    private static Operatore scegliOperatoreRandom(){
        return operatores[random.nextInt(GeneratoreGriglia.operatores.length)];
    }

    private void setBlocchi(){
        int n_blocchi4;
        int n_blocchi3;
        int n_blocchi2;

        switch (impl.getN()) {
            case 3 -> {
                n_blocchi4 = 1;
                n_blocchi3 = 1;
                n_blocchi2 = 1;
            }
            case 4 -> {
                n_blocchi4 = 1;
                n_blocchi3 = 2;
                n_blocchi2 = 3;
            }
            case 5 -> {
                n_blocchi4 = 2;
                n_blocchi3 = 3;
                n_blocchi2 = 4;
            }
            default-> {
                n_blocchi4 = 3;
                n_blocchi3 = 4;
                n_blocchi2 = 6;
            }
        }

        int i=0;
        int id_blocco=1;
        //Blocchi da 2
        while(i<n_blocchi2){
            Operatore op = scegliOperatoreRandom();
            int ris= TipologiaBlocco.BLOCCO_DUE.creaRisultato(op,impl.getMaxCifraInseribile());
            Blocco b=new Blocco(id_blocco,TipologiaBlocco.BLOCCO_DUE,impl.getMaxCifraInseribile());
            b.setRisultato(ris); b.setOperatore(op);

            setCelle(b);
            blocchi.add(b);
            blocchi_finals.add(b);
            i++;
            id_blocco++;
        }

        //Blocchi da 3
        i=0;
        while(i<n_blocchi3){
            Operatore op = scegliOperatoreRandom();
            int ris= TipologiaBlocco.BLOCCO_TRE.creaRisultato(op,impl.getMaxCifraInseribile());
            Blocco b=new Blocco(id_blocco,TipologiaBlocco.BLOCCO_TRE,impl.getMaxCifraInseribile());
            b.setRisultato(ris); b.setOperatore(op);

            setCelle(b);
            blocchi.add(b);
            blocchi_finals.add(b);
            i++;
            id_blocco++;
        }

        //Blocchi da 4
        i=0;
        while(i<n_blocchi4){
            Operatore op = scegliOperatoreRandom();
            int ris= TipologiaBlocco.BLOCCO_QUATTRO.creaRisultato(op,impl.getMaxCifraInseribile());
            Blocco b=new Blocco(id_blocco,TipologiaBlocco.BLOCCO_QUATTRO,impl.getMaxCifraInseribile());
            b.setRisultato(ris); b.setOperatore(op);

            setCelle(b);
            blocchi.add(b);
            blocchi_finals.add(b);
            i++;
            id_blocco++;
        }

        impl.setBlocchi(blocchi_finals);
    }

    private void setCelle(Blocco b) {
        int n_celle = b.getDimensione();
        int i = 0;
        while(i<n_celle){
            Cella c = new Cella(i);
            c.setPos(null);
            b.addChild(c);
            i++;
        }
    }

    private Blocco scegliBloccoRandom() {
        return blocchi.get(random.nextInt(blocchi.size()));
    }

    private void create(){

        setBlocchi();

        while(!(blocchi.isEmpty())){
            Blocco b= scegliBloccoRandom();
            StatoBloccoIF posizionato = posizionaBlocco(b);
            posizionato.esegui(this,b);//State
        }
        calcoloSoluzioniBlocchi();
    }

    private boolean creaPercorso(Blocco b) {//si può iterare sulle celle del blocco

        int id_cella=0;
        int i=1;
        boolean cella_aggiunta;

        assegna(prima_pos_libera.getX(),prima_pos_libera.getY(),b.getId());
        ((Cella) b.getChild(id_cella)).setPos(new Posizione(prima_pos_libera.getX(),prima_pos_libera.getY()));


        id_cella++;
        while (i<b.getDimensione()){
            cella_aggiunta=aggiungiCella(b);
            if(!cella_aggiunta){
                return false;//caso in cui abbiamo il limite di spazio
            }
            ((Cella) b.getChild(id_cella)).setPos(new Posizione(prima_pos_libera.getX(),prima_pos_libera.getY()));
            id_cella++;
            i++;
        }
        return true;
    }

    public void aggiornaPosizione(){
        prima_pos_libera = impl.getPrimaPosizioneLibera();
    }

    public StatoBloccoIF posizionaBlocco(Blocco b) {
        if (!creaPercorso(b)) return StatoBlocco.NO_SPAZIO;
        return StatoBlocco.POSIZIONATO;
    }

    private boolean aggiungiCella(Blocco b) {
        boolean[] direzioni = new boolean[4];
        controllaDirezioni(direzioni);
        int dir = scegliDirezione(direzioni);
        switch (dir) {
            case 0 -> {
                assegna(prima_pos_libera.getX()- 1, prima_pos_libera.getY(), b.getId());
                prima_pos_libera.setX(prima_pos_libera.getX()-1);
                return true;
            } //sopra
            case 1 -> {
                assegna(prima_pos_libera.getX() + 1, prima_pos_libera.getY(), b.getId());
                prima_pos_libera.setX(prima_pos_libera.getX()+1);
                return true;
            }//sotto
            case 2 -> {
                assegna(prima_pos_libera.getX(), prima_pos_libera.getY()+ 1, b.getId());
                prima_pos_libera.setY(prima_pos_libera.getY()+1);
                return true;
            }//destra
            case 3 -> {
                assegna(prima_pos_libera.getX(), prima_pos_libera.getY() - 1, b.getId());
                prima_pos_libera.setY(prima_pos_libera.getY()-1);
                return true;
            }//sinistra
            default -> { return false;}
        }
    }

    private void assegna(int riga, int colonna, int idBlocco) {
        impl.assegnaBlocco(new Posizione(riga,colonna),idBlocco);
    }

    private int scegliDirezione(boolean[] direzioni) {
        int count=0;
        int[] temp=new int[4];
        for(int i=0; i<direzioni.length; i++){
            if(direzioni[i]){
                temp[count]=i;
                count++;
            }
        }
        if(count==0) return -1;
        int[] finali = new int[count];
        System.arraycopy(temp, 0, finali, 0, finali.length);

        return finali[random.nextInt(finali.length)];
    }

    private void controllaDirezioni(boolean[] direzioni) {
        //Controllo sopra
        if(prima_pos_libera.getX()-1 >= 0 && !(impl.esisteBlocco(new Posizione(prima_pos_libera.getX()-1, prima_pos_libera.getY())))){
            direzioni[0]=true;
        }
        //Controllo sotto
        if (prima_pos_libera.getX()+1< impl.getN() && !(impl.esisteBlocco(new Posizione(prima_pos_libera.getX()+1, prima_pos_libera.getY())))) {
            direzioni[1]=true;
        }
        //Controllo destra
        if (prima_pos_libera.getY()+1< impl.getN() && !(impl.esisteBlocco(new Posizione(prima_pos_libera.getX(), prima_pos_libera.getY()+1)))) {
            direzioni[2]=true;
        }
        //Controllo sinistra
        if (prima_pos_libera.getY()-1>=0 && !(impl.esisteBlocco(new Posizione(prima_pos_libera.getX(), prima_pos_libera.getY()-1)))) {
            direzioni[3]=true;
        }
    }

    public void deassegnaBlocco(Blocco b) {
        for(GrigliaElement se: b){
            Cella c = (Cella) se;
            c.setValore(null);
            if(c.getPos()!=null) {
                Posizione p = c.getPos();
                impl.deassegnaBlocco(p);
                impl.annullaInserimento(p);
                c.setPos(null);
            }
        }
    }

    private void calcoloSoluzioniBlocchi(){
        for(GrigliaElement ge : blocchi_finals){
            Blocco b = (Blocco) ge;
            while(b.getSoluzioni().isEmpty()){
                b.setOperatore(GeneratoreGriglia.scegliOperatoreRandom());
                b.setRisultato(b.getTb().creaRisultato(b.getOperatore(), impl.getMaxCifraInseribile()));
            }
        }
    }

    public ArrayList<Blocco> getBlocchi() {
        return blocchi;
    }

    public ArrayList<Blocco> getAssegnati() {
        return assegnati;
    }

}
