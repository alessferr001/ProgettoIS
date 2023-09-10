package ModelloPuzzle.Componenti;

import java.util.*;
abstract class AbstractCompositeGrigliaElement extends AbstractGrigliaElement implements CompositeGrigliaElement{

    private final ArrayList<GrigliaElement> elements = new ArrayList<>();

    @Override
    public Integer getValue(){return null;}

    @Override
    public CompositeGrigliaElement asComposite() {
        return this;
    }

    @Override
    public GrigliaElement getChild(int i) {
        return elements.get(i);
    }

    @Override
    public void addChild(GrigliaElement el) {
        elements.add(el);
    }

    @Override
    public int getChildrenCount() {
        return elements.size();
    }

    @Override
    public Iterator<GrigliaElement> iterator() {
        return new InnerIterator();
    }

    private class InnerIterator implements Iterator<GrigliaElement> {
        Iterator<GrigliaElement> it = elements.iterator();
        private GrigliaElement last = null;

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public GrigliaElement next() {
            last = it.next();
            return last;
        }

    }

}
