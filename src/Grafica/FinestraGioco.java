package Grafica;

import ModelloPuzzle.Builder.C.GraphicGrigliaBuilder;
import ModelloPuzzle.Builder.C.GrigliaBuilderComposite;
import ModelloPuzzle.Visitor.DirectorGrigliaVisitor;
import ModelloPuzzle.controller.Controller;
import ModelloPuzzle.controller.ControllerFactory;
import ModelloPuzzle.controller.GrigliaControllerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class FinestraGioco extends JFrame {

    private static FinestraGioco instance;
    private GrigliaPanel strutturaPanel;
    private final SoluzioniPanel soluzioniPanel=new SoluzioniPanel();
    private final ComandiPanel comandiPanel=new ComandiPanel();
    private FinestraIniziale finestraIniziale;

    private final JMenuBar jMenuBar=new JMenuBar();
    private final JFileChooser fileChooser=new JFileChooser();
    private final GrigliaBuilderComposite builder=new GraphicGrigliaBuilder();

    private final ControllerFactory controllerFactory= new GrigliaControllerFactory();
    private final Controller c = controllerFactory.getController();

    private boolean fine_gioco;

    public void setFinestraIniziale(FinestraIniziale finestraIniziale) {
        this.finestraIniziale = finestraIniziale;
    }

    public static FinestraGioco getInstance() {
        if(instance==null){
            instance = new FinestraGioco();
        }
        return instance;
    }

    private FinestraGioco(){

        setLayout(new BorderLayout());
        //visualizzo solo file '.txt'
        fileChooser.addChoosableFileFilter(new GrigliaFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        nuovaPartita();
        setMenu();
        setJMenuBar(jMenuBar);

        add(comandiPanel,BorderLayout.NORTH);
        add(soluzioniPanel,BorderLayout.SOUTH);

        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(false);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                int selezione = JOptionPane.showConfirmDialog(FinestraGioco.this, "Vuoi tornare al menu principale?", "Conferma Uscita", JOptionPane.OK_CANCEL_OPTION);
                if (selezione == JOptionPane.OK_OPTION) {
                    FinestraGioco.this.setVisible(false);
                    finestraIniziale.setVisible(true);
                }
            }
        });
    }

    public void configuraPannello(GrigliaPanel strutturaPanel) {
        strutturaPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!fine_gioco) strutturaPanel.effettuataSelezione(e);
            }
        });
    }

    public void fineGioco() {
        fine_gioco=true;
        aggiornaComandi();
    }

    private void aggiornaComandi() {
        for(Component c: comandiPanel.getComponents()){
            JButton bt = (JButton) c;
            if(!(bt.getText().equals("Nuova Partita"))) bt.setEnabled(!fine_gioco);
        }
    }

    public void nuovaPartita() {
        fine_gioco=false;
        if(strutturaPanel != null) remove(strutturaPanel);
        c.getGriglia().accept(new DirectorGrigliaVisitor(builder));
        strutturaPanel = ((GraphicGrigliaBuilder)builder).getGrigliaPanel();
        configuraPannello(strutturaPanel);
        add(strutturaPanel,BorderLayout.CENTER);
        revalidate();
        aggiornaComandi();
        soluzioniPanel.reset();
    }


    private void setMenu() {
        JMenu file = new JMenu("File");
        JMenuItem importa = new JMenuItem("Carica Partita");
        importa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(FinestraGioco.this) == JFileChooser.APPROVE_OPTION){
                    int scelta = JOptionPane.showConfirmDialog(FinestraGioco.this,"Vuoi abilitare il controllo sul soddisfacimento dei vincoli?","Seleziona Opzione",JOptionPane.YES_NO_OPTION);
                    if(scelta == JOptionPane.OK_OPTION){
                        try {
                            c.caricaDaFile(fileChooser.getSelectedFile());
                            c.abilitaControlli();
                            nuovaPartita();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(FinestraGioco.this,"Impossibile importare dati da file","Errore",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if(scelta == JOptionPane.NO_OPTION){
                        try {
                            c.caricaDaFile(fileChooser.getSelectedFile());
                            nuovaPartita();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(FinestraGioco.this,"Impossibile importare dati da file","Errore",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if (scelta == JOptionPane.CLOSED_OPTION) {
                            JOptionPane.showMessageDialog(FinestraGioco.this,"Operazioni annullate","Annullato",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        JMenuItem esporta = new JMenuItem("Salva Partita");
        esporta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(FinestraGioco.this) == JFileChooser.APPROVE_OPTION){
                    try {
                        c.salvaSuFile(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(FinestraGioco.this,"Impossibile esportare dati su file","Errore",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JMenuItem esci = new JMenuItem("Esci");
        esci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selezione = JOptionPane.showConfirmDialog(FinestraGioco.this,"Vuoi tornare al menu principale?","Conferma Uscita",JOptionPane.OK_CANCEL_OPTION);
                if(selezione == JOptionPane.OK_OPTION){
                    FinestraGioco.this.setVisible(false);
                    finestraIniziale.setVisible(true);
                }
            }
        });

        file.add(importa);
        file.add(esporta);
        file.addSeparator();
        file.add(esci);
        jMenuBar.add(file);
    }

    public void mostraMessaggio(String titolo,String messaggio){
        JOptionPane.showMessageDialog(this,messaggio,titolo,JOptionPane.INFORMATION_MESSAGE,null);
    }

}
