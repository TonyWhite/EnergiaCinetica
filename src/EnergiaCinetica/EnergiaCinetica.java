/**
 * La classe avvia il programma
 * 
 * Autore: Antonio Bianco
 * Creazione: 09/09/2012
 * Ultima modifica: 02/01/2013
 * Versione: 1.1.2 stable
 */
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
public class EnergiaCinetica implements Runnable
{
    // Istanza di variabili
    private Finestra finestra;
    private IconaAnimata iconaAnimata;
    
    public EnergiaCinetica()
    {
        iconaAnimata = new IconaAnimata("img/icona/128/", "png", 12);
        finestra = new Finestra();
    }
    
    public static void main(String args[])
    {
        // Carica il look and feel predefinito di sistema
        try
        {
            String tema = OSValidator.getTemaIntegrazione();
            String temaClassName = "";
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if (tema.equals(info.getName()))
                {
                    temaClassName = info.getClassName();
                    break;
                }
            }
            UIManager.setLookAndFeel(temaClassName);
        }
        catch(Exception e){}
        // Carica il look and feel predefinito di sistema (non funziona su XFCE)
        /*try
        {
            String nome = UIManager.getSystemLookAndFeelClassName();
            System.out.println(nome);
            UIManager.setLookAndFeel(nome);
        }
        catch (Exception e){}*/
        /*try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if (info.getName().equals("Nimbus"))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception e){}*/
        
        new Thread(new EnergiaCinetica()).start();
    }
    
    /** Thread  per animare l'icona della finestra */
    public void run()
    {
        while(true)
        {
            try
            {
                finestra.setIconImage(iconaAnimata.getNext());
                Thread.sleep(100);
            }
            catch(Exception e){}
        }
    }
}
