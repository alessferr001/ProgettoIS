package ModelloPuzzle.Builder.NC;

import ModelloPuzzle.Componenti.*;
import ModelloPuzzle.State.Operatore;
import ModelloPuzzle.State.TipologiaBlocco;

public interface GrigliaBuilderNOComposite {

    boolean creaStruttura(int n,Integer max_cifra);
    default boolean closeStruttura(){return false;};
    boolean creaBlocco(int id_blocco, TipologiaBlocco tb, Operatore op, int ris, int max_cifra);
    default boolean closeBlocco(){return false;};
    boolean aggiungiCella(int id_cella, Integer valore, Posizione pos);

}
