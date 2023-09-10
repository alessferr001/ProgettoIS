package ModelloPuzzle.State;

import java.util.Random;

public enum TipologiaBlocco implements TipologiaBloccoIF {


    BLOCCO_QUATTRO{
        private final Random r = new Random();
        private final int dim=4;

        public int getDim(){
            return this.dim;
        }

        public Integer creaRisultato(Operatore op, Integer n) {
            switch (op){
                case ADDIZIONE -> {
                    return r.nextInt((n*(dim-1))+1)+dim*2;
                }
                case SOTTRAZIONE -> {
                    return r.nextInt(n-(dim-1))+1;
                }
                case MOLTIPLICAZIONE ->{
                    Integer num=r.nextInt(141)+10;
                    while((TipologiaBlocco.isPrime(num)) || num%10!=0){
                        num=r.nextInt(301);
                    }
                    return num;
                }
                default->{//Divisione
                    return r.nextInt(3)+2;
                }
            }
        }
    },
    BLOCCO_TRE{
        private final Random r = new Random();
        private final int dim=3;

        public int getDim(){
            return this.dim;
        }

        public Integer creaRisultato(Operatore op, Integer n) {
            switch (op){
                case ADDIZIONE -> {
                    return r.nextInt((n*(dim-1))+1)+dim*2;
                }
                case SOTTRAZIONE -> {
                    return r.nextInt(n-(dim-1))+1;
                }
                case MOLTIPLICAZIONE ->{
                    Integer num=r.nextInt(91)+10;
                    while((TipologiaBlocco.isPrime(num)) || num%10!=0){
                        num=r.nextInt(151);
                    }
                    return num;
                }
                default->{//Divisione
                    return r.nextInt(4)+2;
                }
            }
        }
    },
    BLOCCO_DUE{
        private final Random r = new Random();
        private final int dim=2;

        public int getDim(){
            return this.dim;
        }

        public Integer creaRisultato(Operatore op, Integer n) {
            switch (op){
                case ADDIZIONE -> {
                    return r.nextInt(n+1)+dim*2;
                }
                case SOTTRAZIONE -> {
                    return r.nextInt(n-(dim-1))+1;
                }
                case MOLTIPLICAZIONE ->{
                    Integer num=r.nextInt(n*n)+n;
                    while((TipologiaBlocco.isPrime(num)) || num%10!=0){
                        num=r.nextInt(n*n);
                    }
                    return num;
                }
                default->{//Divisione
                    return r.nextInt(4)+2;
                }
            }
        }
    };

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

}
