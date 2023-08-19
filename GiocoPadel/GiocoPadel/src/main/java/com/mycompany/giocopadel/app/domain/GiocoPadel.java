/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GiocoPadel {
    float sconto=0;
    Scanner tastiera=new Scanner(System.in);
    private static GiocoPadel giocopadel;
    private Map<String, Padeleur> elencoPadeleur;
    private Map<Integer, Prenotazione> elencoPrenotazioni;
    private Map<Integer, CampoPadel> elencoCampiPadel;
    private Map<String, Magazzino> elencoMagazzino;
    
    private List<Observer> observers = new ArrayList<>();
    private PadeleurFactory padeleurFactory;

    public Padeleur nuovoPadeleur;  
    public Prenotazione nuovaPrenotazione;
    public Magazzino nuovoMagazzino;
    
    //Pattern GoF Observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(int idPrenotazione, List<String> emails) {
        for (Observer observer : observers) {
            observer.update(idPrenotazione, emails);
        }
    }
    
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public GiocoPadel() { 
        this.elencoPadeleur=new HashMap<String, Padeleur>();
        this.elencoPrenotazioni = new HashMap<Integer, Prenotazione>();
        this.elencoCampiPadel = new HashMap<Integer, CampoPadel>();
        this.elencoMagazzino = new HashMap<String, Magazzino>();

        loadElencoPadeleur();
        loadCampiPadel();
        loadElencoPrenotazioni();
        loadElencoMagazzino();
        
        Magazzino magazzino=new Magazzino("0",0,0);
        magazzino.loadquantitaTotali();
       
        
        this.padeleurFactory = new DefaultPadeleurFactory();
    }

    public static GiocoPadel getInstance() { //Pattern GoF Singleton
            if(giocopadel==null) giocopadel=new GiocoPadel();
            return giocopadel;
    }

    /*Creazione dell'elencoPadeleur*/
    public void loadElencoPadeleur() {
        try {
            BufferedReader bfElencoPadeleur = new BufferedReader(new FileReader("padeleur.txt"));
            String linea;

            while ((linea = bfElencoPadeleur.readLine()) != null) {
                String[] datiPadeleur = linea.split(",");

                // Analizza la riga e crea un nuovo oggetto Padeleur
                if(datiPadeleur.length>=5){
                    String nome = datiPadeleur[0];
                    String cognome = datiPadeleur[1];
                    String codiceFiscale = datiPadeleur[2];
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataDiNascita = sdf.parse(datiPadeleur[3]);
                    String email = datiPadeleur[4];

                    Padeleur padeleur = new Padeleur(nome, cognome, codiceFiscale, dataDiNascita, email);


                        // Aggiungi il giocatore all'elencoPadeleur
                    elencoPadeleur.put(codiceFiscale, padeleur);
                }

            }
            bfElencoPadeleur.close();
            System.out.println("Caricamento dei padeleur completato con successo");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    /*Creazione dell'elencoCampiPadel*/ 
    public void loadCampiPadel() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("campi.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int idCampo = Integer.parseInt(data[0]);
                float prezzo = Float.parseFloat(data[1]);
                elencoCampiPadel.put(idCampo, new CampoPadel(idCampo, prezzo));
            }

            reader.close();
            System.out.println("Caricamento dei campi di Padel completato con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento dei campi di Padel: " + e.getMessage());
        }
    }
    
    /*Creazione dell'elencoPrenotazioni*/
    public void loadElencoPrenotazioni() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("prenotazioni.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int idPrenotazione = Integer.parseInt(data[0]);
                boolean attrezzaturaRichiesta = Boolean.parseBoolean(data[1]);
                Date giornoPrenotazione = new SimpleDateFormat("dd/MM/yyyy").parse(data[2]);
                LocalTime tempoInizio = LocalTime.parse(data[3], DateTimeFormatter.ofPattern("HH:mm"));
                Time oraInizio=Time.valueOf(tempoInizio);
                LocalTime tempoFine = LocalTime.parse(data[4], DateTimeFormatter.ofPattern("HH:mm"));
                Time oraFine=Time.valueOf(tempoFine);
                float costoPrenotazione = Float.parseFloat(data[5]);
                int idCampo = Integer.parseInt(data[6]);

                Padeleur organizzatore = getPadeleurByEmail(data[7]);
                Padeleur partecipante2 = getPadeleurByEmail(data[8]);
                Padeleur partecipante3 = getPadeleurByEmail(data[9]);
                Padeleur partecipante4 = getPadeleurByEmail(data[10]);

                Prenotazione prenotazione = new Prenotazione(idPrenotazione ,attrezzaturaRichiesta, giornoPrenotazione, oraInizio, oraFine, costoPrenotazione);
                prenotazione.setOrganizzatore(organizzatore);
                prenotazione.setPartecipante2(partecipante2);
                prenotazione.setPartecipante3(partecipante3);
                prenotazione.setPartecipante4(partecipante4);
                CampoPadel campoPadel = getCampoPadelById(idCampo);
                prenotazione.setCampoPadel(campoPadel);

                elencoPrenotazioni.put(idPrenotazione, prenotazione);
            }

            reader.close();
            System.out.println("Caricamento delle prenotazioni completato con successo.");
        } catch (IOException | ParseException e) {
                System.out.println("Errore durante il caricamento delle prenotazioni: " + e.getMessage());
        }
    }
    
    /*Creazione dell'elenco magazzino*/
    public void loadElencoMagazzino() {
    try {
        BufferedReader reader = new BufferedReader(new FileReader("magazzini.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String idGiorno = data[0];
            int racchetteRichieste = Integer.parseInt(data[1]);
            int pallineRichieste = Integer.parseInt(data[2]);

            Magazzino magazzino = new Magazzino(idGiorno, racchetteRichieste, pallineRichieste);
            elencoMagazzino.put(idGiorno, magazzino);
        }

        reader.close();
        System.out.println("Caricamento dell'elenco del magazzino completato con successo.");
    } catch (IOException e) {
        System.out.println("Errore durante il caricamento dell'elenco del magazzino: " + e.getMessage());
    }
}

    public boolean verificaEsistenzaPadeleur(String email) { //UC1
        for (Padeleur padeleur : elencoPadeleur.values()) {
            if (padeleur.getEmail().equals(email)) {
                return true; // L'utente esiste già
            }
        }
        return false; // L'utente non esiste
    }

    public void inserisciNuovoPadeleur(String nome, String cognome, String codiceFiscale, Date dataDiNascita, String email) { //UC1
        //Pattern GoF Factory Method
        nuovoPadeleur = padeleurFactory.createPadeleur(nome, cognome, codiceFiscale, dataDiNascita, email); 
    }

    public void confermaNuovoPadeleur(){ //UC1
        elencoPadeleur.put(nuovoPadeleur.getCodiceFiscale(), nuovoPadeleur);
        salvaPadeleurSuFile();
        System.out.println("Nuovo Padeleur inserito con successo.");
    }
    
    public Padeleur getPadeleurByEmail(String email) {
        for (Padeleur padeleur : elencoPadeleur.values()) {
            if (padeleur.getEmail().equals(email)) {
                return padeleur;
            }
        }
        return null; // Se non viene trovato nessun Padeleur con l'email corrispondente
    }

    public void salvaPadeleurSuFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("padeleur.txt", true));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String riga = nuovoPadeleur.getNome() + "," + nuovoPadeleur.getCognome() + "," + nuovoPadeleur.getCodiceFiscale() + ","
                    + sdf.format(nuovoPadeleur.getDataDiNascita()) + "," + nuovoPadeleur.getEmail();
            writer.write(riga);
            writer.newLine();
            writer.close();
            System.out.println("Padeleur salvato su file con successo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*metodo getElencoPadeleur() per ottenere la mappa elencoPadeleur dal di fuori della classe. */
    public Map<String, Padeleur> getElencoPadeleur() {
        return elencoPadeleur;
    }
    
    public void inserisciNuovaPrenotazione(int idPrenotazione, Date giornoPrenotazione, Time oraInizio,Time oraFine,String email,String email2,String email3, String email4, boolean attrezzaturaRichiesta, int idCampo, int numeroRacchette, int numeroPalline) { //UC2
        int annullamento=0;
        Padeleur organizzatore = getPadeleurByEmail(email);
        Padeleur partecipante2 = getPadeleurByEmail(email2);
        Padeleur partecipante3 = getPadeleurByEmail(email3);
        Padeleur partecipante4 = getPadeleurByEmail(email4);

        CampoPadel campoPadel = getCampoPadelById(idCampo);

        nuovaPrenotazione = new Prenotazione(idPrenotazione, attrezzaturaRichiesta, giornoPrenotazione, oraInizio, oraFine, 0);
        nuovaPrenotazione.setOrganizzatore(organizzatore);
        nuovaPrenotazione.setPartecipante2(partecipante2);
        nuovaPrenotazione.setPartecipante3(partecipante3);
        nuovaPrenotazione.setPartecipante4(partecipante4);
        nuovaPrenotazione.setCampoPadel(campoPadel);
        
        //Pattern Gof Strategy
        nuovaPrenotazione.setPagamentoStrategy(new PagamentoStandard());
        Magazzino magazzino=new Magazzino("0",0,0);
        
        if (attrezzaturaRichiesta) {
            if(inserimentoAttrezzatura(numeroRacchette, numeroPalline, magazzino)){
                nuovaPrenotazione.setAttrezzaturaStrategy(new PagamentoAttrezzatura());               
            }
            else {
                annullamento=1;
            }
        } else {
            nuovaPrenotazione.setAttrezzaturaStrategy(null); // Nessuna attrezzatura richiesta
            RichiestaAttrezzatura richiestaAttrezzatura = new RichiestaAttrezzatura(0, 0);
            nuovaPrenotazione.setRichiestaAttrezzatura(richiestaAttrezzatura);
        }
        
        
        float costoPrenotazione = nuovaPrenotazione.calcolaImportoTotale(magazzino);
        
        costoPrenotazione=costoPrenotazione-sconto; //Modifica per UC3
       
        nuovaPrenotazione.setCostoPrenotazione(costoPrenotazione);
         if(annullamento==1){
            nuovaPrenotazione.setCostoPrenotazione(-1.0f);
        }
    }
    
    public String creazioneidGiorno(Prenotazione prenotazione){
        Date giornoPrenotazione=prenotazione.getGiornoPrenotazione();
        Time oraFine=prenotazione.getOraFine();
        Time oraInizio=prenotazione.getOraInizio();
        
        int campoPadel=prenotazione.getCampoPadel().getIdCampo();
                             
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String giornoPrenotazioneString = dateFormat.format(giornoPrenotazione);
        String oraFineString = timeFormat.format(oraFine);
        String oraInizioString = timeFormat.format(oraInizio);
        String campoPadelString = Integer.toString(campoPadel);

        return giornoPrenotazioneString + oraInizioString + oraFineString + campoPadelString; 
    }
    
    public boolean inserimentoAttrezzatura(int numeroRacchette, int numeroPalline, Magazzino mag) {  //UC2
        RichiestaAttrezzatura richiestaAttrezzatura = new RichiestaAttrezzatura(numeroRacchette, numeroPalline);
                
        String idGiorno=creazioneidGiorno(nuovaPrenotazione);          
                
        int racchetteTotali=mag.getracchetteTotali();
        int pallineTotali=mag.getpallineTotali();
                              
              
                
        for(Magazzino magazzino : elencoMagazzino.values()){
            if(idGiorno.substring(0,10).compareTo(magazzino.getidGiorno().substring(0, 10))==0 &&
                ((idGiorno.substring(10,15).compareTo(magazzino.getidGiorno().substring(10,15))>=0 && idGiorno.substring(10,15).compareTo(magazzino.getidGiorno().substring(15,20))<=0) ||
                (idGiorno.substring(15,20).compareTo(magazzino.getidGiorno().substring(10,15))>=0 && idGiorno.substring(15,20).compareTo(magazzino.getidGiorno().substring(15,20))<=0))    
                ){                      
                //Ci sono richieste di attrezzatura nella fascia oraria richiesta, calcolo racchette e palline totali disponibili
                racchetteTotali=racchetteTotali-magazzino.getracchetteRichieste();
                pallineTotali=pallineTotali-magazzino.getpallineRichieste();
                } 
            }          
                
        if (racchetteTotali >= numeroRacchette && pallineTotali>= numeroPalline) {
                System.out.println("Attrezzature disponibili. ");
                nuovaPrenotazione.setRichiestaAttrezzatura(richiestaAttrezzatura);
                    
                nuovoMagazzino=new Magazzino(idGiorno, numeroRacchette, numeroPalline);
                return true;
        } 
        
        System.out.println("Attrezzature non disponibili. ");
        System.out.println("Se vuoi continuare con la prenotazione digita 1, altrimenti digita qualsiasi altro tasto"); //Estensione 5a
        int scelta=0;
        Scanner tastiera=new Scanner(System.in);
        scelta=tastiera.nextInt();
        if(scelta==1){
            System.out.println("La prenotazione continua senza aggiunta di attrezzatura");
            richiestaAttrezzatura = new RichiestaAttrezzatura(0, 0);
            nuovaPrenotazione.setRichiestaAttrezzatura(richiestaAttrezzatura);
            nuovaPrenotazione.setAttrezzaturaRichiesta(false);
            return true;
        }
                   
        System.out.println("Annullamento prenotazione");
        return false;               
    }
    
    public CampoPadel getCampoPadelById(int idCampo) {
        return elencoCampiPadel.get(idCampo);
    }

    public boolean ControlloPrenotazione(int idCampo, Date giornoPrenotazione, Time oraInizio, Time oraFine) { 
         long durataPrenotazioneMillis = oraFine.getTime() - oraInizio.getTime();
         long dueOreMillis = 2 * 60 * 60 * 1000; // Conversione due ore in millisecondi

        if (durataPrenotazioneMillis > dueOreMillis) {
            System.out.println("Un campo può essere prenotato per un massimo di due ore consecutive."); 
            return false; // Regola di dominio R3: Durata della prenotazione supera le due ore consentite
        }
        
        for (Prenotazione prenotazione : elencoPrenotazioni.values()) {
            if (prenotazione.getCampoPadel().getIdCampo() == idCampo && prenotazione.getGiornoPrenotazione().equals(giornoPrenotazione) && !(oraFine.compareTo(prenotazione.getOraInizio()) <= 0 || oraInizio.compareTo(prenotazione.getOraFine()) >= 0)) {
                System.out.println("Il campo nel giorno e nella fascia inserita è già occupato.");
                return false; // Il campo è occupato
            }
        }
            return true; // Il campo è disponibile
    }
    
    public void confermaNuovaPrenotazione() { //UC2
        if(nuovaPrenotazione.getCostoPrenotazione()==-1.0f){
            System.out.println("Prenotazione annullata con successo");
        }
        else{
            if(sconto!=0){ //Modifica per UC3
                System.out.println("Rimodulazione costo prenotazione");
                if(nuovaPrenotazione.getCostoPrenotazione()<0){
                    System.out.println("Avrai un accredito monetario di: "+ -nuovaPrenotazione.getCostoPrenotazione() + " euro");
                }
                else
                    System.out.println("Devi versare una cifra aggiuntiva di: "+nuovaPrenotazione.getCostoPrenotazione() + " euro");
                sconto=0;
            }
            else
                System.out.println("Il costo della prenotazione ammonta a: "+ nuovaPrenotazione.getCostoPrenotazione() + " euro");
            
            if(nuovaPrenotazione.isAttrezzaturaRichiesta()){
                    elencoMagazzino.put(nuovoMagazzino.getidGiorno(), nuovoMagazzino);
                    salvaMagazzinoSuFile(nuovoMagazzino);
           }
           //Aggiunta prenotazione al file
           int creazioneIndice=0;
           for(Prenotazione prenotazione: elencoPrenotazioni.values()){
               creazioneIndice=prenotazione.getIdPrenotazione()+1;
           }
           nuovaPrenotazione.setIdPrenotazione(creazioneIndice);
           elencoPrenotazioni.put(nuovaPrenotazione.getIdPrenotazione(), nuovaPrenotazione);
           salvaPrenotazioneSuFile(nuovaPrenotazione);
          
           List<String> emails = new ArrayList<>();
           emails.add(nuovaPrenotazione.getOrganizzatore().getEmail());
           emails.add(nuovaPrenotazione.getPartecipante2().getEmail());
           emails.add(nuovaPrenotazione.getPartecipante3().getEmail());
           emails.add(nuovaPrenotazione.getPartecipante4().getEmail());
           notifyObservers(nuovaPrenotazione.getIdPrenotazione(), emails);
        }
    }
    
    public void salvaMagazzinoSuFile(Magazzino magazzino) {
    try {
        BufferedWriter writer = new BufferedWriter(new FileWriter("magazzini.txt", true));
        String riga = magazzino.getidGiorno() + "," + magazzino.getracchetteRichieste() + "," + magazzino.getpallineRichieste();
        writer.write(riga);
        writer.newLine();
        writer.close();
        System.out.println("Magazzino salvato su file con successo.");
    } catch (IOException e) {
        System.out.println("Errore durante il salvataggio del magazzino su file: " + e.getMessage());
    }
}

    public void salvaPrenotazioneSuFile(Prenotazione prenotazione) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("prenotazioni.txt", true));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String riga = prenotazione.getIdPrenotazione() + "," + prenotazione.isAttrezzaturaRichiesta() + "," + sdf.format(prenotazione.getGiornoPrenotazione()) + "," +
                    timeFormat.format(prenotazione.getOraInizio()) + "," + timeFormat.format(prenotazione.getOraFine()) + "," + prenotazione.getCostoPrenotazione() + "," + prenotazione.getCampoPadel().getIdCampo() + "," +
                    prenotazione.getOrganizzatore().getEmail() + "," + prenotazione.getPartecipante2().getEmail() + "," + prenotazione.getPartecipante3().getEmail() + "," + prenotazione.getPartecipante4().getEmail();
            writer.write(riga);
            writer.newLine();
            writer.close();
            System.out.println("Prenotazione salvata su file con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio della prenotazione su file: " + e.getMessage());
        }
    }  

    
    public boolean modificaPrenotazione() { //UC3
        System.out.println("Inserisci ID della prenotazione da modificare");
        Scanner tastiera=new Scanner(System.in);
        String text=tastiera.next();
        int idPrenotazioneDaModificare=Integer.valueOf(text);
        
        Prenotazione prenotazioneDaModificare = elencoPrenotazioni.get(idPrenotazioneDaModificare);
        
        if(prenotazioneDaModificare != null){
            sconto=prenotazioneDaModificare.getCostoPrenotazione();
            System.out.println("Per la prenotazione avevi speso: "+sconto + "euro che ti verranno decurtati nella modifica prenotazione");
            elencoPrenotazioni.remove(idPrenotazioneDaModificare);
            rimuoviPrenotazioneDaFile(idPrenotazioneDaModificare);
            if(prenotazioneDaModificare.isAttrezzaturaRichiesta()){
                String idGiorno=creazioneidGiorno(prenotazioneDaModificare);
                elencoMagazzino.remove(idGiorno);
                rimuoviMagazzinoDaFile(idGiorno); 
            }
            return true;
        }
        return false;
    }

    public boolean rimuoviPrenotazione() { //UC3
        System.out.println("Inserisci ID della prenotazione da rimuovere");
        Scanner tastiera=new Scanner(System.in);
        int idPrenotazioneDaRimuovere=tastiera.nextInt();
        
        Prenotazione prenotazioneDaRimuovere = elencoPrenotazioni.get(idPrenotazioneDaRimuovere);
        if(prenotazioneDaRimuovere != null){
            elencoPrenotazioni.remove(idPrenotazioneDaRimuovere);
            rimuoviPrenotazioneDaFile(idPrenotazioneDaRimuovere);
            if(prenotazioneDaRimuovere.isAttrezzaturaRichiesta()){
                String idGiorno=creazioneidGiorno(prenotazioneDaRimuovere);
                elencoMagazzino.remove(idGiorno);
                rimuoviMagazzinoDaFile(idGiorno); 
            }
            
            Date oggi = new Date();
            long differenzaTempo = prenotazioneDaRimuovere.getGiornoPrenotazione().getTime()-oggi.getTime();
 
            long dueGiorniMillis = 48 * 60 * 60 * 1000; //48 ore in millisecondi
            
            if(prenotazioneDaRimuovere.getCostoPrenotazione()>0){
                if (differenzaTempo > dueGiorniMillis) {
                    System.out.println("Hai diritto al rimborso completo pari a " +prenotazioneDaRimuovere.getCostoPrenotazione()+ "euro.");
                } else {
                // Rimborso del 70% per la regola di dominio R4
                float rimborso = prenotazioneDaRimuovere.getCostoPrenotazione() * 0.7f;
                System.out.println("Hai diritto a un rimborso del 70% pari a " + rimborso + " euro.");
                }
            }
            else{
                System.out.println("Hai una prenotazione in cui hai già avuto un riaccredito poiché hai modificato la prenotazione avendo diritto ad uno sconto");
            }
            return true;
        }
        return false;
    }
    
    public void rimuoviPrenotazioneDaFile(int idPrenotazioneDaRimuovere) {
        List<String> righeDaMantenere = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("prenotazioni.txt"));
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(",");
                int idPrenotazione = Integer.parseInt(campi[0]);
                if (idPrenotazione != idPrenotazioneDaRimuovere) {
                    righeDaMantenere.add(riga);
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("prenotazioni.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della prenotazione dal file: " + e.getMessage());
        
        }
    }
    
    public void rimuoviMagazzinoDaFile(String idGiornoDaRimuovere) {
        List<String> righeDaMantenere = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("magazzini.txt"));
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(",");
                String idGiorno = campi[0];
                if (!idGiorno.equals(idGiornoDaRimuovere)) {
                    righeDaMantenere.add(riga);
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("magazzini.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della prenotazione dal file: " + e.getMessage());
        
        }
    }

    public void conteggioPartite() { //UC5
        Scanner tastiera=new Scanner(System.in);
        int conteggio=0;
        System.out.println("Inserisci identificativo del campo di Padel di cui vuoi conoscere quante partite sono state effettuate"); 
        int idCampo=tastiera.nextInt();
        if(idCampo>0 && idCampo<4){
            for(Prenotazione prenotazione: elencoPrenotazioni.values()){
                if(prenotazione.getCampoPadel().getIdCampo()==idCampo){
                    conteggio++;
                }
            }
            System.out.println("Il numero di partite effettuate nel campo " + idCampo + " è: " + conteggio);
        }
        else
            System.out.println("Inserimento errato del campo");
    }
    
    public void modificaPrezzi(){ //UC6
        Scanner tastiera = new Scanner(System.in);
        System.out.println("Di quale campo di padel vuoi modificare il prezzo?");
        int idCampo=tastiera.nextInt();
        if(idCampo>0 && idCampo<4){
            for(CampoPadel campoPadel: elencoCampiPadel.values()){
                if(campoPadel.getIdCampo()==idCampo){
                    System.out.println("Nuovo prezzo: ");
                    float nuovoPrezzo=tastiera.nextFloat();
                    campoPadel.setPrezzo(nuovoPrezzo);
                    modificaCostoFile(idCampo, nuovoPrezzo);
                }
            }
        }
        else 
            System.out.println("Inserimento errato del campo");
    }
    
    public void modificaCostoFile(int idCampo, float nuovoPrezzo){
        List<String> righeDaMantenere = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("campi.txt"));
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(",");
                float idCampoDaModificare = Float.parseFloat(campi[0]);
                if (idCampoDaModificare!=idCampo) {
                    righeDaMantenere.add(riga);
                }
                else{
                    String Nuovariga=Integer.toString(idCampo)+","+Float.toString(nuovoPrezzo);
                    righeDaMantenere.add(Nuovariga);
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("campi.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della prenotazione dal file: " + e.getMessage());
        
        }
    }

    public void rimuoviPadeleur(String email) {
         List<String> righeDaMantenere = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("padeleur.txt"));
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(",");
                String idEmail = campi[4];
                if (!idEmail.equals(email)) {
                    righeDaMantenere.add(riga);
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("padeleur.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della prenotazione dal file: " + e.getMessage());
        
        }
    }
    
    public void modificaMagazzino() { //UC7
        Scanner tastiera=new Scanner(System.in);
        System.out.println("Cosa vuoi modificare: 1.Quantità totali racchette 2.Quanitità totali palline");
        int scelta=tastiera.nextInt();
        System.out.println("Quante quantità vuoi aggiungere? ");
        int aggiunta=tastiera.nextInt();
        Magazzino prendimagazzino=new Magazzino("0",0,0);
        int racchetteTotali=prendimagazzino.getracchetteTotali();
        int pallineTotali=prendimagazzino.getpallineTotali();
        switch(scelta){
            case 1:
                for(Magazzino magazzino : elencoMagazzino.values()){
                    magazzino.setracchetteTotali(aggiunta);
                    racchetteTotali=magazzino.getracchetteTotali();
                }
                break;
      
            case 2:
                for(Magazzino magazzino : elencoMagazzino.values()){
                    magazzino.setpallineTotali(aggiunta);
                    pallineTotali=magazzino.getpallineTotali();
                }
                break;
            default:
                System.out.println("Scelta non valida");
                Magazzino magazzino=new Magazzino("0",0,0);
                racchetteTotali=magazzino.getracchetteTotali();
                pallineTotali=magazzino.getpallineTotali();
        }
        
         List<String> riganuova = new ArrayList<>();
         try {
             
            BufferedReader reader = new BufferedReader(new FileReader("quantitaTotali.txt"));
            
            String Nuovariga=Integer.toString(racchetteTotali)+","+Integer.toString(pallineTotali);
            riganuova.add(Nuovariga);
            
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("quantitaTotali.txt"));
            for (String rigaDaScrivere : riganuova) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della prenotazione dal file: " + e.getMessage());
        
        }
       
    }
    
   
    
    //Funzioni necessarie per testing
    public Map<Integer, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
    }

    public Map<String, Magazzino> getElencoMagazzino() {
        return elencoMagazzino;
    }
    
    public Map<Integer, CampoPadel> getElencoCampiPadel() {
        return elencoCampiPadel;
    }

    

    
}