package Grafica;
import ModelloPuzzle.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ComandiPanel extends JPanel{

    private JButton undo,redo,reset,verifica_soluzione,nuova_partita;
    private Object[] tipologie_griglia = {"Griglia3X3","Griglia4X4","Griglia5X5","Griglia6X6"};
    private Object[] cifre = {6,9,15,20};
    private Object[] cifre_cinque = {6,9};
    private final ControllerFactory controllerFactory= new GrigliaControllerFactory();
    private final Controller c = controllerFactory.getController();

    public ComandiPanel() {
        setLayout(new FlowLayout());
        undo = new JButton("Undo");
        redo = new JButton("Redo");
        reset = new JButton("Reset");
        verifica_soluzione = new JButton("Verifica Soluzione");
        nuova_partita = new JButton("Nuova Partita");

        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.undo();
            }
        });
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.redo();
            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.reset();
            }
        });
        verifica_soluzione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.verificaSoluzione();
            }
        });
        nuova_partita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = JOptionPane.showInputDialog(ComandiPanel.this, "Scegli tipologia struttura", "Seleziona Struttura", JOptionPane.QUESTION_MESSAGE, null, tipologie_griglia, tipologie_griglia[0]);
                String selezione = (String) obj;
                if (selezione != null) {
                    int n = switch (selezione) {
                        case "Griglia3X3" -> 3;
                        case "Griglia4X4" -> 4;
                        case "Griglia5X5" -> 5;
                        default -> 6;
                    };
                    Object[] cifre_selezionabili=cifre;
                    Integer cifra=9;
                    if(n==5) cifre_selezionabili=cifre_cinque;
                    if(n!=6) cifra = (Integer) JOptionPane.showInputDialog(ComandiPanel.this, "Scegli massima cifra inseribile nelle caselle", "Seleziona Cifra", JOptionPane.QUESTION_MESSAGE, null, cifre_selezionabili, cifre_selezionabili[0]);
                    if (cifra != null) {
                        int scelta = JOptionPane.showConfirmDialog(ComandiPanel.this, "Vuoi abilitare il controllo sul soddisfacimento dei vincoli?", "Seleziona Opzione", JOptionPane.YES_NO_OPTION);
                        if (scelta == JOptionPane.OK_OPTION) {
                            c.nuovaPartita(n,cifra);
                            c.abilitaControlli();
                            FinestraGioco.getInstance().nuovaPartita();
                        } else if (scelta == JOptionPane.NO_OPTION) {
                            c.nuovaPartita(n,cifra);
                            FinestraGioco.getInstance().nuovaPartita();
                        } else if (scelta == JOptionPane.CLOSED_OPTION) {
                            JOptionPane.showMessageDialog(ComandiPanel.this, "Operazioni annullate", "Annullato", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });

        add(verifica_soluzione, FlowLayout.LEFT);
        add(reset);
        add(undo);
        add(redo);
        add(nuova_partita);
    }

}
