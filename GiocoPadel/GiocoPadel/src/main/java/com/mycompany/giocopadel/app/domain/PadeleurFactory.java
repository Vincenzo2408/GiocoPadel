package com.mycompany.giocopadel.app.domain;

import java.util.Date;

public interface PadeleurFactory {
     Padeleur createPadeleur(String nome, String cognome, String codiceFiscale, Date dataDiNascita, String email);
}
