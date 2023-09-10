package ModelloPuzzle.Componenti;

import ModelloPuzzle.Builder.C.GrigliaStreamBuilder;
import ModelloPuzzle.State.Operatore;
import ModelloPuzzle.Visitor.DirectorGrigliaVisitor;
import ModelloPuzzle.Visitor.GrigliaVisitorIF;
import ModelloPuzzle.Mediator.Messaggio;
import ModelloPuzzle.Mediator.MessaggioIF;
import RisolutorePuzzle.TrovaSoluzioni;

import java.util.ArrayList;

class Griglia extends AbstractCompositeGrigliaElement implements GrigliaIF {

    private final GrigliaImpl impl;
    private GrigliaImplFactory factory = new MatriceGrigliaFactory();
    private final int maxCifraInseribile;

    public Griglia(int n, int maxCifraInseribile, boolean generate) {
        this.maxCifraInseribile=maxCifraInseribile;
        this.impl= factory.creaGrigliaImpl(n,maxCifraInseribile);
        if(generate) {
            new GeneratoreGriglia(impl);
            for (GrigliaElement se : impl.getBlocchi()) {
                Blocco b = (Blocco) se;
                addChild(b);
                for (GrigliaElement se0 : b) {
                    Cella c = (Cella) se0;
                    c.setValore(null);
                }
            }
        }
    }

    public boolean isValida(){
        TrovaSoluzioni soluzioni = new TrovaSoluzioni(this,1);
        soluzioni.risolvi();
        if(getNSoluzioni()>0) {
            impl.azzeraInserimenti();
            return true;
        }
        return false;
    }

    public int getMaxCifraInseribile() {
        return maxCifraInseribile;
    }

    public int getN(){
        return impl.getN();
    }

    public void accept(GrigliaVisitorIF visitor){
        visitor.visit(this);
    }

    public void stampa(){
        impl.stampa();
        new DirectorGrigliaVisitor(new GrigliaStreamBuilder(System.out)).visit(this);
    }

    public void aggiungiSoluzione(){
        impl.aggiungiSoluzione();
    }

    @Override
    public void annullaInserimento(Cella c,boolean fromTrovaSoluzioni) {
        if(!fromTrovaSoluzioni) c.setValore(null);
        impl.annullaInserimento(c.getPos());
    }

    @Override
    public void azzeraSoluzioni() {
        impl.azzeraSoluzioni();
    }

    public void reset(){
        for(GrigliaElement se: this) {
            Blocco b = (Blocco) se;
            for (GrigliaElement se1 : b) {
                Cella c = (Cella) se1;
                c.setValore(null);
            }
        }
        impl.azzeraInserimenti();
    }

    @Override
    public boolean esisteRipetizione(Integer valore, Posizione pos) {
        return impl.verificaRipetizioni(valore, pos).size()!=0;
    }

    public void aggiornaGriglia(){
        ArrayList<GrigliaElement> nuovi = new ArrayList<>();
        for(GrigliaElement se: this) {
            Blocco b = (Blocco) se;
            nuovi.add(b);
        }
        impl.setBlocchi(nuovi);
        impl.aggiorna();
    }

    public void rappresentaSoluzione(int id_soluzione){
        impl.rappresentaSoluzione(this,id_soluzione);
    }

    @Override
    public MessaggioIF verificaSoluzione() {
        for(GrigliaElement ge: this){
            Blocco b = (Blocco) ge;
            for(GrigliaElement ge0: b){
                Cella c= (Cella) ge0;
                if(c.getValue()!=null) {
                    if(esisteRipetizione(c.getValue(),c.getPos())) return Messaggio.RIPETIZIONI;
                }
                else{
                    return Messaggio.CELLE_VUOTE;
                }
            }
            if(!verificaRisultato(b)) return Messaggio.SOLUZIONE_BLOCCO_NON_VALIDA;
        }
        return Messaggio.SOLUZIONE_VALIDA;
    }

    private boolean verificaRisultato(Blocco b) {
        Operatore op = b.getOperatore();
        ArrayList<Integer> valori = new ArrayList<>();

        for (GrigliaElement se : b){
            Cella c = (Cella) se;
            Integer operando=c.getValue();
            valori.add(operando);
        }
        return op.verificaRisultato(valori,b.getRisultato());
    }

    public int getNSoluzioni(){
        return impl.getNSoluzioni();
    }

    @Override
    public boolean effettuaInserimento(Blocco b, Cella cella, Integer valore,boolean fromTrovaSoluzioni) {
        if(!fromTrovaSoluzioni) cella.setValore(valore);
        if(valore==null) valore=0;
        impl.effettuaInserimento(cella.getPos(),valore);
        return true;
    }

    public String toString(){
        return "Griglia: Dimensione: "+impl.getN()+" MaxCifra: "+maxCifraInseribile;
    }

}
