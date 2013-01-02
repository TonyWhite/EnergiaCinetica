/**
 * La classe visualizza la finestra di calcolo
 * 
 * Autore: Antonio Bianco
 * Creazione: 09/09/2012
 * Ultima modifica: 18/09/2012
 * Versione: 1.1.2 stable
 */
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Vector;
public class Finestra extends JFrame implements WindowListener, KeyListener, ActionListener, FocusListener
{
    // Costanti
    private final String NOME_APPLICAZIONE = "Energia cinetica";
    private final String VERSIONE_APPLICAZIONE = "1.1.2";
    
    // Istanza di variabili
    private int calcoli = 1;          // Righe da visualizzare
    private Vector<CalcoloEnergiaCinetica> calcoloEnergiaCinetica = new Vector<CalcoloEnergiaCinetica>();
    private DecimalFormat formatoNumeri;    // Variabile per il formato numerico: predefinito con 6 decimali e punto come separatore di decimali
    double fattoreSpazio = 1;         // Fattore di moltiplicazione per lo spazio: permette di calcolare con multipli e sottomultipli
    double fattoreTempo = 1;          // Fattore di moltiplicazione per il tempo: permette di calcolare con multipli e sottomultipli
    double fattoreVelocita = 1;       // Fattore di moltiplicazione per la velocità: permette di calcolare con multipli e sottomultipli
    double fattoreMassa = 1;          // Fattore di moltiplicazione per la massa: permette di calcolare con multipli e sottomultipli
    double fattoreEnergia = 1;        // Fattore di moltiplicazione per l'energia: permette di calcolare con multipli e sottomultipli
    
    // Combo box in alto
    private JComboBox cbbSpazio;
    private JComboBox cbbTempo;
    private JComboBox cbbVelocita;
    private JComboBox cbbMassa;
    private JComboBox cbbEnergia;
    
    // Componenti della griglia
    private Vector<JTextField> txtSpazio;
    private Vector<JTextField> txtTempo;
    private Vector<JTextField> txtVelocita;
    private Vector<JTextField> txtMassa;
    private Vector<JTextField> txtEnergia;
    
    // Componenti a sinistra della griglia
    private Vector<JButton> btnAggiungi;
    private Vector<JButton> btnRimuovi;
    
    private JPanel pannello;
    
    /**
     * Constructor for objects of class Finestra
     */
    public Finestra()
    {
        super();
        this.setTitle(NOME_APPLICAZIONE + " " + VERSIONE_APPLICAZIONE);
        
        // Definizione del formato numerico
        DecimalFormatSymbols simboli = new DecimalFormatSymbols(Locale.getDefault());
        simboli.setDecimalSeparator('.');
        formatoNumeri = new DecimalFormat("#.######", simboli);
        
        // Preparazione del pannello
        pannello = new JPanel();
        GroupLayout layout = new GroupLayout(pannello);
        pannello.setLayout(layout);
        this.add(pannello);
        
        // Creazione dei componenti
        inizializzaComponentiDinamici();
        inizializzaComponentiStatici();
        posizionaComponenti();
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        Dimension dimensioni = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)(dimensioni.getWidth()/2-getWidth()/2), (int)(dimensioni.getHeight()/2-getHeight()/2));
        this.setVisible(true);
        setMinimumSize(getPreferredSize()); // Imposta la dimensione minima mentre la finestra è visibile
        txtSpazio.get(0).requestFocus();
    }
    
    /**
     * Inizializza i componenti dinamici: i componenti della griglia
     */
    private void inizializzaComponentiDinamici()
    {
        // Inizializzazione dei componenti della griglia
        txtSpazio = new Vector<JTextField>();
        txtTempo = new Vector<JTextField>();
        txtVelocita = new Vector<JTextField>();
        txtMassa = new Vector<JTextField>();
        txtEnergia = new Vector<JTextField>();
        btnAggiungi = new Vector<JButton>();
        btnRimuovi = new Vector<JButton>();
        for (int i=0; i<calcoli; i++)
        {
            {
                JTextField tmp = new JTextField("0", 10);
                tmp.addActionListener(this);
                //tmp.addKeyListener(this);
                tmp.addFocusListener(this);
                txtSpazio.add(tmp);
            }
            {
                JTextField tmp = new JTextField("0", 10);
                tmp.addActionListener(this);
                //tmp.addKeyListener(this);
                tmp.addFocusListener(this);
                txtTempo.add(tmp);
            }
            {
                JTextField tmp = new JTextField("0", 10);
                tmp.addActionListener(this);
                //tmp.addKeyListener(this);
                tmp.addFocusListener(this);
                txtVelocita.add(tmp);
            }
            {
                JTextField tmp = new JTextField("0", 10);
                tmp.addActionListener(this);
                //tmp.addKeyListener(this);
                tmp.addFocusListener(this);
                txtMassa.add(tmp);
            }
            {
                JTextField tmp = new JTextField("0", 10);
                tmp.addActionListener(this);
                //tmp.addKeyListener(this);
                tmp.addFocusListener(this);
                txtEnergia.add(tmp);
            }
            {
                JButton tmp = new JButton("+");
                tmp.setFont(tmp.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
                tmp.setToolTipText("Aggiungi una riga");
                //tmp.setBorderPainted(false);
                //tmp.setFocusPainted(false);
                //tmp.setContentAreaFilled(false);
                tmp.addActionListener(this);
                btnAggiungi.add(tmp);
            }
            {
                JButton tmp = new JButton("-");
                tmp.setFont(tmp.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
                tmp.setToolTipText("Rimuovi questa riga");
                //tmp.setBorderPainted(false);
                //tmp.setFocusPainted(false);
                //tmp.setContentAreaFilled(false);
                tmp.addActionListener(this);
                btnRimuovi.add(tmp);
            }
            calcoloEnergiaCinetica.add(new CalcoloEnergiaCinetica());
        }
    }
    
    /**
     * inizializza i componenti statici: i combo box delle unità di misura
     */
    private void inizializzaComponentiStatici()
    {
        // Inizializzazione dei componenti in alto
        cbbSpazio = new JComboBox();
        cbbSpazio.addItem("Spazio [km]");
        cbbSpazio.addItem("Spazio [m]");
        cbbSpazio.addItem("Spazio [mm]");
        cbbSpazio.addItem("Spazio [μm]");
        cbbSpazio.setFont(cbbSpazio.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
        cbbSpazio.setEditable(false);
        cbbSpazio.addActionListener(this);
        cbbSpazio.setSelectedIndex(1);
        
        cbbTempo = new JComboBox();
        cbbTempo.addItem("Tempo [h]");
        cbbTempo.addItem("Tempo [min]");
        cbbTempo.addItem("Tempo [s]");
        cbbTempo.addItem("Tempo [μs]");
        cbbTempo.setFont(cbbTempo.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
        cbbTempo.setEditable(false);
        cbbTempo.addActionListener(this);
        cbbTempo.setSelectedIndex(2);
        
        cbbVelocita = new JComboBox();
        cbbVelocita.addItem("Velocità [km/h]");
        cbbVelocita.addItem("Velocità [m/s]");
        cbbVelocita.setFont(cbbVelocita.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
        cbbVelocita.setEditable(false);
        cbbVelocita.addActionListener(this);
        cbbVelocita.setSelectedIndex(1);
        
        cbbMassa = new JComboBox();
        cbbMassa.addItem("Massa [Mg]");     // SI: Megagrammo; Comunemente chiamata Tonnellata, più precisamente Tonnellata metrica (1000 kg)
        cbbMassa.addItem("Massa [kg]");
        cbbMassa.addItem("Massa [g]");
        cbbMassa.addItem("Massa [mg]");
        cbbMassa.addItem("Massa [μg]");
        cbbMassa.setFont(cbbMassa.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
        cbbMassa.setEditable(false);
        cbbMassa.addActionListener(this);
        cbbMassa.setSelectedIndex(2);
        
        cbbEnergia = new JComboBox();
        cbbEnergia.addItem("Energia [MJ]");
        cbbEnergia.addItem("Energia [kJ]");
        cbbEnergia.addItem("Energia [J]");
        cbbEnergia.addItem("Energia [mJ]");
        cbbEnergia.addItem("Energia [μJ]");
        cbbEnergia.setFont(cbbEnergia.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
        cbbEnergia.setEditable(false);
        cbbEnergia.addActionListener(this);
        cbbEnergia.setSelectedIndex(2);
    }
    
    /**
     * Posiziona i componenti
     */
    private void posizionaComponenti()
    {
        // Prepara il GroupLayout
        this.remove(pannello);                              // Rimuove il pannello
        pannello = new JPanel();                            // Ne crea uno nuovo
        GroupLayout layout = new GroupLayout(pannello);     // Crea un nuovo GroupLayout
        pannello.setLayout(layout);                         // Applica un nuovo GroupLayout
        this.add(pannello);                                 // Reinserisce il pannello immacolato. Tutto questo perché altrimenti non si possono eliminare le righe in eccesso.
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        // Imposta i gruppi orizzontali
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        layout.setHorizontalGroup(hGroup);
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(LEADING);
            hGroup.addGroup(pGroup);
            for (int i=0; i<calcoli; i++)
            {
                pGroup.addComponent(btnRimuovi.get(i));
            }
        }
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(LEADING);
            hGroup.addGroup(pGroup);
            pGroup.addComponent(cbbSpazio);
            for (int i=0; i<calcoli; i++)
            {
                pGroup.addComponent(txtSpazio.get(i));
            }
        }
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(LEADING);
            hGroup.addGroup(pGroup);
            pGroup.addComponent(cbbTempo);
            for (int i=0; i<calcoli; i++)
            {
                pGroup.addComponent(txtTempo.get(i));
            }
        }
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(LEADING);
            hGroup.addGroup(pGroup);
            pGroup.addComponent(cbbVelocita);
            for (int i=0; i<calcoli; i++)
            {
                pGroup.addComponent(txtVelocita.get(i));
            }
        }
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(LEADING);
            hGroup.addGroup(pGroup);
            pGroup.addComponent(cbbMassa);
            for (int i=0; i<calcoli; i++)
            {
                pGroup.addComponent(txtMassa.get(i));
            }
        }
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(LEADING);
            hGroup.addGroup(pGroup);
            pGroup.addComponent(cbbEnergia);
            for (int i=0; i<calcoli; i++)
            {
                pGroup.addComponent(txtEnergia.get(i));
            }
        }
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(LEADING);
            hGroup.addGroup(pGroup);
            for (int i=0; i<calcoli; i++)
            {
                pGroup.addComponent(btnAggiungi.get(i));
            }
        }
        // Imposta la stessa dimensione per i pulsanti Aggiungi e Rimuovi
        for (int i=0; i<calcoli; i++)
        {
            layout.linkSize(SwingConstants.HORIZONTAL, btnAggiungi.get(i), btnRimuovi.get(i));
        }
        
        // Imposta i gruppi verticali
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        layout.setVerticalGroup(vGroup);
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(BASELINE, false);
            vGroup.addGroup(pGroup);
            pGroup.addComponent(cbbSpazio);
            pGroup.addComponent(cbbTempo);
            pGroup.addComponent(cbbVelocita);
            pGroup.addComponent(cbbMassa);
            pGroup.addComponent(cbbEnergia);
        }
        for (int i=0; i<calcoli; i++)
        {
            GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(BASELINE, false);
            vGroup.addGroup(pGroup);
            pGroup.addComponent(btnRimuovi.get(i));
            pGroup.addComponent(txtSpazio.get(i));
            pGroup.addComponent(txtTempo.get(i));
            pGroup.addComponent(txtVelocita.get(i));
            pGroup.addComponent(txtMassa.get(i));
            pGroup.addComponent(txtEnergia.get(i));
            pGroup.addComponent(btnAggiungi.get(i));
        }
        setMinimumSize(getPreferredSize()); // Imposta la dimensione minima
        pack();                             // Setta la dimensione minima
    }
    
    /**
     * Ridisegna i componenti della griglia.
     * indice: è l'indice su cui è stata scelta l'azione da compiere.
     * aggiungi: true per aggiungere un rigo sotto l'indice corrente; false per cancellare il rigo all'indice corrente
     */
    private void ridisegnaGriglia(int indice, boolean aggiungi)
    {
        // Aggiunge o rimuove una riga dalla griglia
        if ((!aggiungi)&&(calcoli==1))
        {
            // Non fa niente se tenti ti cancellare l'ultima riga
            JOptionPane.showMessageDialog(null, "INFORMAZIONE\nNon puoi cancellare tutte le righe.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
        else    // Altrimenti procedi
        {
            if (aggiungi)
            {
                calcoli++;
                // Vettore dei componenti
                {
                    JButton tmp = new JButton("-");
                    tmp.setFont(tmp.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
                    tmp.setToolTipText("Rimuovi questa riga");
                    tmp.addActionListener(this);
                    btnRimuovi.add(indice, tmp);
                }
                {
                    JTextField tmp = new JTextField("0", 10);
                    tmp.addActionListener(this);
                    tmp.addFocusListener(this);
                    txtSpazio.add(indice, tmp);
                }
                {
                    JTextField tmp = new JTextField("0", 10);
                    tmp.addActionListener(this);
                    tmp.addFocusListener(this);
                    txtTempo.add(indice, tmp);
                }
                {
                    JTextField tmp = new JTextField("0", 10);
                    tmp.addActionListener(this);
                    tmp.addFocusListener(this);
                    txtVelocita.add(indice, tmp);
                }
                {
                    JTextField tmp = new JTextField("0", 10);
                    tmp.addActionListener(this);
                    tmp.addFocusListener(this);
                    txtMassa.add(indice, tmp);
                }
                {
                    JTextField tmp = new JTextField("0", 10);
                    tmp.addActionListener(this);
                    tmp.addFocusListener(this);
                    txtEnergia.add(indice, tmp);
                }
                {
                    JButton tmp = new JButton("+");
                    tmp.setFont(tmp.getFont().deriveFont(Font.BOLD));   // Applica lo stile grassetto
                    tmp.setToolTipText("Aggiungi una riga");
                    tmp.addActionListener(this);
                    btnAggiungi.add(indice, tmp);
                }
                // Vettore dei calcoli
                calcoloEnergiaCinetica.add(indice, new CalcoloEnergiaCinetica());
            }
            else
            {
                // rimuove dal vettore
                calcoli--;
                
                // Vettore dei componenti
                btnRimuovi.removeElementAt(indice);
                txtSpazio.removeElementAt(indice);
                txtTempo.removeElementAt(indice);
                txtVelocita.removeElementAt(indice);
                txtMassa.removeElementAt(indice);
                txtEnergia.removeElementAt(indice);
                btnAggiungi.removeElementAt(indice);
                // Vettore dei calcoli
                calcoloEnergiaCinetica.removeElementAt(indice);
            }
            // aggiorna la grafica
            posizionaComponenti();
        }
    }
    
    /**
     * Esce dal programma
     */
    private void esci()
    {
        int scelta = JOptionPane.showConfirmDialog(null,"Vuoi uscire dal programma?", getTitle(), JOptionPane.YES_NO_OPTION);
        if (scelta==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    
    /** Aggiorna i calcoli */
    private void aggiornaCalcoli(Object obj)
    {
        for (int i=0; i<calcoli; i++)
        {
            if ((obj.equals((Object)txtSpazio.get(i)))||(obj.equals((Object)txtTempo.get(i)))||(obj.equals((Object)txtVelocita.get(i)))||(obj.equals((Object)txtMassa.get(i)))||(obj.equals((Object)txtEnergia.get(i))))
            {
                String stringa = ((JTextField)obj).getText(); // Legge la stringa digitata
                if (stringa.equals("")) stringa = "0";                  // Scrive "0" se è vuota
                try
                {
                    Double numero = new Double(stringa);                // Prova a convertire la stringa in numero
                    if (obj.equals((Object)txtSpazio.get(i)))
                    {
                        calcoloEnergiaCinetica.get(i).setSpazio(numero*fattoreSpazio);    // Aggiorna lo spazio
                        {
                            JTextField tmp = txtTempo.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getTempo()/fattoreTempo));
                            tmp.select(0, 0);
                            txtTempo.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtVelocita.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getVelocita()/fattoreVelocita));
                            tmp.select(0, 0);
                            txtVelocita.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtMassa.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getMassa()/fattoreMassa));
                            tmp.select(0, 0);
                            txtMassa.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtEnergia.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getEnergia()/fattoreEnergia));
                            tmp.select(0, 0);
                            txtEnergia.set(i, tmp);
                        }
                    }
                    else if (obj.equals((Object)txtTempo.get(i)))
                    {
                        calcoloEnergiaCinetica.get(i).setTempo(numero*fattoreTempo);     // Aggiorna il tempo
                        {
                            JTextField tmp = txtSpazio.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getSpazio()/fattoreSpazio));
                            tmp.select(0, 0);
                            txtSpazio.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtVelocita.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getVelocita()/fattoreVelocita));
                            tmp.select(0, 0);
                            txtVelocita.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtMassa.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getMassa()/fattoreMassa));
                            tmp.select(0, 0);
                            txtMassa.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtEnergia.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getEnergia()/fattoreEnergia));
                            tmp.select(0, 0);
                            txtEnergia.set(i, tmp);
                        }
                    }
                    else if (obj.equals((Object)txtVelocita.get(i)))
                    {
                        calcoloEnergiaCinetica.get(i).setVelocita(numero*fattoreVelocita);  // Aggiorna la velocità
                        {
                            JTextField tmp = txtSpazio.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getSpazio()/fattoreSpazio));
                            tmp.select(0, 0);
                            txtSpazio.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtTempo.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getTempo()/fattoreTempo));
                            tmp.select(0, 0);
                            txtTempo.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtMassa.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getMassa()/fattoreMassa));
                            tmp.select(0, 0);
                            txtMassa.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtEnergia.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getEnergia()/fattoreEnergia));
                            tmp.select(0, 0);
                            txtEnergia.set(i, tmp);
                        }
                    }
                    else if (obj.equals((Object)txtMassa.get(i)))
                    {
                        calcoloEnergiaCinetica.get(i).setMassa(numero*fattoreMassa);     // Aggiorna la massa
                        {
                            JTextField tmp = txtSpazio.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getSpazio()/fattoreSpazio));
                            tmp.select(0, 0);
                            txtSpazio.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtTempo.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getTempo()/fattoreTempo));
                            tmp.select(0, 0);
                            txtTempo.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtVelocita.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getVelocita()/fattoreVelocita));
                            tmp.select(0, 0);
                            txtVelocita.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtEnergia.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getEnergia()/fattoreEnergia));
                            tmp.select(0, 0);
                            txtEnergia.set(i, tmp);
                        }
                    }
                    else if (obj.equals((Object)txtEnergia.get(i)))
                    {
                        calcoloEnergiaCinetica.get(i).setEnergia(numero*fattoreEnergia);   // Aggiorna l'energia
                        {
                            JTextField tmp = txtSpazio.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getSpazio()/fattoreSpazio));
                            tmp.select(0, 0);
                            txtSpazio.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtTempo.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getTempo()/fattoreTempo));
                            tmp.select(0, 0);
                            txtTempo.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtVelocita.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getVelocita()/fattoreVelocita));
                            tmp.select(0, 0);
                            txtVelocita.set(i, tmp);
                        }
                        
                        {
                            JTextField tmp = txtMassa.get(i);
                            tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getMassa()/fattoreMassa));
                            tmp.select(0, 0);
                            txtMassa.set(i, tmp);
                        }
                    }
                }
                catch(Exception exc){}
                
                break;
            }
        }
    }
    
    /**
     * Ascoltatore dei tasti
     */
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e){}
    
    /**
     * Ascoltatore dei pulsanti
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof JComboBox)
        {
            if (e.getSource().equals((Object)cbbSpazio))
            {
                String unita = (String)cbbSpazio.getSelectedItem();
                String compara = "Spazio ";
                if (unita.equals(compara+"[km]"))
                {
                    fattoreSpazio = 1000;
                }
                else if (unita.equals(compara+"[m]"))
                {
                    fattoreSpazio = 1;
                }
                else if (unita.equals(compara+"[mm]"))
                {
                    fattoreSpazio = 0.001;
                }
                else if (unita.equals(compara+"[μm]"))
                {
                    fattoreSpazio = 0.000001;
                }
                else
                {
                    System.out.println("Errore di runtime: il programmatore ha perso il collegamento tra cervello e polpastrelli!");
                }
                // Aggiorna i valori nelle celle
                for (int i=0; i<calcoli; i++)
                {
                    JTextField tmp = txtSpazio.get(i);
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getSpazio()/fattoreSpazio));
                    tmp.select(0, 0);
                    txtSpazio.set(i, tmp);
                }
            }
            else if (e.getSource().equals((Object)cbbTempo))
            {
                String unita = (String)cbbTempo.getSelectedItem();
                String compara = "Tempo ";
                if (unita.equals(compara+"[h]"))
                {
                    fattoreTempo = 3600;
                }
                else if (unita.equals(compara+"[min]"))
                {
                    fattoreTempo = 60;
                }
                else if (unita.equals(compara+"[s]"))
                {
                    fattoreTempo = 1;
                }
                else if (unita.equals(compara+"[ms]"))
                {
                    fattoreTempo = 0.001;
                }
                else if (unita.equals(compara+"[μs]"))
                {
                    fattoreTempo = 0.000001;
                }
                else
                {
                    System.out.println("Errore di runtime: il programmatore ha perso il collegamento tra cervello e polpastrelli!");
                }
                // Aggiorna i valori nelle celle
                for (int i=0; i<calcoli; i++)
                {
                    JTextField tmp = txtTempo.get(i);
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getTempo()/fattoreTempo));
                    tmp.select(0, 0);
                    txtTempo.set(i, tmp);
                }
            }
            else if (e.getSource().equals((Object)cbbVelocita))
            {
                String unita = (String)cbbVelocita.getSelectedItem();
                String compara = "Velocità ";
                if (unita.equals(compara+"[km/h]"))
                {
                    fattoreVelocita = new Double(1000)/new Double(3600);     // km/h=1000m/3600s --> km/h=(1000/3600)m/s
                }
                else if (unita.equals(compara+"[m/s]"))
                {
                    fattoreVelocita = 1;
                }
                else
                {
                    System.out.println("Errore di runtime: il programmatore ha perso il collegamento tra cervello e polpastrelli!");
                }
                // Aggiorna i valori nelle celle
                for (int i=0; i<calcoli; i++)
                {
                    JTextField tmp = txtVelocita.get(i);
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getVelocita()/fattoreVelocita));
                    tmp.select(0, 0);
                    txtVelocita.set(i, tmp);
                }
            }
            else if (e.getSource().equals((Object)cbbMassa))
            {
                String unita = (String)cbbMassa.getSelectedItem();
                String compara = "Massa ";
                if (unita.equals(compara+"[Mg]"))
                {
                    fattoreMassa = 1000000;
                }
                else if (unita.equals(compara+"[kg]"))
                {
                    fattoreMassa = 1000;
                }
                else if (unita.equals(compara+"[g]"))
                {
                    fattoreMassa = 1;
                }
                else if (unita.equals(compara+"[mg]"))
                {
                    fattoreMassa = 0.001;
                }
                else if (unita.equals(compara+"[μg]"))
                {
                    fattoreMassa = 0.000001;
                }
                else
                {
                    System.out.println("Errore di runtime: il programmatore ha perso il collegamento tra cervello e polpastrelli!");
                }
                // Aggiorna i valori nelle celle
                for (int i=0; i<calcoli; i++)
                {
                    JTextField tmp = txtMassa.get(i);
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getMassa()/fattoreMassa));
                    tmp.select(0, 0);
                    txtMassa.set(i, tmp);
                }
            }
            else if (e.getSource().equals((Object)cbbEnergia))
            {
                String unita = (String)cbbEnergia.getSelectedItem();
                String compara = "Energia ";
                if (unita.equals(compara+"[MJ]"))
                {
                    fattoreEnergia = 1000000;
                }
                else if (unita.equals(compara+"[kJ]"))
                {
                    fattoreEnergia = 1000;
                }
                else if (unita.equals(compara+"[J]"))
                {
                    fattoreEnergia = 1;
                }
                else if (unita.equals(compara+"[mJ]"))
                {
                    fattoreEnergia = 0.001;
                }
                else if (unita.equals(compara+"[μJ]"))
                {
                    fattoreEnergia = 0.000001;
                }
                else
                {
                    System.out.println("Errore di runtime: il programmatore ha perso il collegamento tra cervello e polpastrelli!");
                }
                // Aggiorna i valori nelle celle
                for (int i=0; i<calcoli; i++)
                {
                    JTextField tmp = txtEnergia.get(i);
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getEnergia()/fattoreEnergia));
                    tmp.select(0, 0);
                    txtEnergia.set(i, tmp);
                }
            }
        }
        else if (e.getSource() instanceof JTextField)
        {
            aggiornaCalcoli(e.getSource());
        }
        else if (e.getSource() instanceof JButton)
        {
            for (int i=0; i<calcoli; i++)
            {
                if (e.getSource().equals((Object)btnAggiungi.get(i)))
                {
                    ridisegnaGriglia(i+1, true);
                    break;
                }
                else if (e.getSource().equals((Object)btnRimuovi.get(i)))
                {
                    ridisegnaGriglia(i, false);
                    break;
                }
            }
        }
        else
        {
            // Se modifichi questo messaggio sei stronzo
            System.out.println("Errore di programmazione: se leggi questo messaggio in run-time sei deficiente");
        }
    }
    
    /**
     * Ascoltatore del focus
     */
    public void focusGained(FocusEvent e)
    {
        Object oggetto = e.getSource();
        for (int i=0; i<calcoli; i++)
        {
            if (oggetto.equals((Object)txtSpazio.get(i)))
            {
                JTextField tmp = txtSpazio.get(i);
                tmp.selectAll();
                txtSpazio.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtTempo.get(i)))
            {
                JTextField tmp = txtTempo.get(i);
                tmp.selectAll();
                txtTempo.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtVelocita.get(i)))
            {
                JTextField tmp = txtVelocita.get(i);
                tmp.selectAll();
                txtVelocita.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtMassa.get(i)))
            {
                JTextField tmp = txtMassa.get(i);
                tmp.selectAll();
                txtMassa.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtEnergia.get(i)))
            {
                JTextField tmp = txtEnergia.get(i);
                tmp.selectAll();
                txtEnergia.set(i, tmp);
            }
        }
    }
    public void focusLost(FocusEvent e) // Quando un oggetto perde il focus
    {
        Object oggetto = e.getSource();
        for (int i=0; i<calcoli; i++)
        {
            if (oggetto.equals((Object)txtSpazio.get(i)))
            {
                JTextField tmp = txtSpazio.get(i);
                // La variabile del calcolo viene aggiornata
                CalcoloEnergiaCinetica cine = calcoloEnergiaCinetica.get(i);
                Double numero = cine.getSpazio();
                try
                {
                    numero = new Double(tmp.getText());
                }
                catch(Exception exc)
                {
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getSpazio()/fattoreSpazio));
                }
                cine.setSpazio(numero);
                
                // Il cursore di selezione si sposta all'inizio
                tmp.select(0, 0);   
                txtSpazio.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtTempo.get(i)))
            {
                JTextField tmp = txtTempo.get(i);
                // La variabile del calcolo viene aggiornata
                CalcoloEnergiaCinetica cine = calcoloEnergiaCinetica.get(i);
                Double numero = cine.getTempo();
                try
                {
                    numero = new Double(tmp.getText());
                }
                catch(Exception exc)
                {
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getTempo()/fattoreTempo));
                }
                cine.setTempo(numero);
                
                // Il cursore di selezione si sposta all'inizio
                tmp.select(0, 0);   
                txtTempo.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtVelocita.get(i)))
            {
                JTextField tmp = txtVelocita.get(i);
                // La variabile del calcolo viene aggiornata
                CalcoloEnergiaCinetica cine = calcoloEnergiaCinetica.get(i);
                Double numero = cine.getVelocita();
                try
                {
                    numero = new Double(tmp.getText());
                }
                catch(Exception exc)
                {
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getVelocita()/fattoreVelocita));
                }
                cine.setVelocita(numero);
                
                // Il cursore di selezione si sposta all'inizio
                tmp.select(0, 0);   
                txtVelocita.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtMassa.get(i)))
            {
                JTextField tmp = txtMassa.get(i);
                // La variabile del calcolo viene aggiornata
                CalcoloEnergiaCinetica cine = calcoloEnergiaCinetica.get(i);
                Double numero = cine.getMassa();
                try
                {
                    numero = new Double(tmp.getText());
                }
                catch(Exception exc)
                {
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getMassa()/fattoreMassa));
                }
                cine.setMassa(numero);
                
                // Il cursore di selezione si sposta all'inizio
                tmp.select(0, 0);   
                txtMassa.set(i, tmp);
            }
            else if (oggetto.equals((Object)txtEnergia.get(i)))
            {
                JTextField tmp = txtEnergia.get(i);
                // La variabile del calcolo viene aggiornata
                CalcoloEnergiaCinetica cine = calcoloEnergiaCinetica.get(i);
                Double numero = cine.getEnergia();
                try
                {
                    numero = new Double(tmp.getText());
                }
                catch(Exception exc)
                {
                    tmp.setText(formatoNumeri.format(calcoloEnergiaCinetica.get(i).getEnergia()/fattoreEnergia));
                }
                cine.setEnergia(numero);
                
                // Il cursore di selezione si sposta all'inizio
                tmp.select(0, 0);   
                txtEnergia.set(i, tmp);
            }
        }
        aggiornaCalcoli(e.getSource());
    }
    
    /**
     * Ascoltatore della finestra
     */
    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowClosing(WindowEvent e)
    {
        esci();
    }
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
}
