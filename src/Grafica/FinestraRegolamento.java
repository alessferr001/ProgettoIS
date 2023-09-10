package Grafica;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class FinestraRegolamento extends JFrame {

    private FinestraIniziale finestraIniziale;

    public FinestraRegolamento(FinestraIniziale finestraIniziale){
        this.finestraIniziale=finestraIniziale;
        String percorso = "FinestraRegolamento.png";
        PannelloImmagine img = new PannelloImmagine(percorso);
        add(img);
        pack();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                int selezione = JOptionPane.showConfirmDialog(FinestraRegolamento.this, "Vuoi tornare al menu principale?", "Conferma Uscita", JOptionPane.OK_CANCEL_OPTION);
                if (selezione == JOptionPane.OK_OPTION) {
                    FinestraRegolamento.this.setVisible(false);
                    finestraIniziale.setVisible(true);
                }
            }
        });

        setLocationRelativeTo(null);
        setResizable(false);
    }

}
