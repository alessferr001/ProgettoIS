package RisolutorePuzzle;

import ModelloPuzzle.Componenti.*;

import java.util.*;

public class TrovaSoluzioni extends Problema<Integer,Integer>{

    private final Map<Integer,Integer> ultime_scelte_assegnate = new HashMap<>();
    private final GrigliaIF struttura;

    @Override
    protected Integer primoPuntoDiScelta() { //blocco iniziale
        return 0;
    }

    @Override
    protected Integer prossimoPuntoDiScelta(Integer ps) {//blocco successivo
        return ps+1;
    }

    @Override
    protected Integer ultimoPuntoDiScelta() {//Ultimo blocco
        return struttura.getChildrenCount()-1;
    }

    @Override
    protected Integer primaScelta(Integer ps) {//soluzione j-esima del blocco i-esimo, quando la richiedo
        return 0;
    }

    @Override
    protected Integer prossimaScelta(Integer scelta) {//soluzione successiva del blocco i-esimo
        return scelta+1;
    }

    @Override
    protected Integer ultimaScelta(Integer ps) {
        Blocco b = (Blocco) struttura.getChild(ps);
        int size = b.getSoluzioni().size();
        if(size == 0) return 0;
        return size-1;
    }//ultima soluzione

    @Override
    protected boolean assegnabile(Integer scelta, Integer puntoDiScelta) {
        Blocco blocco_corrente = (Blocco) struttura.getChild(puntoDiScelta);
        if(blocco_corrente.getSoluzioni().isEmpty()) return false;//non succede
        SoluzioneBlocco sol_corrente_blocco = blocco_corrente.getSoluzioni().get(scelta);
        for (GrigliaElement se : blocco_corrente){
            Cella c = (Cella) se;
            Integer valore = sol_corrente_blocco.getValori().get(c.getId());
            if(struttura.esisteRipetizione(valore,c.getPos())) return false;
        }
        return true;
    }

    @Override
    protected void assegna(Integer scelta, Integer puntoDiScelta) {
        Blocco blocco_corrente = (Blocco) struttura.getChild(puntoDiScelta);
        ultime_scelte_assegnate.put(puntoDiScelta,scelta);
        SoluzioneBlocco sol_corrente_blocco = blocco_corrente.getSoluzioni().get(scelta);
        for (GrigliaElement se : blocco_corrente){
            Cella c = (Cella) se;
            int valore =sol_corrente_blocco.getValori().get(c.getId());
            struttura.effettuaInserimento(blocco_corrente,c,valore,true);
        }
    }

    @Override
    protected void deassegna(Integer scelta, Integer puntoDiScelta) {
        Blocco blocco_corrente = (Blocco) struttura.getChild(puntoDiScelta);
        ultime_scelte_assegnate.remove(puntoDiScelta);
        for (GrigliaElement se : blocco_corrente){
            Cella c = (Cella) se;
            struttura.annullaInserimento(c,true);
        }
    }

    @Override
    protected Integer precedentePuntoDiScelta(Integer puntoDiScelta) {
        return puntoDiScelta-1;
    }

    @Override
    protected Integer ultimaSceltaAssegnataA(Integer puntoDiScelta) {
        return ultime_scelte_assegnate.get(puntoDiScelta);
    }

    @Override
    protected void scriviSoluzione(int nr) {
        struttura.aggiungiSoluzione();
    }

    public TrovaSoluzioni(GrigliaIF s, int n){
        struttura=s;
        num_max_soluzioni=n;
    }

}
