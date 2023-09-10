package ModelloPuzzle.Mediator;

public enum Messaggio implements MessaggioIF {

    RIPETIZIONI_INSERIMENTO{
        @Override
        public String toString() {
            return "Attenzione, l'inserimento causa delle ripetizioni!";
        }
    },
    RISULTATO_NON_VALIDO_INSERIMENTO{
        @Override
        public String toString() {
            return "Attenzione, l'inserimento non consente di ottenere\nil risultato previsto!";
        }
    },
    SOLUZIONE_VALIDA{
        @Override
        public String toString() {
            return "Congratulazioni la soluzione è valida!";
        }
    },
    SOLUZIONE_BLOCCO_NON_VALIDA{
        @Override
        public String toString() {
            return "Attenzione, la soluzione di un blocco non è valida!";
        }
    },
    RIPETIZIONI{
        @Override
        public String toString() {
            return "Attenzione, esistono delle ripetizioni!";
        }
    },
    CELLE_VUOTE{
        @Override
        public String toString() {
            return "Attenzione, ci sono delle caselle vuote!";
        }
    };

}
