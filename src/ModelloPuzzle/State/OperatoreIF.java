package ModelloPuzzle.State;

import java.util.ArrayList;

public interface OperatoreIF {

    Integer effettuaOperazione(int op1,int op2);

    boolean verificaRisultato(ArrayList<Integer> valori,Integer risultato_atteso);

}
