package Grafica;

import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.GrigliaElement;

import java.awt.*;

public class CellaView implements GrigliaElementView {

    @Override
    public void drawGraphicObject(GrigliaElement strutturaElement, Graphics2D g) {
        Cella c = (Cella) strutturaElement;
        int cellaSize = GrigliaPanel.getCellaSize();
        Rectangle rettangolo_cella = new Rectangle(c.getPos().getY()*cellaSize,c.getPos().getX()*cellaSize,cellaSize,cellaSize);
        c.setRectangle(rettangolo_cella);
        g.setColor(c.getColor());
        g.fill(rettangolo_cella);
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(1));
        g.draw(rettangolo_cella);
        double x = rettangolo_cella.getCenterX();
        double y = rettangolo_cella.getCenterY();
        Font font = new Font("Arial", Font.PLAIN, 35);
        g.setColor(Color.BLACK);
        g.setFont(font);
        Integer valore=c.getValue();
        if(valore!=null) {
            int textWidth = g.getFontMetrics().stringWidth("" + c.getValue());
            int textHeight = g.getFontMetrics().getHeight();
            int textX = (int) (x - textWidth / 2.0);
            int textY = (int) (y + textHeight / 4.0);
            g.drawString("" + c.getValue(), textX, textY);
        }
    }
}
