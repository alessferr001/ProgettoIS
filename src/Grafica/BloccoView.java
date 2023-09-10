package Grafica;

import ModelloPuzzle.Componenti.Blocco;
import ModelloPuzzle.Componenti.Cella;
import ModelloPuzzle.Componenti.GrigliaElement;

import java.awt.*;
import java.awt.geom.Area;

public class BloccoView implements GrigliaElementView {
    @Override
    public void drawGraphicObject(GrigliaElement strutturaElement, Graphics2D g) {
        Blocco b = (Blocco) strutturaElement;
        int cellaSize=GrigliaPanel.getCellaSize();
        boolean prima_cella=true;
        Point p=null;
        Area area_blocco = new Area();
        for(GrigliaElement se: b){
            Cella c = (Cella) se;
            Rectangle rettangolo_cella = new Rectangle(c.getPos().getY()*cellaSize,c.getPos().getX()*cellaSize,cellaSize,cellaSize);
            if(prima_cella){
                p=rettangolo_cella.getLocation();
                prima_cella=false;
            }
            area_blocco.add(new Area(rettangolo_cella));
        }
        b.setArea(area_blocco);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        g.draw(area_blocco);
        Font font = new Font("Arial", Font.PLAIN, 15);
        g.setColor(Color.darkGray);
        g.setFont(font);
        g.drawString(""+b.getRisultato()+b.getOperatore(),(p.x+10),(p.y+20));
    }
}
