/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.sql.Time;

/**
 *
 * @author gerar
 */
public class CampoPadel {
    private int idCampo;
    private float prezzo;

    public CampoPadel(int idCampo, float prezzo) {
        this.idCampo = idCampo;
        this.prezzo = prezzo;
    }

    public int getIdCampo() {
        return idCampo;
    }

    public float getPrezzo() {
        return prezzo;
    }
    
    public void setPrezzo(float nuovoPrezzo){
       this.prezzo=nuovoPrezzo;
    }
    
    public double getCostoCampo(Time oraInizio, Time oraFine) {
        long durataPrenotazioneMillis = oraFine.getTime() - oraInizio.getTime();
        double durataPrenotazioneOre = durataPrenotazioneMillis / (1000.0 * 60.0 * 60.0); // Conversione da millisecondi a ore

        double prezzoCampo = durataPrenotazioneOre * prezzo;

        return prezzoCampo;
    }
}

