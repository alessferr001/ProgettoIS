package Grafica;

import ModelloPuzzle.controller.Controller;
import ModelloPuzzle.controller.ControllerFactory;
import ModelloPuzzle.controller.GrigliaControllerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FinestraIniziale extends JFrame {

    private final FinestraGioco finestraGioco;
    private final Object[] tipologie_griglia = {"Griglia3X3","Griglia4X4","Griglia5X5","Griglia6X6"};
    private final Object[] cifre = {6,9,15,20};
    private final Object[] cifre_cinque ={6,9};
    private final JFileChooser fileChooser;
    private final FinestraRegolamento finestraRegolamento = new FinestraRegolamento(FinestraIniziale.this);;
    private final ControllerFactory controllerFactory= new GrigliaControllerFactory();
    private final Controller c = controllerFactory.getController();


    public FinestraIniziale(){

        finestraGioco = FinestraGioco.getInstance();

        JLayeredPane pane = this.getLayeredPane();

        JButton nuova_partita = new JButton("Nuova Partita");
        JButton caricaPartita = new JButton("Carica Partita");
        JButton regolamento = new JButton("Regolamento");

        PannelloImmagine pannelloImmagine = new PannelloImmagine("ImmagineIniziale.jpg");
        finestraGioco.setFinestraIniziale(this);

        fileChooser=new JFileChooser();
        fileChooser.addChoosableFileFilter(new GrigliaFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        add(pannelloImmagine);
        pack();

        nuova_partita.setBounds((this.getSize().width/2)-100, this.getSize().height/2-30,200,40);
        caricaPartita.setBounds((this.getSize().width/2)-100, ((this.getSize().height/2)+50)-30,200,40);
        regolamento.setBounds((this.getSize().width/2)-100, ((this.getSize().height/2)+100)-30,200,40);

        nuova_partita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = JOptionPane.showInputDialog(FinestraIniziale.this, "Scegli tipologia struttura", "Seleziona Struttura", JOptionPane.QUESTION_MESSAGE, null, tipologie_griglia, tipologie_griglia[0]);
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
                    if(n!=6) cifra = (Integer) JOptionPane.showInputDialog(FinestraIniziale.this, "Scegli massima cifra inseribile nelle caselle", "Seleziona Cifra", JOptionPane.QUESTION_MESSAGE, null, cifre_selezionabili, cifre_selezionabili[0]);
                    if (cifra != null) {
                        int scelta = JOptionPane.showConfirmDialog(FinestraIniziale.this, "Vuoi abilitare il controllo sul soddisfacimento dei vincoli?", "Seleziona Opzione", JOptionPane.YES_NO_OPTION);
                        if (scelta == JOptionPane.OK_OPTION) {
                            c.nuovaPartita(n,cifra);
                            c.abilitaControlli();
                            finestraGioco.nuovaPartita();
                            finestraGioco.setVisible(true);
                            FinestraIniziale.this.setVisible(false);
                        } else if (scelta == JOptionPane.NO_OPTION) {
                            c.nuovaPartita(n,cifra);
                            finestraGioco.nuovaPartita();
                            finestraGioco.setVisible(true);
                            FinestraIniziale.this.setVisible(false);
                        } else if (scelta == JOptionPane.CLOSED_OPTION) {
                            JOptionPane.showMessageDialog(FinestraIniziale.this, "Operazioni annullate", "Annullato", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });

        caricaPartita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(FinestraIniziale.this) == JFileChooser.APPROVE_OPTION){
                    int scelta = JOptionPane.showConfirmDialog(FinestraIniziale.this,"Vuoi abilitare il controllo sul soddisfacimento dei vincoli?","Seleziona Opzione",JOptionPane.YES_NO_OPTION);
                    if(scelta == JOptionPane.OK_OPTION){
                        try {
                            c.caricaDaFile(fileChooser.getSelectedFile());
                            c.abilitaControlli();
                            finestraGioco.nuovaPartita();
                            finestraGioco.setVisible(true);
                            FinestraIniziale.this.setVisible(false);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(FinestraIniziale.this,"Impossibile importare dati da file","Errore",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if(scelta == JOptionPane.NO_OPTION){
                        try {
                            c.caricaDaFile(fileChooser.getSelectedFile());
                            finestraGioco.nuovaPartita();
                            finestraGioco.setVisible(true);
                            FinestraIniziale.this.setVisible(false);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(FinestraIniziale.this,"Impossibile importare dati da file","Errore",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if (scelta == JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(FinestraIniziale.this,"Operazioni annullate","Annullato",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        regolamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinestraIniziale.this.setVisible(false);
                finestraRegolamento.setVisible(true);
            }
        });

        pane.add(nuova_partita);
        pane.add(caricaPartita);
        pane.add(regolamento);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
