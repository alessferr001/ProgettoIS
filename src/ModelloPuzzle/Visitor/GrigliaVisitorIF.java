package ModelloPuzzle.Visitor;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.GrigliaIF;

public interface GrigliaVisitorIF {

    void visit(GrigliaIF s);

    void visit(Blocco b);

    void visit(Cella c);

}
