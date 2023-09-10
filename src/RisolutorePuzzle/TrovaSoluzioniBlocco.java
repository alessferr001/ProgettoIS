package RisolutorePuzzle;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.GrigliaElement;
import ModelloPuzzle.State.Operatore;

import java.util.ArrayList;

public class TrovaSoluzioniBlocco extends Problema<Integer,Integer> {

    private final SoluzioneBlocco soluzione_corrente;
    private final ArrayList<SoluzioneBlocco> soluzioni_trovate=new ArrayList<>();
    private final Blocco b;
    private final Integer maxCifraSelezionabile;

    public TrovaSoluzioniBlocco(Blocco b,Integer maxCifraSelezionabile){
        this.b=b;
        this.maxCifraSelezionabile=maxCifraSelezionabile;
        nr_soluzione=-1;
        num_max_soluzioni=14;
        soluzione_corrente = new SoluzioneBlocco(b.getChildrenCount());
    }
    ;

    protected Integer primoPuntoDiScelta() {return 0;}

    @Override
    protected Integer prossimoPuntoDiScelta(Integer ps) {
        return ps+1;
    }

    @Override
    protected Integer ultimoPuntoDiScelta() {
        return b.getChildrenCount()-1;
    }

    @Override
    protected Integer primaScelta(Integer ps) {
        return 1;
    }

    @Override
    protected Integer prossimaScelta(Integer integer) {
        return integer+1;
    }

    @Override
    protected Integer ultimaScelta(Integer ps) {
        return maxCifraSelezionabile;
    }

    @Override
    protected boolean assegnabile(Integer scelta, Integer puntoDiScelta) {
        if(esisteCifraUgualeAdiacente(scelta,puntoDiScelta)) return false;
        if (puntoDiScelta == b.getDimensione()-1){
            return verificaRisultato(scelta);
        }
        return true;
    }

    private boolean esisteCifraUgualeAdiacente(int cifra, int id_cella) {
        Cella cur = (Cella) b.getChild(id_cella);
        for (GrigliaElement se : b){
            Cella c = (Cella) se;
            if(c.getId()!=cur.getId() && soluzione_corrente.getValori().get(c.getId()) == cifra && adiacenti(cur,c) ){
                return true;
            }
        }
        return false;
    }

    private boolean adiacenti(Cella c1, Cella c2) {
        if(c1.getPos().getX()==c2.getPos().getX() || c1.getPos().getY()==c2.getPos().getY()) return true;
        return false;
    }

    private boolean verificaRisultato(int temp_num) {
        Operatore op = b.getOperatore();
        ArrayList<Integer> valori = new ArrayList<>();
        valori.add(temp_num);

        for (GrigliaElement se : b){
            Cella c = (Cella) se;
            Integer operando=soluzione_corrente.getValori().get(c.getId());
            if(operando!=0) valori.add(operando);
        }
        return op.verificaRisultato(valori,b.getRisultato());
    }


    @Override
    protected void assegna(Integer scelta, Integer puntoDiScelta) {
        soluzione_corrente.aggiungiValoreCella(puntoDiScelta,scelta);
    }

    @Override
    protected void deassegna(Integer scelta, Integer puntoDiScelta) {
        soluzione_corrente.aggiungiValoreCella(puntoDiScelta,0);
    }

    @Override
    protected Integer precedentePuntoDiScelta(Integer puntoDiScelta) {//cella precedente
        return puntoDiScelta-1;
    }

    @Override
    protected Integer ultimaSceltaAssegnataA(Integer puntoDiScelta) {
        return soluzione_corrente.getValori().get(puntoDiScelta);
    }

    @Override
    protected void scriviSoluzione(int nr_sol) {
        SoluzioneBlocco s = new SoluzioneBlocco(soluzione_corrente.size());
        int i = 0;
        while(i < soluzione_corrente.size()){
            s.aggiungiValoreCella(i,soluzione_corrente.getValori().get(i));
            i++;
        }
        soluzioni_trovate.add(s);
    }

    public ArrayList<SoluzioneBlocco> getSoluzioniTrovate() {
        return soluzioni_trovate;
    }
}
