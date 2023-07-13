import com.mycompany.giocopadel.app.domain.GiocoPadel;
import com.mycompany.giocopadel.app.domain.Padeleur;
import java.io.BufferedReader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

 

public class TestGiocoPadel {

    private GiocoPadel giocopadel;
    static SimpleDateFormat sdf;

    @BeforeEach
    public void setup() throws ParseException {
        giocopadel = GiocoPadel.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        giocopadel.inserisciNuovoPadeleur("Mario", "Rossi", "ABC123", new Date(), "mario@example.com");
    }

    @Test
    public void testInserisciNuovoPadeleur() {
        Map<String, Padeleur> elencoPadeleur = giocopadel.getElencoPadeleur();
        Assertions.assertEquals(1, elencoPadeleur.size());

        Padeleur padeleur = elencoPadeleur.get("ABC123");
        Assertions.assertNotNull(padeleur);
        Assertions.assertEquals("Mario", padeleur.getNome());
        Assertions.assertEquals("Rossi", padeleur.getCognome());
        Assertions.assertEquals("mario@example.com", padeleur.getEmail());
    }

    @Test
    public void testVerificaEsistenzaPadeleur() {
        Assertions.assertTrue(giocopadel.verificaEsistenzaPadeleur("mario@example.com"));
        Assertions.assertFalse(giocopadel.verificaEsistenzaPadeleur("nonEsiste@example.com"));
    }
    /*
    @Test
    public void testSalvaPadeleurSuFile() throws IOException, ParseException {
        String nome = "Francesco";
        String cognome = "Meli";
        String codiceFiscale = "MLEFN";
        Date dataDiNascita = sdf.parse("21/06/2000");
        String email = "cicciomeli@example.com";

        // Inseriamo un nuovo padeleur
        giocoPadel.inserisciNuovoPadeleur(nome, cognome, codiceFiscale, dataDiNascita, email);

        // Eseguiamo il metodo confermaNuovoPadeleur per salvare il padeleur su file
        giocoPadel.confermaNuovoPadeleur();

        // Verifichiamo se il padeleur è stato correttamente salvato sul file
        Map<String, Padeleur> elencoPadeleur = giocoPadel.getElencoPadeleur();
        Assertions.assertTrue(elencoPadeleur.containsKey(codiceFiscale));

        // Verifichiamo anche il file "padeleur.txt" per assicurarci che contenga i dati del padeleur
        try (BufferedReader reader = new BufferedReader(new FileReader("padeleur.txt"))) {
            String riga;
            String rigaAspettata = nome + "," + cognome + "," + codiceFiscale + "," + sdf.format(dataDiNascita) + "," + email;
            while ((riga = reader.readLine()) != null) {
                if (riga.equals(rigaAspettata)) {
                    return; // La riga corretta è stata trovata nel file
                }
            }
        }

        Assertions.fail("Il padeleur non è stato correttamente salvato sul file.");
    }*/
}
