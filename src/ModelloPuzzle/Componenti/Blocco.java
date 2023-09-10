package ModelloPuzzle.Componenti;
import ModelloPuzzle.State.Operatore;
import ModelloPuzzle.State.TipologiaBlocco;
import ModelloPuzzle.Visitor.GrigliaVisitorIF;
import RisolutorePuzzle.SoluzioneBlocco;
import RisolutorePuzzle.TrovaSoluzioniBlocco;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Blocco extends AbstractCompositeGrigliaElement {
    private final int id;
    private final int maxCifraInseribile;
    private final TipologiaBlocco tb;

    private Area area_blocco;
    private ArrayList<SoluzioneBlocco> soluzioni_trovate;
    private int ris;
    private Operatore op;

    public Blocco(int id, TipologiaBlocco tb,int maxCifraInseribile){
        this.id=id;
        this.tb=tb;
        this.maxCifraInseribile = maxCifraInseribile;
    }

    public void setArea(Area area_blocco) {
        this.area_blocco = area_blocco;
    }

    public void setRisultato(int ris) {
        this.ris = ris;
        soluzioni_trovate=null;//devo ritrovare le soluzioni se cambio il risultato
    }

    public void setOperatore(Operatore op) {
        this.op = op;
        soluzioni_trovate=null;//devo ritrovare le soluzioni se cambio l'operatore
    }

    public TipologiaBlocco getTb() {
        return tb;
    }

    public Operatore getOperatore() {
        return this.op;
    }

    public int getRisultato(){
        return this.ris;
    }

    public int getDimensione(){
        return this.tb.getDim();
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<SoluzioneBlocco> getSoluzioni() {
        if(soluzioni_trovate == null){
            TrovaSoluzioniBlocco tsb = new TrovaSoluzioniBlocco(this,maxCifraInseribile);
            tsb.risolvi();
            soluzioni_trovate=tsb.getSoluzioniTrovate();
        }
        return soluzioni_trovate;
    }


    public boolean isUltimoInserimento(){
        int i=0;
        for(GrigliaElement se: this){
            Cella c = (Cella) se;
            if(c.getValue()==null) i++;
        }
        return i==1;
    }

    public boolean isPieno() {
        int i=0;
        for(GrigliaElement se: this){
            Cella c = (Cella) se;
            if(c.getValue()==null) i++;
        }
        return i==0;
    }

    @Override
    public boolean contains(Point2D p) {
        if(area_blocco == null) return false;
        return area_blocco.contains(p);
    }

    @Override
    public void accept(GrigliaVisitorIF visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString(){
        return "Blocco: " + this.getId() + " Dimensione: "+ this.getDimensione() + " Operazione: "+this.getOperatore()+" Risultato_Atteso: "+this.getRisultato();
    }
}
