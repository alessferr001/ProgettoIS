package ModelloPuzzle.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public enum Operatore implements OperatoreIF {

    ADDIZIONE{
        @Override
        public Integer effettuaOperazione(int op1, int op2) {
            return op1+op2;
        }

        @Override
        public boolean verificaRisultato(ArrayList<Integer> valori,Integer risultato_atteso) {
            Integer temp = valori.get(0);
            for (int i=1; i<valori.size(); i++){
                temp=effettuaOperazione(temp,valori.get(i));
            }
            return temp.equals(risultato_atteso);
        }

        @Override
        public String toString() {
            return "+";
        }
    },
    MOLTIPLICAZIONE{
        @Override
        public Integer effettuaOperazione(int op1, int op2) {
            return op1*op2;
        }

        @Override
        public boolean verificaRisultato(ArrayList<Integer> valori,Integer risultato_atteso) {
            Integer temp = valori.get(0);
            for (int i=1; i<valori.size(); i++){
                temp=effettuaOperazione(temp,valori.get(i));
            }
            return temp.equals(risultato_atteso);
        }

        @Override
        public String toString() {
            return "x";
        }
    },
    DIVISIONE{
        @Override
        public Integer effettuaOperazione(int op1, int op2) {
            return op1/op2;
        }

        @Override
        public boolean verificaRisultato(ArrayList<Integer> valori,Integer risultato_atteso) {
            valori = ordinaValori(valori);
            Integer temp = valori.get(valori.size()-2);
            for (int i=valori.size()-3; i>=0; i--){
                temp = MOLTIPLICAZIONE.effettuaOperazione(temp,valori.get(i));
            }
            Integer ris = valori.get(valori.size()-1);
            if(ris%temp != 0 ) return false;
            ris = effettuaOperazione(ris,temp);
            return ris.equals(risultato_atteso);
        }

        @Override
        public String toString() {
            return "÷";
        }
    },
    SOTTRAZIONE{
        @Override
        public Integer effettuaOperazione(int op1, int op2) {
            return op1-op2;
        }

        @Override
        public boolean verificaRisultato(ArrayList<Integer> valori,Integer risultato_atteso) {
            valori = ordinaValori(valori);
            Integer temp = valori.get(valori.size()-2);
            for (int i=valori.size()-3; i>=0; i--){
                temp=ADDIZIONE.effettuaOperazione(temp,valori.get(i));
            }
            Integer ris = valori.get(valori.size()-1);
            ris = effettuaOperazione(ris,temp);
            return ris.equals(risultato_atteso);
        }

        @Override
        public String toString() {
            return "-";
        }
    };

    public static ArrayList<Integer> ordinaValori(ArrayList<Integer> valori) {
        valori.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (Objects.equals(o1, o2)) return 0;
                if (o1 < o2) return -1;
                else return 1;
            }
        });
        return valori;
    }
}
