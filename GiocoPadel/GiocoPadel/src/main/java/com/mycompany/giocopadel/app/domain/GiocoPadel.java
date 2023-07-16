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
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GiocoPadel {
    public static GiocoPadel giocopadel;
    public Map<String, Padeleur> elencoPadeleur;
    private Map<Integer, Prenotazione> elencoPrenotazioni;
    private Map<Integer, CampoPadel> elencoCampiPadel;
    public Padeleur nuovoPadeleur;  
    private Prenotazione nuovaPrenotazione;

    public GiocoPadel() throws ParseException{ //Singleton
        this.elencoPadeleur=new HashMap<String, Padeleur>();
        this.elencoPrenotazioni = new HashMap<>();
        this.elencoCampiPadel = new HashMap<>();

        loadElencoPadeleur();
        loadCampiPadel();
        loadElencoPrenotazioni();
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
            Time oraInizio = Time.valueOf(data[3]);
            Time oraFine = Time.valueOf(data[4]);
            float costoPrenotazione = Float.parseFloat(data[5]);
            int idCampo = Integer.parseInt(data[6]);

            Padeleur organizzatore = getPadeleurByEmail(data[7]);
            Padeleur partecipante2 = getPadeleurByEmail(data[8]);
            Padeleur partecipante3 = getPadeleurByEmail(data[9]);
            Padeleur partecipante4 = getPadeleurByEmail(data[10]);

            Prenotazione prenotazione = new Prenotazione(idPrenotazione, attrezzaturaRichiesta, giornoPrenotazione, oraInizio, oraFine, costoPrenotazione);
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
        elencoPadeleur.put(codiceFiscale, nuovoPadeleur);
    }

    public void confermaNuovoPadeleur(){ //Rif. UC1 1.1 add(nuovoPadeleur) 3. SD ConfermaPadeleur
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
        double costoAttrezzatura=0.0f;
        
        if(attrezzaturaRichiesta){
            //Implementazione per SD 3. Inserimento Attrezzatura
        }
        
        float costoPrenotazione=GeneraPrezzo(costoCampo, costoAttrezzatura);

        elencoPrenotazioni.put(idPrenotazione, nuovaPrenotazione);   
    }
    
    public float GeneraPrezzo(double costoCampo, double costoAttrezzatura){
        float generazionePrezzo=0.0f;
        //Da implementare
        return generazionePrezzo;
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
        // Implementa qui la logica per confermare e salvare la nuova prenotazione
    }
    
   


}