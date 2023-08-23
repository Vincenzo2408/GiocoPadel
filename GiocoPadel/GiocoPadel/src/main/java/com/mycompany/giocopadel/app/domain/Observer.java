package com.mycompany.giocopadel.app.domain;

import java.util.List;

public interface Observer {
    void update(int idPrenotazione, List<String> emails);
}
