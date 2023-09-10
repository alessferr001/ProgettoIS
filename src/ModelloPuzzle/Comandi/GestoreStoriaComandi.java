package ModelloPuzzle.Comandi;
import java.util.LinkedList;

public class GestoreStoriaComandi implements GestoreComando {

    private int maxHistoryLength = 100;

    private final LinkedList<Comando> history = new LinkedList<>();

    private final LinkedList<Comando> redoList = new LinkedList<>();

    public GestoreStoriaComandi() {
        this(100);
    }

    public GestoreStoriaComandi(int maxHistoryLength) {
        if (maxHistoryLength < 0)
            throw new IllegalArgumentException();
        this.maxHistoryLength = maxHistoryLength;
    }

    public void gestisci(Comando cmd) {

        if (cmd.doIt()) {
            // restituisce true: può essere annullato
            addToHistory(cmd);
        } else {
            // restituisce false: non può essere annullato
            history.clear();
        }
        if (redoList.size() > 0)
            redoList.clear();
    }

    public void redo() {
        if (redoList.size() > 0) {
            Comando redoCmd = redoList.removeFirst();
            redoCmd.doIt();
            history.addFirst(redoCmd);
        }
    }

    public void undo() {
        if (history.size() > 0) {
            Comando undoCmd = history.removeFirst();
            undoCmd.undoIt();
            redoList.addFirst(undoCmd);
        }
    }

    private void addToHistory(Comando cmd) {
        history.addFirst(cmd);
        if (history.size() > maxHistoryLength) {
            history.removeLast();
        }
    }

    public void clear(){
        history.clear();
        redoList.clear();
    }

}
