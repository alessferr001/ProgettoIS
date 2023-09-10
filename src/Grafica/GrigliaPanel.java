package Grafica;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.CompositeGrigliaElement;
import ModelloPuzzle.Componenti.GrigliaElement;
import ModelloPuzzle.Mediator.Componente;
import ModelloPuzzle.controller.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class GrigliaPanel extends JPanel implements GrigliaElementListener , Componente {

    private LinkedList<GrigliaElement> elements = new LinkedList<>();

    private static int cellaSize;
    private Object[] cifre_selezionabili;
    private final ControllerFactory controllerFactory= new GrigliaControllerFactory();
    private final Controller c = controllerFactory.getController();

    public static int getCellaSize(){
        return cellaSize;
    }

    public GrigliaPanel(int n){
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500, 500));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cellaSize = this.getPreferredSize().width / n;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (GrigliaElement se : elements) {
            if(se.asComposite()!=null) {
                CompositeGrigliaElement se0 = (CompositeGrigliaElement)se;
                GrigliaElementView view_blocco = GrigliaElementViewFactory.FACTORY.createView(se0);
                for (GrigliaElement se1 : se0) {
                    GrigliaElementView view_cella = GrigliaElementViewFactory.FACTORY.createView(se1);
                    view_cella.drawGraphicObject(se1, g2);
                }
                view_blocco.drawGraphicObject(se, g2);
            }
        }
    }

    GrigliaElement getStrutturaElementAt(Point2D p,boolean blocco_cella) {
        for (GrigliaElement se : elements) {
            if(blocco_cella){//cerco la cella
                if(se instanceof Cella) {
                    if (se.contains(p))
                        return se;
                }
            }
            else if(se instanceof Blocco){
                if (se.contains(p))
                    return se;
            }
        }
        return null;
    }

    public void add(GrigliaElement go) {
        elements.add(go);
        go.addObjectListener(this);
        repaint();
    }

    void effettuataSelezione(MouseEvent e){
        Cella cella = (Cella) this.getStrutturaElementAt(e.getPoint(), true);//cella
        Blocco blocco = (Blocco) this.getStrutturaElementAt(e.getPoint(), false);//blocco
        if(blocco!=null && cella!=null) {
            cella.setColor(Color.LIGHT_GRAY);
            aggiornaCifreSelezionabili(c.getMaxCifra());
            Object valore = JOptionPane.showInputDialog(this, "Seleziona Cifra", "Menu Selezione", JOptionPane.QUESTION_MESSAGE, null, cifre_selezionabili, cifre_selezionabili[0]);
            if (valore != null) {
                Integer value;
                if (!(valore.equals("Azzera"))) {
                    value = (Integer) valore;
                    c.effettuaInserimento(blocco, cella, value);
                } else {
                    if (!(cella.getValue() == null)) {
                        c.effettuaInserimento(blocco, cella, null);
                    }
                }
            }
            cella.setColor(Color.white);
        }
    }

    private void aggiornaCifreSelezionabili(int maxCifra) {
        cifre_selezionabili = new Object[maxCifra+1];
        cifre_selezionabili[0]="Azzera";
        for(int i = 1; i<=maxCifra; i++){
            cifre_selezionabili[i]=i;
        }
    }

    @Override
    public void graphicChanged() {
        repaint();
    }

}
