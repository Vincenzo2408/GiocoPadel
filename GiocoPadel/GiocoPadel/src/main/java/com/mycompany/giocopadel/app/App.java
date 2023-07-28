/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import com.mycompany.giocopadel.app.domain.GiocoPadel;
import com.mycompany.giocopadel.app.domain.Padeleur;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws ParseException {
        GiocoPadel giocopadel = GiocoPadel.getInstance();
        Scanner tastiera = new Scanner(System.in);

        System.out.println("Benvenuto in GiocoPadel");

        boolean isAdmin = false;
        System.out.println("Se sei amministratore, inserisci il codice amministratore");
        String adminChoice = tastiera.nextLine().toLowerCase();
        if (adminChoice.equals("peggy")) {
            isAdmin = true;
        }

        if (isAdmin) {
            AmministratoreMenu(giocopadel, tastiera);
        } else {
            padeleurMenu(giocopadel, tastiera);
        }
    }

    public static void AmministratoreMenu(GiocoPadel giocopadel, Scanner tastiera) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int scelta = 0;
        Map<String, Padeleur> elencoPadeleur;

        do {
            System.out.println("Seleziona cosa si desidera fare: \n1. Inserire nuovo padeleur \n2. Visualizzazione padeleur \n0. Uscita");
            try {
                scelta = tastiera.nextInt();
            } catch (NumberFormatException n) {
                scelta = 0;
            } catch (Exception e) {
                System.out.println(e);
            }

            switch (scelta) {
                case 0:
                    System.out.println("Uscita");
                    break;
                case 1: //UC1
                    System.out.println("Inserisci email");
                    String email = tastiera.next();

                    if (giocopadel.verificaEsistenzaPadeleur(email)) {
                        System.out.println("L'utente esiste già.");
                    } else {
                        System.out.println("Inserisci nome");
                        String nome = tastiera.next();
                        System.out.println("Inserisci cognome");
                        String cognome = tastiera.next();
                        System.out.println("Inserisci codFiscale");
                        String codFiscale = tastiera.next();
                        System.out.println("Inserisci data di nascita dd/MM/yyyy");
                        String dataInput = tastiera.next();

                        try {
                            Date dataDiNascita = sdf.parse(dataInput);
                            giocopadel.inserisciNuovoPadeleur(nome, cognome, codFiscale, dataDiNascita, email);
                            giocopadel.confermaNuovoPadeleur();
                        } catch (ParseException e) {
                            System.out.println("Formato non valido. Riprova.");
                        }
                    }
                    break;

                case 2:
                    elencoPadeleur = giocopadel.getElencoPadeleur();
                    System.out.println("Elenco dei Padeleur:");
                    for (Padeleur padeleur : elencoPadeleur.values()) {
                        System.out.println("Codice fiscale: " + padeleur.getCodiceFiscale());
                        System.out.println("Nome: " + padeleur.getNome());
                        System.out.println("Cognome: " + padeleur.getCognome());
                        System.out.println("Data di nascita: " + sdf.format(padeleur.getDataDiNascita()));
                        System.out.println("Email: " + padeleur.getEmail());
                        System.out.println("---------------");
                    }
                    break;

                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        } while (scelta != 0);
    }

    public static void padeleurMenu(GiocoPadel giocopadel, Scanner tastiera) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int scelta = 0;
        do {
            System.out.println("Seleziona cosa si desidera fare: \n1. Inserimento e pagamento di una prenotazione \n0. Uscita");
            try {
                scelta = tastiera.nextInt();
            } catch (NumberFormatException n) {
                scelta = 0;
            } catch (Exception e) {
                System.out.println(e);
            }

            switch (scelta) {
                case 1: //UC2
                    System.out.println("Inserisci email del Padeleur:");
                    String email = tastiera.next();
                    int numeroRacchette=0;
                    int numeroPalline=0;

                    if (giocopadel.verificaEsistenzaPadeleur(email)) {
                        boolean tutteLeEmailEsistenti = true;

                        // Richiesta delle email dei partecipanti 2, 3 e 4
                        System.out.println("Inserisci email del partecipante 2:");
                        String email2 = tastiera.next();
                        System.out.println("Inserisci email del partecipante 3:");
                        String email3 = tastiera.next();
                        System.out.println("Inserisci email del partecipante 4:");
                        String email4 = tastiera.next();

                        int idPrenotazione;
                        boolean attrezzaturaRichiesta;
                        Date giornoPrenotazione;
                        Time oraInizio;
                        Time oraFine;
                        int idCampo;
                        int controlloprenotazione;

                        idPrenotazione = 0;
                        System.out.println("Richiesta attrezzatura (true/false):");
                        attrezzaturaRichiesta = tastiera.nextBoolean();
                        
                        do{
                            controlloprenotazione=0;
                            System.out.println("Inserisci giorno della prenotazione (dd/MM/yyyy):");
                            giornoPrenotazione = sdf.parse(tastiera.next());
                            System.out.println("Inserisci ora di inizio (HH:mm):");
                            String oraInizioString = tastiera.next();
                            LocalTime localTimeInizio = LocalTime.parse(oraInizioString, DateTimeFormatter.ofPattern("HH:mm"));
                            oraInizio = Time.valueOf(localTimeInizio);

                            System.out.println("Inserisci ora di fine (HH:mm):");
                            String oraFineString = tastiera.next();
                            LocalTime localTimeFine = LocalTime.parse(oraFineString, DateTimeFormatter.ofPattern("HH:mm"));
                            oraFine = Time.valueOf(localTimeFine);

                            System.out.println("Inserisci campo");
                            idCampo = tastiera.nextInt();

                            if (!giocopadel.ControlloPrenotazione(idCampo, giornoPrenotazione, oraInizio, oraFine)) {
                                //Estensione 3a e 3b
                                System.out.println("Riselezionamento:");
                                controlloprenotazione=1;
                            }
                        }while(controlloprenotazione!=0);  
                        
                        // Verifica le email dei partecipanti con un ciclo for
                        //Regola di dominio R1
                        String[] emailPartecipanti = { email2, email3, email4 };
                        for (String emailPartecipante : emailPartecipanti) {
                            if (!giocopadel.verificaEsistenzaPadeleur(emailPartecipante)) {
                                tutteLeEmailEsistenti = false;
                                //Estensione 4a
                                System.out.println("Padeleur non registrato:"+ emailPartecipante);                              
                            }
                        }

                        if (tutteLeEmailEsistenti) {
                            if(attrezzaturaRichiesta){
                               System.out.println("Inserisci il numero di racchette richieste:");
                               numeroRacchette = tastiera.nextInt();
                               System.out.println("Inserisci il numero di palline richieste:");
                               numeroPalline = tastiera.nextInt();
                               giocopadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, attrezzaturaRichiesta, idCampo,numeroRacchette,numeroPalline);}
                            else{
                               giocopadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, attrezzaturaRichiesta, idCampo,0,0);   
                            } 
                            
                            giocopadel.confermaNuovaPrenotazione();
                        } else {
                            System.out.println("Una o più email inserite non corrispondono a Padeleur registrati.");
                        }
                    } else {
                        System.out.println("Non sei registrato nel sistema");
                    }
                    break;

              
                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        } while (scelta != 0);
    }

}
