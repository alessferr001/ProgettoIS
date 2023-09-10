package ModelloPuzzle.Componenti;

import ModelloPuzzle.State.Operatore;
import ModelloPuzzle.Visitor.GrigliaVisitorIF;
import ModelloPuzzle.Mediator.Messaggio;
import ModelloPuzzle.Mediator.MessaggioIF;
import ModelloPuzzle.Mediator.Componente;
import ModelloPuzzle.Mediator.MediatorFactory;

import java.util.ArrayList;
import java.util.Iterator;

public class GrigliaProxy implements GrigliaIF, Componente {

    private final GrigliaIF s;

    public GrigliaProxy(GrigliaIF s){
        this.s=s;
    }

    @Override
    public int getMaxCifraInseribile() {
        return s.getMaxCifraInseribile();
    }

    @Override
    public int getN() {
        return s.getN();
    }

    @Override
    public void aggiungiSoluzione() {
        s.aggiungiSoluzione();
    }

    @Override
    public void annullaInserimento(Cella c,boolean fromTrovaSoluzioni) {s.annullaInserimento(c,fromTrovaSoluzioni);}

    @Override
    public void azzeraSoluzioni() {
        s.azzeraSoluzioni();
    }

    @Override
    public Integer getValue() {
        return null;
    }

    @Override
    public void accept(GrigliaVisitorIF visitor) {
        visitor.visit(s);
    }

    @Override
    public void stampa() {
        s.stampa();
    }

    @Override
    public void reset() {
        s.reset();
    }

    @Override
    public boolean esisteRipetizione(Integer valore, Posizione pos) {
        return s.esisteRipetizione(valore, pos);
    }

    @Override
    public void rappresentaSoluzione(int id_soluzione) {
        s.rappresentaSoluzione(id_soluzione);
    }

    @Override
    public boolean isValida() {
        return s.isValida();
    }

    @Override
    public MessaggioIF verificaSoluzione() {
        return s.verificaSoluzione();
    }

    @Override
    public void aggiornaGriglia() {s.aggiornaGriglia();}

    @Override
    public int getNSoluzioni() {
        return s.getNSoluzioni();
    }

    @Override
    public boolean effettuaInserimento(Blocco b, Cella cella, Integer valore,boolean fromTrovaSoluzioni) {
        if (valore!=null) {//caso azzeramento cella non necessita controllo
            if(esisteRipetizione(valore,cella.getPos())){
                MediatorFactory.FACTORY.getMediatore(this).notifica(this, Messaggio.RIPETIZIONI_INSERIMENTO);
                return false;
            }
            if(b.isUltimoInserimento() && cella.getValue()==null || b.isPieno()){
                if(!(risultatoValido(b,valore))) {
                    MediatorFactory.FACTORY.getMediatore(this).notifica(this, Messaggio.RISULTATO_NON_VALIDO_INSERIMENTO);
                    return false;
                }
            }
        }
        s.effettuaInserimento(b,cella,valore,fromTrovaSoluzioni);
        return true;
    }

    private boolean risultatoValido(Blocco b,Integer valore) {
        Operatore op = b.getOperatore();
        ArrayList<Integer> valori = new ArrayList<>();
        valori.add(valore);

        for (GrigliaElement se : b){
            Cella c = (Cella) se;
            Integer operando=c.getValue();
            if(operando!=null) valori.add(operando);
        }
        return op.verificaRisultato(valori,b.getRisultato());
    }

    public String toString(){
        return s.toString();
    }

    @Override
    public GrigliaElement getChild(int i) {
        return s.getChild(i);
    }

    @Override
    public void addChild(GrigliaElement el) {
        s.addChild(el);
    }

    @Override
    public int getChildrenCount() {
        return s.getChildrenCount();
    }

    @Override
    public CompositeGrigliaElement asComposite() {
        return s.asComposite();
    }

    @Override
    public Iterator<GrigliaElement> iterator() {
        return s.iterator();
    }
}
