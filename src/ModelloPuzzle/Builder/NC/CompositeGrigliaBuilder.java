package ModelloPuzzle.Builder.NC;

import ModelloPuzzle.Componenti.*;
import ModelloPuzzle.State.Operatore;
import ModelloPuzzle.State.TipologiaBlocco;

import java.util.Deque;
import java.util.LinkedList;

public class CompositeGrigliaBuilder implements GrigliaBuilderNOComposite {

    private final Deque<CompositeGrigliaElement> stack=new LinkedList<>();
    private GrigliaFactory grigliaFactory = new ConcreteGrigliaFactory();
    private GrigliaIF s;

    @Override
    public boolean creaStruttura(int n,Integer max_cifra) {
        s=grigliaFactory.creaGriglia(n,max_cifra,false);
        stack.push(s);
        return true;
    }

    public boolean closeStruttura(){
        stack.pop();return true;
    }

    @Override
    public boolean creaBlocco(int id_blocco, TipologiaBlocco tb, Operatore op, int ris, int max_cifra) {
        Blocco b = new Blocco(id_blocco,tb,max_cifra);
        b.setRisultato(ris);
        b.setOperatore(op);
        stack.peek().addChild(b);
        stack.push(b);
        return true;
    }

    public boolean closeBlocco(){
        stack.pop();return true;
    }

    @Override
    public boolean aggiungiCella(int id_cella, Integer valore, Posizione pos) {
        Cella c = new Cella(id_cella);
        c.setValore(valore);
        c.setPos(pos);
        stack.peek().addChild(c);
        return true;
    }

    public GrigliaIF getStruttura(){
        return s;
    }
}
