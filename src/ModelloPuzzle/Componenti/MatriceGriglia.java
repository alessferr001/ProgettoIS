package ModelloPuzzle.Componenti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class MatriceGriglia implements GrigliaImpl {

    private int n;
    private int[][] matrice_blocchi;
    private int[][] matrice_inserimenti;
    private int max_cifra_inseribile;
    private ArrayList<Integer[][]> soluzioni = new ArrayList<>();
    private ArrayList<GrigliaElement> blocchi_finals;


    public MatriceGriglia(int n, int max_cifra_inseribile){
        this.n=n;
        this.max_cifra_inseribile=max_cifra_inseribile;
        matrice_blocchi = new int[n][n];
        matrice_inserimenti = new int[n][n];
    }

    @Override
    public int getN() {
       return n;
    }

    @Override
    public int getMaxCifraInseribile() {
        return max_cifra_inseribile;
    }

    @Override
    public void stampa() {
        for(int[] v: matrice_blocchi){
            System.out.println(Arrays.toString(v));
        }
        System.out.println();

        for(int[] v: matrice_inserimenti){
            System.out.println(Arrays.toString(v));
        }
        System.out.println();
    }

    @Override
    public Posizione getPrimaPosizioneLibera() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((matrice_blocchi[i][j] == 0)) return new Posizione(i, j);
            }
        }
        return null;
    }


    @Override
    public void azzeraInserimenti() {
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                matrice_inserimenti[i][j]=0;
            }
        }
    }


    @Override
    public void assegnaBlocco(Posizione pos, int id_blocco) {
        matrice_blocchi[pos.getX()][pos.getY()]=id_blocco;
    }

    @Override
    public Set<Posizione> verificaRipetizioni(Integer valore, Posizione pos) {
        //restituisce l'insieme di posizioni in cui è presente il valore passato come parametro
        Set<Posizione> posizioni_conflitti = new HashSet<>();
        controllaRigheColonne(valore,pos,true,posizioni_conflitti);
        controllaRigheColonne(valore,pos,false,posizioni_conflitti);
        return posizioni_conflitti;
    }


    private void controllaRigheColonne(Integer valore,Posizione pos, boolean riga_colonna,Set<Posizione> posizioni_conflitti) {
        int i,index;
        if(riga_colonna){//Controllo_riga
            for(i=0;i<n;i++){
                index=pos.getX();
                if(matrice_inserimenti[index][i]==valore && i!=pos.getY()){
                    posizioni_conflitti.add(new Posizione(index,i));
                }
            }
        }
        else{//Controllo_colonna
            for(i=0;i<n;i++){
                index=pos.getY();
                if(matrice_inserimenti[i][index]==valore && i!=pos.getX()){
                    posizioni_conflitti.add(new Posizione(i,index));
                }
            }
        }
    }

    @Override
    public void deassegnaBlocco(Posizione pos) {
        matrice_blocchi[pos.getX()][pos.getY()]=0;
    }

    @Override
    public void annullaInserimento(Posizione pos) {
        matrice_inserimenti[pos.getX()][pos.getY()]=0;
    }

    @Override
    public void effettuaInserimento(Posizione p, Integer valore) {
        matrice_inserimenti[p.getX()][p.getY()]=valore;
    }

    @Override
    public ArrayList<GrigliaElement> getBlocchi() {
        return blocchi_finals;
    }

    public void setBlocchi(ArrayList<GrigliaElement> blocchi_finals){
        this.blocchi_finals=blocchi_finals;
    }

    @Override
    public void aggiungiSoluzione() {
        Integer[][] matrice_copia = new Integer[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                matrice_copia[i][j]=matrice_inserimenti[i][j];
            }
        }
        soluzioni.add(matrice_copia);
    }

    @Override
    public void aggiorna() {
        soluzioni = new ArrayList<>();
        azzeraInserimenti();
        for(GrigliaElement ge: blocchi_finals){
            Blocco b = (Blocco) ge;
            for(GrigliaElement ge0: b){
                Cella c = (Cella) ge0;
                int valore = 0;
                if(c.getValue()!=null) valore=c.getValue();
                Posizione p = c.getPos();
                matrice_blocchi[p.getX()][p.getY()]=b.getId();
                matrice_inserimenti[p.getX()][p.getY()]=valore;
            }
        }
    }

    @Override
    public int getNSoluzioni() {
        return soluzioni.size();
    }

    @Override
    public void rappresentaSoluzione(GrigliaIF grigliaIF, int id_soluzione) {
        Integer[][] soluzione = soluzioni.get(id_soluzione);
        for(GrigliaElement ge: grigliaIF){
            Blocco b = (Blocco) ge;
            for(GrigliaElement ge0: b){
                Cella c = (Cella) ge0;
                Posizione p= c.getPos();
                c.setValore(soluzione[p.getX()][p.getY()]);
                matrice_inserimenti[p.getX()][p.getY()]=soluzione[p.getX()][p.getY()];
            }
        }
    }

    public boolean esisteBlocco(Posizione p){
        return matrice_blocchi[p.getX()][p.getY()]!=0;
    }

    @Override
    public void azzeraSoluzioni() {
        soluzioni=new ArrayList<>();
    }

}
