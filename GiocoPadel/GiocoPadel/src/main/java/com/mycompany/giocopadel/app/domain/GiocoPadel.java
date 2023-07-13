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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GiocoPadel {
    public static GiocoPadel giocoPadel;
    private Map<String, Padeleur> elencoPadeleur;
    private Padeleur nuovoPadeleur;  


public GiocoPadel() throws ParseException{ //Singleton
    this.elencoPadeleur=new HashMap<String, Padeleur>();
    loadElencoPadeleur();
}
        
public static GiocoPadel getInstance() throws ParseException{
        if(giocoPadel==null) giocoPadel=new GiocoPadel();
        return giocoPadel;
}

        
/*Nel codice sotto, si apre il file "padeleur.txt" utilizzando un BufferedReader 
e si leggono le righe una per volta. Ogni riga viene quindi suddivisa in base al 
carattere di separazione (ad esempio, la virgola) utilizzando il metodo split(). 
I dati estratti vengono utilizzati per creare un nuovo oggetto Padeleur, che viene 
quindi aggiunto all'elencoPadeleur utilizzando il codice fiscale come chiave.*/
        
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

/*metodo getElencoPadeleur() per ottenere la mappa elencoPadeleur dal di fuori della classe. */
public Map<String, Padeleur> getElencoPadeleur() {
    return elencoPadeleur;
}

public boolean verificaEsistenzaPadeleur(String email) {
    for (Padeleur padeleur : elencoPadeleur.values()) {
        if (padeleur.getEmail().equals(email)) {
            return true; // L'utente esiste già
        }
    }
    return false; // L'utente non esiste
}

public void inserisciNuovoPadeleur(String nome, String cognome, String codiceFiscale, Date dataDiNascita, String email) {
    nuovoPadeleur = new Padeleur(nome, cognome, codiceFiscale, dataDiNascita, email);
    elencoPadeleur.put(codiceFiscale, nuovoPadeleur);
}

public void confermaNuovoPadeleur(){
   
    salvaPadeleurSuFile();
    System.out.println("Nuovo Padeleur inserito con successo.");
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
}