/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import com.mycompany.giocopadel.app.domain.GiocoPadel;
import com.mycompany.giocopadel.app.domain.Padeleur;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws ParseException{
        GiocoPadel giocopadel=GiocoPadel.getInstance();
        Menu(giocopadel);
    }
    
    public static void Menu(GiocoPadel giocopadel){
        Scanner tastiera=new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int scelta=0;
        Map<String, Padeleur> elencoPadeleur;
        
        System.out.println("Benvenuto in GiocoPadel");
        do{
            System.out.println("Seleziona cosa si desidera fare: \n1. Inserire nuovo padeleur \n2. Visualizzazione padeleur");
                try{
                    scelta=tastiera.nextInt();}
                catch(NumberFormatException n){
                    scelta = 0;}
                catch (Exception e){
                    System.out.println(e);
                }
            switch(scelta){
                case 0: 
                    System.out.println("Uscita");
                    break;
                case 1: 
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
                        String codFiscale=tastiera.next();
                        System.out.println("Inserisci data di nascita dd/MM/yyyy");
                        String dataInput=tastiera.next();
                    
                        try{
                            Date dataDiNascita = sdf.parse(dataInput);
                            giocopadel.inserisciNuovoPadeleur(nome, cognome, codFiscale, dataDiNascita, email);
                            giocopadel.confermaNuovoPadeleur();}
                        catch (ParseException e){
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
        }while(scelta!=0);
    }
}
