package Grafica;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

class PannelloImmagine extends JPanel{

    private Image immagine;

    public PannelloImmagine(String nome_immagine){
        URL imgURL = getClass().getResource("immagini/"+nome_immagine);
        immagine = new ImageIcon(imgURL).getImage();
        setPreferredSize(new Dimension(immagine.getWidth(this),immagine.getHeight(this)));
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(immagine, 0, 0, null);
    }
}
