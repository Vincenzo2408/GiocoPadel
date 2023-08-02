/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.util.List;

/**
 *
 * @author gerar
 */
public interface Observer {
    void update(int idPrenotazione, List<String> emails);
}
