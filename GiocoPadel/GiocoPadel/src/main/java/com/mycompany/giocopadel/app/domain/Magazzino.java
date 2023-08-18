/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author gerar
 */


public class Magazzino {

    private String idGiorno;
    public int racchetteTotali=20;
    public int pallineTotali=20;
    public int racchetteRichieste;
    public int pallineRichieste;
    public float costoSingoloAttrezzatura=2.0f;

    public void loadquantitaTotali(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("quantitaTotali.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");               
                    racchetteTotali = Integer.parseInt(data[0]);
                    pallineTotali = Integer.parseInt(data[1]);
            }
            reader.close();
        } catch (IOException e) {
         System.out.println("Errore durante il caricamento dell'elenco del magazzino: " + e.getMessage());
        }
    }
    
    
    
    public Magazzino(String idGiorno, int racchetteRichieste, int pallineRichieste) {
        this.idGiorno=idGiorno;
        this.racchetteRichieste=racchetteRichieste;
        this.pallineRichieste=pallineRichieste;
        loadquantitaTotali();
    }  
 
    public int getpallineTotali(){
        return pallineTotali;
    }
    
    public int getracchetteTotali(){
        return racchetteTotali;
    }
    
    public int getracchetteRichieste(){
        return racchetteRichieste;
    }
    
    public int getpallineRichieste(){
        return pallineRichieste;
    }
    
    public String getidGiorno(){
        return idGiorno;
    }

    public float getCostoSingoloAttrezzatura() {
       return costoSingoloAttrezzatura; 
    }

    public void setnumeroRacchette(int numeroRacchette) {
         racchetteRichieste=racchetteRichieste+numeroRacchette; }

    public void setnumeroPalline(int numeroPalline) {
        pallineRichieste=pallineRichieste+numeroPalline; 
    }
    
    public void setracchetteTotali(int numeroRacchette){
        racchetteTotali=racchetteTotali+numeroRacchette;
    }
    
    public void setpallineTotali(int numeroPalline){
        pallineTotali=pallineTotali+numeroPalline;
    }
}

