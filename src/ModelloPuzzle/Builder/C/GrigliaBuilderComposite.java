package ModelloPuzzle.Builder.C;

import ModelloPuzzle.Componenti.*;

public interface GrigliaBuilderComposite{
//Utilizzato per rappresentare la struttura gerarchica in diverse maniere (Grafiche, e altro)

    void creaStruttura(GrigliaIF struttura);
    default void closeStruttura(){};
    void creaBlocco(Blocco blocco);
    default void closeBlocco(){};
    void aggiungiCella(Cella c);
}
