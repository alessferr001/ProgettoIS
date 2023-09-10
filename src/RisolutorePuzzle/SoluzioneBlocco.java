package RisolutorePuzzle;

import java.util.ArrayList;

public class SoluzioneBlocco {

    private final ArrayList<Integer> valori;//indice = id_cella, valore = valore cella

    public SoluzioneBlocco(int n_celle){
        valori = new ArrayList<>();
        int i=0;
        while(i<n_celle){
            valori.add(0);
            i++;
        }
    }

    public void aggiungiValoreCella(int id_cella,int valore){
        valori.set(id_cella,valore);
    }

    public ArrayList<Integer> getValori(){
        return valori;
    }

    public boolean isEmpty(){
        return valori.isEmpty();
    }

    public int size(){
        return valori.size();
    }

}
