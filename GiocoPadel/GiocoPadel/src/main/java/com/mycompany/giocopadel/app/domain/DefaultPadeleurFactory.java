package com.mycompany.giocopadel.app.domain;

import java.util.Date;

public class DefaultPadeleurFactory implements PadeleurFactory {
    @Override
    public Padeleur createPadeleur(String nome, String cognome, String codiceFiscale, Date dataDiNascita, String email) {
        return new Padeleur(nome, cognome, codiceFiscale, dataDiNascita, email);
    }
}
