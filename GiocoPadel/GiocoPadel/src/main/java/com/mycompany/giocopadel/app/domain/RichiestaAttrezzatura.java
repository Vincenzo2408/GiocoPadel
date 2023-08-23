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

