/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;



public class RichiestaAttrezzatura {
    private int numeroRacchette;
    private int numeroPalline;
  
  

    public RichiestaAttrezzatura(int numeroRacchette, int numeroPalline) {
        this.numeroRacchette = numeroRacchette;
        this.numeroPalline = numeroPalline;    
    }

    public int getNumeroRacchette() {
        return numeroRacchette;
    }

    public int getNumeroPalline() {
        return numeroPalline;
    }

  
}

