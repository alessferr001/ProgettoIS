package ModelloPuzzle.Componenti;

public class MatriceGrigliaFactory implements GrigliaImplFactory{
    @Override
    public GrigliaImpl creaGrigliaImpl(int n, int max_cifra) {
        return new MatriceGriglia(n, max_cifra);
    }
}
