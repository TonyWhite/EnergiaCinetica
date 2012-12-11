/**
 * La classe calcola l'energia cinetica ed aggiorna automaticamente i valori incoerenti.
 * 
 * Autore: Antonio Bianco
 * Creazione: 08/09/2012
 * Ultima modifica: 09/09/2012
 * Versione: 1.0 stable
 */

public class CalcoloEnergiaCinetica
{
    // Istanza di variabili
    private double massa;       // Kilogrammi
    private double spazio;      // Metri
    private double tempo;       // Secondi
    private double velocita;    // Metri al secondo
    private double energia;     // Joule
    
    /**
     * Constructor for objects of class CalcoloEnergiaCinetica
     */
    public CalcoloEnergiaCinetica()
    {
        // Inizializza le variabili
        massa = 0;
        spazio = 0;
        tempo = 0;
        velocita = 0;
        energia = 0;
    }
    
    /** Calcola la massa*/
    private boolean calcolaMassa()
    {
        boolean tuttoBene = false;
        if (velocita!=0)        // Calcola con la formula inversa dell'energia
        {
            massa = (2*energia)/(velocita*velocita);
            tuttoBene = true;
        }
        return tuttoBene;
    }
    
    /** Calcola lo spazio*/
    private boolean calcolaSpazio()
    {
        boolean tuttoBene = false;
        if ((velocita!=0)&&(tempo!=0))  // Calcola con la formula inversa della velocità
        {
            spazio = velocita*tempo;
            tuttoBene = true;
        }
        else if ((!tuttoBene)&&(massa!=0)&&(tempo!=0))   // Calcola con la formula inversa dell'energia
        {
            spazio = Math.sqrt((2*energia)/massa)*tempo;
            tuttoBene = true;
        }
        return tuttoBene;
    }
    
    /** Calcola il tempo*/
    private boolean calcolaTempo()
    {
        boolean tuttoBene = false;
        if ((spazio!=0)&&(velocita!=0))     // Calcola con la formula inversa della velocità
        {
            tempo = spazio/velocita;
            tuttoBene = true;
        }
        else if ((spazio!=0)&&(massa!=0))   // Calcola con la formula inversa dell'energia
        {
            tempo = spazio/Math.sqrt((2*energia)/massa);
            tuttoBene = true;
        }
        return tuttoBene;
    }
    
    /** Calcola la velocità */
    private boolean calcolaVelocita()
    {
        boolean tuttoBene = false;
        if ((spazio!=0)&&(tempo!=0))        // Calcola con la formula canonica
        {
            velocita = spazio/tempo;
            tuttoBene = true;
        }
        else if ((!tuttoBene)&&(massa!=0))      // Calcola con la formula inversa dell'energia
        {
            velocita = Math.sqrt((2*energia)/massa);
            tuttoBene = true;
        }
        return tuttoBene;
    }
    
    /** Calcola l'energia cinetica */
    private boolean calcolaEnergia()
    {
        boolean tuttoBene = false;
        if ((massa!=0)&&(velocita!=0))  // Calcola con la formula canonica
        {
            energia = (massa*velocita*velocita)/2;
            tuttoBene = true;
        }
        return tuttoBene;
    }
    
    /** Ritorna il valore di massa */
    public double getMassa()
    {
        return massa;
    }
    
    /** Ritorna il valore di spazio */
    public double getSpazio()
    {
        return spazio;
    }
    
    /** Ritorna il valore del tempo */
    public double getTempo()
    {
        return tempo;
    }
    
    /** Ritorna il valore della velocità */
    public double getVelocita()
    {
        return velocita;
    }
    
    /** Ritorna il valore dell'energia cinetica */
    public double getEnergia()
    {
        return energia;
    }
    
    /** Setta il valore di massa [kg]*/
    public void setMassa(double massa)
    {
        this.massa = massa;
        if (massa==0) return;
        if ((velocita!=0)&&(energia==0))                // Se c'è solo la velocità...
        {
            calcolaEnergia();                           // ...calcola l'energia
        }
        else if ((velocita==0)&&(energia!=0))           // Se c'è solo l'energia...
        {
            if ((spazio==0)||(tempo==0))                // ...e possiamo gestire la velocità come ci pare...
            {
                calcolaVelocita();                      // ...calcola la velocità.
                // calcola spazio e tempo in funzione della velocità
                calcolaSpazio();                                
                calcolaTempo();
            }
            else                                        // Ma se spazio e tempo sono definiti, siamo vincolati con la velocità
            {
                tempo = 0;                              // Ci svincoliamo modificando il tempo
                calcolaVelocita();                      // ignora la formula spazio/tempo e prende la formula inversa dell'energia
                calcolaTempo();                         // ricalcola il tempo.
            }
        }
        else if ((velocita!=0)&&(energia!=0))           // Se c'è sia la velocità che l'energia...
        {
            calcolaEnergia();                           // ...ricalcolo l'energia.
        }
        else if ((velocita==0)&&(energia==0))           // Se manca sia la velocità che l'energia...
        {
            if (calcolaVelocita()) calcolaEnergia();    // ...prova a calcolare la velocità e se va bene calcola l'energia.
        }
    }
    
    /** Setta il valore di spazio [m]*/
    public void setSpazio(double spazio)
    {
        this.spazio = spazio;
        if (spazio==0) return;
        boolean condizioneSufficiente = false;  // Condizione sufficiente per proseguire i calcoli
        if ((tempo!=0)&&(velocita==0))                      // Se c'è solo il tempo...
        {
            condizioneSufficiente = calcolaVelocita();      // ...calcola la velocità.
        }
        else if ((tempo==0)&&(velocita!=0))                 // Se c'è solo la velocità...
        {
            condizioneSufficiente = calcolaTempo();         // ...calcola il tempo.
        }
        else if ((tempo!=0)&&(velocita!=0))                 // Se ci sono sia il tempo che la velocità...
        {
            condizioneSufficiente = calcolaVelocita();      // ...ricalcola la velocità
        }
        else if ((tempo==0)&&(velocita==0))                 // Se mancano sia il tempo che la velocità...
        {
            condizioneSufficiente = calcolaVelocita();      // ...prova a calcolare la velocità...
            if (condizioneSufficiente) calcolaTempo();      // ... e se va bene calcola il tempo.
            condizioneSufficiente = false;
        }
        
        if (condizioneSufficiente)      // Se è possibile procedere al calcolo della massa e dell'energia
        {
            if ((massa!=0)&&(energia==0))                   // Se c'è solo la massa...
            {
                calcolaEnergia();                           // ...calcola l'energia.
            }
            else if ((massa==0)&&(energia!=0))              // Se c'è solo l'energia...
            {
                calcolaMassa();                             // ...calcola la massa.
            }
            else if ((massa!=0)&&(energia!=0))              // Se c'è sia la massa che l'energia...
            {
                calcolaEnergia();                           // ...ricalcola l'energia.
            }
            else if ((massa==0)&&(energia==0))              // Se mancano sia la massa che l'energia...
            {
                // Non si hanno dati sufficienti per ricavare l'energia o la massa
            }
        }
    }
    
    /** Setta il valore di tempo [s]*/
    public void setTempo(double tempo)
    {
        this.tempo = tempo;
        if (tempo==0) return;
        boolean condizioneSufficiente = false;  // Condizione sufficiente per proseguire i calcoli
        if ((spazio!=0)&&(velocita==0))                     // Se c'è solo lo spazio...
        {
            condizioneSufficiente = calcolaVelocita();      // ...calcola la velocità.
        }
        else if ((spazio==0)&&(velocita!=0))                 // Se c'è solo la velocità...
        {
            condizioneSufficiente = calcolaSpazio();        // ...calcola lo spazio.
        }
        else if ((spazio!=0)&&(velocita!=0))                // Se ci sono sia lo spazio che la velocità...
        {
            condizioneSufficiente = calcolaVelocita();      // ...ricalcola la velocità.
        }
        else if ((spazio==0)&&(velocita==0))                 // Se mancano sia lo spazio che la velocità...
        {
            condizioneSufficiente = calcolaVelocita();      // ...prova a calcolare la velocità...
            if (condizioneSufficiente) calcolaSpazio();     // ... e se va bene calcola lo spazio.
            condizioneSufficiente = false;
        }
        
        if (condizioneSufficiente)      // Se la velocità è stata calcolata correttamente
        {
            if ((massa!=0)&&(energia==0))                   // Se c'è solo la massa...
            {
                calcolaEnergia();                           // ...calcola l'energia.
            }
            else if ((massa==0)&&(energia!=0))              // Se c'è solo l'energia...
            {
                calcolaMassa();                             // ...calcola la massa.
            }
            else if ((massa!=0)&&(energia!=0))              // Se c'è sia la massa che l'energia...
            {
                calcolaEnergia();                           // ...ricalcola l'energia.
            }
            else if ((massa==0)&&(energia==0))              // Se mancano sia la massa che l'energia...
            {
                // Non si hanno dati sufficienti per ricavare l'energia o la massa
            }
        }
    }
    
    /** Setta il valore di velocità [m/s]*/
    public void setVelocita(double velocita)
    {
        this.velocita = velocita;
        if (velocita==0) return;
        // La condizione sufficiente per proseguire con i calcoli è la presenza del valore di velocità, pertanto è inutile istanziare la variabile booleana
        if ((spazio!=0)&&(tempo==0))                        // Se c'è solo lo spazio...
        {
            calcolaTempo();                                 // ...calcola il tempo.
        }
        else if ((spazio==0)&&(tempo!=0))                   // Se c'è solo il tempo...
        {
            calcolaSpazio();                                // ...calcola lo spazio.
        }
        else if ((spazio!=0)&&(tempo!=0))                   // Se ci sono sia il tempo che lo spazio...
        {
            calcolaTempo();                                 // ...ricalcola il tempo.
        }
        
        // La condizione è comunque sufficiente per continuare con il calcolo dell'energia cinetica o la massa
        if ((massa!=0)&&(energia==0))                       // Se c'è solo la massa...
        {
            calcolaEnergia();                               // ...calcola l'energia.
        }
        else if ((massa==0)&&(energia!=0))                  // Se c'è solo l'energia...
        {
            calcolaMassa();                                 // ...calcola la massa.
        }
        else if ((massa!=0)&&(energia!=0))                  // Se c'è sia la massa che l'energia...
        {
            calcolaEnergia();                               // ...ricalcola l'energia.
        }
        else if ((massa==0)&&(energia==0))                  // Se mancano sia la massa che l'energia...
        {
            // Non si hanno dati sufficienti per ricavare l'energia o la massa
        }
    }
    
    /** Setta il valore di energia [J]*/
    public void setEnergia(double energia)
    {
        this.energia = energia;
        if (energia==0) return;
        if ((massa!=0)&&(velocita==0))                          // Se c'è solo la massa...
        {
            if ((spazio==0)||(tempo==0))                        // ...e possiamo gestire la velocità come ci pare...
            {
                calcolaVelocita();                              // ...calcola la velocità.
                // calcola spazio e tempo in funzione della velocità
                calcolaSpazio();                                
                calcolaTempo();
            }
            else                                                // Ma se spazio e tempo sono definiti, siamo vincolati con la velocità
            {
                tempo = 0;                                      // Ci svincoliamo modificando il tempo
                calcolaVelocita();                              // ignora la formula spazio/tempo e prende la formula inversa dell'energia
                calcolaTempo();                                 // ricalcola il tempo.
            }
        }
        else if ((massa==0)&&(velocita!=0))                     // Se c'è solo la velocità...
        {
            calcolaMassa();                                     // ...calcola la massa.
            // calcola spazio e tempo in funzione della massa
            calcolaSpazio();
            calcolaTempo();
        }
        else if ((massa!=0)&&(velocita!=0))                     // Se c'è sia la massa che la velocità...
        {
            // Vado a modificare la velocità, ma prima devo controllare se sono già presenti i parametri spazio/tempo
            if ((spazio!=0)&&(tempo==0))                        // Se c'è solo lo spazio...
            {
                calcolaTempo();                                 // ...calcola il tempo.
            }
            else if ((spazio==0)&&(tempo!=0))                   // Se c'è solo il tempo...
            {
                calcolaSpazio();                                // ...calcola lo spazio.
            }
            else if ((spazio!=0)&&(tempo!=0))                   // Se c'è sia lo spazio che il tempo...
            {
                tempo=0;                                        // Ci svincoliamo modificando il tempo
                calcolaVelocita();                              // ignora la formula spazio/tempo e prende la formula inversa dell'energia
                calcolaTempo();                                 // ricalcola il tempo.
            }
            else if ((spazio==0)&&(tempo==0))                   // Se manca sia lo spazio che il tempo...
            {
                calcolaVelocita();                              // ...ricalcola la velocità
            }
        }
        else if ((massa==0)&&(velocita==0))                     // Se mancano sia la massa che la velocità...
        {
            if (calcolaVelocita()) calcolaMassa();      // ...prova a calcolare la velocità e se va bene calcola la massa.
        }
    }
}