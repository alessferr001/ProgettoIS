package ModelloPuzzle.Componenti;

public class ConcreteGrigliaFactory implements GrigliaFactory{

    @Override
    public GrigliaIF creaGriglia(int n, int max_cifra, boolean genera) {
        return new Griglia(n,max_cifra,genera);
    }
}
