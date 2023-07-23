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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class GiocoPadel {
    public static GiocoPadel giocopadel;
    public Map<String, Padeleur> elencoPadeleur;
    private Map<Integer, Prenotazione> elencoPrenotazioni;
    private Map<Integer, CampoPadel> elencoCampiPadel;
    private Map<String, Magazzino> elencoMagazzino;

    public Padeleur nuovoPadeleur;  
    public Prenotazione nuovaPrenotazione;
    public Magazzino nuovoMagazzino;
    

    public GiocoPadel() throws ParseException{ //Singleton
        this.elencoPadeleur=new HashMap<String, Padeleur>();
        this.elencoPrenotazioni = new HashMap<Integer, Prenotazione>();
        this.elencoCampiPadel = new HashMap<Integer, CampoPadel>();
        this.elencoMagazzino = new HashMap<String, Magazzino>();

        loadElencoPadeleur();
        loadCampiPadel();
        loadElencoPrenotazioni();
        loadElencoMagazzino();
    }

    public static GiocoPadel getInstance() throws ParseException{
            if(giocopadel==null) giocopadel=new GiocoPadel();
            return giocopadel;
    }

    /*Creazione dell'elencoPadeleur*/
    public void loadElencoPadeleur() throws ParseException {
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

    public boolean verificaEsistenzaPadeleur(String email) { //Rif. UC1 e UC2 1.1 VerificaEmail(email): boolean 1. SD RichiestaEmail e 2.SD InserimentoDati 
        for (Padeleur padeleur : elencoPadeleur.values()) {
            if (padeleur.getEmail().equals(email)) {
                return true; // L'utente esiste già
            }
        }
        return false; // L'utente non esiste
    }

    public void inserisciNuovoPadeleur(String nome, String cognome, String codiceFiscale, Date dataDiNascita, String email) { //Rif. UC1 1.1 create(nome, cognome, codiceFiscale, dataDiNascita, email): void 2. SD InserimentoDatiAnagrafici
        nuovoPadeleur = new Padeleur(nome, cognome, codiceFiscale, dataDiNascita, email);
      
    }

    public void confermaNuovoPadeleur(){ //Rif. UC1 1.1 add(nuovoPadeleur) 3. SD ConfermaPadeleur
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
    
    public void inserisciNuovaPrenotazione(int idPrenotazione, Date giornoPrenotazione, Time oraInizio,Time oraFine,String email,String email2,String email3, String email4, boolean attrezzaturaRichiesta, int idCampo) { //Rif. UC2 createNuovaPrenotazione (...) 2. SD InserimentoDati
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
        
        double costoCampo=campoPadel.getCostoCampo(oraInizio, oraFine);
        float costoAttrezzatura=0;
        
        float costoPrenotazione=GeneraPrezzo(costoCampo, costoAttrezzatura);
        nuovaPrenotazione.setCostoPrenotazione(costoPrenotazione);
    }
    
    public void inserimentoAttrezzatura(int numeroRacchette, int numeroPalline) { //Rif. UC2 1.1 AggiuntaAttrezzatura(...):void 3.SD InserimentoAttrezzatura
        if (nuovaPrenotazione != null) {
                Magazzino mag=new Magazzino("0",0,0);
                float costoAttrezzatura=0.0f;
                RichiestaAttrezzatura richiestaAttrezzatura = new RichiestaAttrezzatura(numeroRacchette, numeroPalline);
                
                Date giornoPrenotazione=nuovaPrenotazione.getGiornoPrenotazione();
                Time oraFine=nuovaPrenotazione.getOraFine();
                Time oraInizio=nuovaPrenotazione.getOraInizio();
                             
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String giornoPrenotazioneString = dateFormat.format(giornoPrenotazione);
                String oraFineString = timeFormat.format(oraFine);
                String oraInizioString = timeFormat.format(oraInizio);

                String idGiorno = giornoPrenotazioneString + oraInizioString + oraFineString;            
                
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
                    // Calcola il costo dell'attrezzatura
                    System.out.println("Attrezzature disponibili. ");
                    costoAttrezzatura = (numeroRacchette+numeroPalline) * mag.getCostoSingoloAttrezzatura();
                } else {
                    System.out.println("Attrezzature non disponibili. ");
                    System.out.println("Se vuoi continuare con la prenotazione digita 1, altrimenti digita qualsiasi altro tasto"); //Estensione 5a
                    int scelta=0;
                    Scanner tastiera=new Scanner(System.in);
                    scelta=tastiera.nextInt();
                    if(scelta==1){
                        System.out.println("La prenotazione continua senza aggiunta di attrezzatura");
                        costoAttrezzatura=0;
                    }
                    else{
                        System.out.println("Annullamento prenotazione");
                        costoAttrezzatura=-1.0f;
                    }
                } 
                nuovaPrenotazione.setRichiestaAttrezzatura(richiestaAttrezzatura);
                                    
                float costoCampo=nuovaPrenotazione.getCampoPadel().getPrezzo();
                float costoPrenotazione=GeneraPrezzo(costoCampo, costoAttrezzatura);
                
                if(costoAttrezzatura==0){ //Serve per estensione 5a
                    nuovaPrenotazione.setAttrezzaturaRichiesta(false);
                }
                
                if(costoAttrezzatura==-1.0f){ //Serve per estensione 5a
                    System.out.println("Annullamento in corso..."); 
                    costoPrenotazione=-1.0f;
                } 
                
                nuovaPrenotazione.setCostoPrenotazione(costoPrenotazione);
                
                if(costoAttrezzatura>0){              
                    for(Magazzino magazzino: elencoMagazzino.values()){
                        if(idGiorno.compareTo(magazzino.getidGiorno())==0){
                            String lettera = "R";
                            idGiorno=idGiorno+lettera;
                            for(Magazzino magaz: elencoMagazzino.values()){
                                if(idGiorno.compareTo(magaz.getidGiorno())==0){
                                lettera = "R";
                                idGiorno=idGiorno+lettera;    
                                }
                            }
                        }
                    }    
                    nuovoMagazzino=new Magazzino(idGiorno, numeroRacchette, numeroPalline);
                }
        }
    }
    
     public int calcolaIndice(Date giorno, Time oraInizio, Time oraFine) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(giorno);
        int giornoInt = cal.get(Calendar.DAY_OF_MONTH);
        int meseInt=cal.get(Calendar.MONTH)+1;
        int annoInt=cal.get(Calendar.YEAR);
        int oraInizioInt = convertiTimeInInt(oraInizio);
        int oraFineInt = convertiTimeInInt(oraFine);
        return (annoInt + meseInt + giornoInt) * 100000000 + oraInizioInt * 10000 + oraFineInt; /* 11515001700 (115 id giorno, dalle 15:00 alle 17:00) */
    }

    private int convertiTimeInInt(Time time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return hours * 100 + minutes; 
    }
    
    public float GeneraPrezzo(double costoCampo, float costoAttrezzatura){ //Rif. UC2 4.GeneramentoCosto(costoCampo, costoTotaleAttrezzatura):float 2.SD InserimentoDati
         float costoPrenotazione = (float) (costoCampo + costoAttrezzatura);
         return costoPrenotazione;
    }
    
    public CampoPadel getCampoPadelById(int idCampo) {
        return elencoCampiPadel.get(idCampo);
    }

    public boolean ControlloPrenotazione(int idCampo, Date giornoPrenotazione, Time oraInizio, Time oraFine) { //Rif. UC2 VerificaDisponibilita(...) 2. SD InserimentoDati
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
    
    public void confermaNuovaPrenotazione() {
        if(nuovaPrenotazione.getCostoPrenotazione()==-1.0f){
            System.out.println("Prenotazione annullata con successo");
        }
        else{
            System.out.println("Il costo della prenotazione ammonta a"+ nuovaPrenotazione.getCostoPrenotazione());
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

           System.out.println("Prenotazione confermata e salvata con successo. Id prenotazione = "+nuovaPrenotazione.getIdPrenotazione());          
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
}