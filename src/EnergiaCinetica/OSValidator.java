/**
 * La classe identifica il sistema operativo in cui è in esecuzione il programma
 * 
 * Autore: Antonio Bianco
 * Creazione: 16/05/1012
 * Ultima modifica: 16/05/1012
 * Versione: 1.0 stable
 */
public class OSValidator
{
    public static String getOSName()
    {
        return System.getProperty("os.name");
    }
 
    public static boolean isWindows()
    {
        String os = System.getProperty("os.name").toLowerCase();
        // windows
        return (os.indexOf("win") >= 0);
    }
 
    public static boolean isMac()
    {
        String os = System.getProperty("os.name").toLowerCase();
        // Mac
        return (os.indexOf("mac") >= 0);
    }
    
    public static boolean isUnix()
    {
        String os = System.getProperty("os.name").toLowerCase();
        // linux or unix
        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
    }
 
    public static boolean isSolaris()
    {
        String os = System.getProperty("os.name").toLowerCase();
        // Solaris
        return (os.indexOf("sunos") >= 0);
    }
    
    /**
     * Ritorna il nome del tema da utilizzare per avere un'integrazione grafica con il sistema operativo
     */
    public static String getTemaIntegrazione()
    {
        if (isWindows()) return "Windows";
        else if (isMac()) return "Mac Os X";
        else if (isUnix()) return "GTK+";
        else return "Nimbus";
    }
}