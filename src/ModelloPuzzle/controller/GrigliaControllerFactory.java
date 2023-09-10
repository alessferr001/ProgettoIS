package ModelloPuzzle.controller;

public class GrigliaControllerFactory implements ControllerFactory{
    @Override
    public Controller getController() {
        return GrigliaController.getInstance();
    }
}
