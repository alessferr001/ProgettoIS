package ModelloPuzzle.State;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.GeneratoreGriglia;

public enum StatoBlocco implements StatoBloccoIF {

    POSIZIONATO{
        @Override
        public void esegui(GeneratoreGriglia s, Blocco b) {
            s.getAssegnati().add(b);
            s.getBlocchi().remove(b);
            s.aggiornaPosizione();
        }
    },
    NO_SPAZIO{
        @Override
        public void esegui(GeneratoreGriglia s, Blocco b) {
            s.deassegnaBlocco(b);
            Blocco b0 = s.getAssegnati().get(GeneratoreGriglia.random.nextInt(s.getAssegnati().size()));
            s.getAssegnati().remove(b0);
            s.deassegnaBlocco(b0);
            s.getBlocchi().add(b0);
            s.aggiornaPosizione();
            StatoBloccoIF sb = s.posizionaBlocco(b);
            sb.esegui(s,b);
        }
    };

}
