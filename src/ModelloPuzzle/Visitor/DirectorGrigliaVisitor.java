package ModelloPuzzle.Visitor;

import ModelloPuzzle.Builder.C.GrigliaBuilderComposite;
import ModelloPuzzle.Componenti.*;

public class DirectorGrigliaVisitor implements GrigliaVisitorIF {

    GrigliaBuilderComposite builder;

    public DirectorGrigliaVisitor(GrigliaBuilderComposite builder){
        this.builder=builder;
    };

    @Override
    public void visit(GrigliaIF s) {
        builder.creaStruttura(s);
        for(GrigliaElement se: s){
            Blocco b = (Blocco) se;
            b.accept(this);
        }
        builder.closeStruttura();
    }

    @Override
    public void visit(Blocco b) {
        builder.creaBlocco(b);
        for(GrigliaElement se: b){
            Cella c = (Cella) se;
            c.accept(this);
        }
        builder.closeBlocco();
    }

    @Override
    public void visit(Cella c) {
        builder.aggiungiCella(c);
    }

}
