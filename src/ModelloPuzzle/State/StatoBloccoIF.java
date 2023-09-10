package ModelloPuzzle.State;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.GeneratoreGriglia;

public interface StatoBloccoIF {

    void esegui(GeneratoreGriglia gs, Blocco b);

}
