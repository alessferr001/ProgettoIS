package Grafica;

import ModelloPuzzle.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SoluzioniPanel extends JPanel{

    private JButton mostraSoluzioni,prossimaSoluzione,precedenteSoluzione;
    private Integer[] id_soluzioni;
    private int id_soluzione_corrente=-1;
    private Integer nr_soluzioni_desiderate;
    private final ControllerFactory controllerFactory= new GrigliaControllerFactory();
    private final Controller c = controllerFactory.getController();


    public SoluzioniPanel(){
        setLayout(new FlowLayout());
        mostraSoluzioni=new JButton("Mostra Soluzioni");
        prossimaSoluzione=new JButton("Prossima Soluzione");
        precedenteSoluzione=new JButton("Precedente Soluzione");
        reset();

        mostraSoluzioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selezione = JOptionPane.showConfirmDialog(SoluzioniPanel.this,"Mostrare le soluzioni annullerà la partita in corso, sei sicuro?","Mostra Soluzioni",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(selezione == JOptionPane.OK_OPTION) {
                    c.trovaSoluzioni();
                    setSoluzioni(c.getNSoluzioniTrovate());
                    nr_soluzioni_desiderate = (Integer) JOptionPane.showInputDialog(SoluzioniPanel.this, "Quante soluzioni desideri visualizzare?",
                            "Mostra Soluzioni", JOptionPane.QUESTION_MESSAGE, null, id_soluzioni, id_soluzioni[0]);
                    if (nr_soluzioni_desiderate != null) {
                        mostraSoluzioni.setEnabled(false);
                        id_soluzione_corrente++;
                        prossimaSoluzione.setEnabled(!(id_soluzione_corrente >= nr_soluzioni_desiderate - 1));
                        c.mostraSoluzione(id_soluzione_corrente);
                        FinestraGioco.getInstance().fineGioco();
                    }
                }
            }
        });

        prossimaSoluzione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.mostraSoluzione(id_soluzione_corrente+1);
                id_soluzione_corrente++;
                if(id_soluzione_corrente >= nr_soluzioni_desiderate-1) prossimaSoluzione.setEnabled(false);
                precedenteSoluzione.setEnabled(true);

            }
        });

        precedenteSoluzione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.mostraSoluzione(id_soluzione_corrente-1);
                id_soluzione_corrente--;
                if (id_soluzione_corrente<=0) precedenteSoluzione.setEnabled(false);
                prossimaSoluzione.setEnabled(true);
            }
        });

        add(mostraSoluzioni,FlowLayout.LEFT);
        add(precedenteSoluzione);
        add(prossimaSoluzione);
    }

    public void reset(){
        id_soluzione_corrente=-1;
        mostraSoluzioni.setEnabled(true);
        precedenteSoluzione.setEnabled(false);
        prossimaSoluzione.setEnabled(false);
    }

    private void setSoluzioni(int nr_soluzioni_trovate){
        id_soluzioni=new Integer[nr_soluzioni_trovate];
        for(int i=0; i<nr_soluzioni_trovate; i++){
            id_soluzioni[i]=i+1;
        }
    }
}
