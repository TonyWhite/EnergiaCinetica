/**
 * La classe prepara i fotogrammi per l'icona animata
 * 
 * Autore: Antonio Bianco
 * Creazione: 09/09/2012
 * Ultima modifica: 09/09/2012
 * Versione: 1.0 stable
 */
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
public class IconaAnimata
{
    // Istanza di variabili
    private Image fotogramma[];
    private int indice = 0;
    
    /**
     * Crea l'oggetto
     */
    public IconaAnimata(String percorso, String estensione, int fotogrammi)
    {
        // Inizializza le variabili
        fotogramma = new Image[fotogrammi];
        for (int i=0; i<fotogrammi; i++)
        {
            // Prepara il nome del file a 3 cifre.
            String nomeFile = "";
            if (i<10) nomeFile = "00";
            else if (i<100) nomeFile = "0";
            nomeFile = percorso + nomeFile + i + "." + estensione;
            fotogramma[i] = getIcona(nomeFile);
        }
    }
    
    /**
     * Ritorna il prossimo fotogramma da visualizzare
     */
    public Image getNext()
    {
        indice++;
        if (indice>=fotogramma.length) indice=0;
        return fotogramma[indice];
    }
    
    /**
     * Ritorna un'icona
     */
    private Image getIcona(String path)
    {
        try
        {
            URL imgURL = IconaAnimata.class.getResource(path);
            if (imgURL != null)
            {
                return Toolkit.getDefaultToolkit().getImage(imgURL);
            }
        }
        catch(Exception e){}
        return null;
    }
}
