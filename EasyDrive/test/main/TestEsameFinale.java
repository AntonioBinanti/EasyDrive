package main;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestEsameFinale {
	EsameFinale e;
	
	@Before
	public void initTest() {
		e = new EsameFinale(LocalDate.of(2023, 04, 15), LocalTime.of(19, 59));
	}
	
	@Test
	public void testIsDisponile() {
		try {
			//L'esame finale non è disponibile poichè ha una data antecedente al momento della chiamata della funzione
			assertFalse(e.isDisponibile());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test 
	public void testPrenotaCliente() {
		Cliente c = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		//Il cliente non potrà prenotarsi poichè non ha un numero di guida >= 15
		try {
			System.out.println(c.toString());
			e.prenotaCliente(c);
			assertTrue(e.getElencoPrenotatiAttività().isEmpty()); //L'elenco è vuoto
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Il cliente non può essere prenotato per l'esame poichè ha un numero di guide minore delle 15 richieste");
		}
		//Il cliente potrà prenotarsi una volta superato il numero richiesto di guide
		try {
			c.setFoglioRosa(true);
			c.setNumeroGuide(15);
			System.out.println(c.toString());
			e.prenotaCliente(c);
			assertNotNull(e.getElencoPrenotatiAttività());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testPromuoviCliente() {
		try {
			Cliente c1 = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			Cliente c2 = new Cliente("MV202051", "Marco", "Verdi", Date.valueOf("2001-1-1"), "095648968", "Alessio.Rossi@gmail.com", 
					"via Rossi 26");
			//Il cliente potrà  prenotarsi una volta superato il numero di guide richiesto
			c1.setFoglioRosa(true);
			c1.setNumeroGuide(15);
			c2.setFoglioRosa(true);
			c2.setNumeroGuide(15);
			e.prenotaCliente(c1);
			e.prenotaCliente(c2);
			e.promuoviCliente(c1.getCodiceFiscale());
			//Solo il cliente c1 avrà l'attributo "patente" pari a "true"
			assertTrue(c1.getPatente());
			assertFalse(c2.getPatente());
			for (Map.Entry<String, Cliente> entry : e.getElencoPrenotatiAttività().entrySet()) {
	            System.out.println(entry.toString());
	            }
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testIsAntecedente() {
		try {
			//L'esame finale ha una data antecedente al momento della chiamata della funzione
			assertTrue(e.isAntecedente());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testConfermaEsiti() {
		try {
			Cliente c1 = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			Cliente c2 = new Cliente("MV202051", "Marco", "Verdi", Date.valueOf("2001-1-1"), "095648968", "Alessio.Rossi@gmail.com", 
					"via Rossi 26");
			Cliente c3 = new Cliente("GB202051", "Giovanni", "Boccaccio", Date.valueOf("1313-1-1"), "095687456", "Giovanni.Boccaccio@gmail.com", 
					"via Rossi 24");
			Cliente c4 = new Cliente("MB202051", "Mattia", "Blu", Date.valueOf("2005-1-1"), "095145793", "Mattia.Blu@gmail.com", 
					"via Bianchi 36");
			//Il cliente potrà  prenotarsi una volta superato il numero di guide richiesto
			c1.setFoglioRosa(true);
			c1.setNumeroGuide(15);
			c2.setFoglioRosa(true);
			c2.setNumeroGuide(15);
			c3.setFoglioRosa(true);
			c3.setNumeroGuide(15);
			c4.setFoglioRosa(true);
			c4.setNumeroGuide(15);
			e.prenotaCliente(c1);
			e.prenotaCliente(c2);
			e.prenotaCliente(c3);
			e.prenotaCliente(c4);
			e.promuoviCliente(c1.getCodiceFiscale());
			e.promuoviCliente(c3.getCodiceFiscale());
			//Solo i clienti c1 e c3 avranno l'attributo "patente" pari a "true"
			e.confermaEsiti();
			//I clienti con l'attributo "patente" pari a "false" avranno incrementato il loro attributo "numeroBocciatureEsameFinale"
			assert c1.getNumeroBocciatureEsameFinale() == 0;
			assert c3.getNumeroBocciatureEsameFinale() == 0;
			assert c2.getNumeroBocciatureEsameFinale() > 0;
			assert c4.getNumeroBocciatureEsameFinale() > 0;
			for (Map.Entry<String, Cliente> entry : e.getElencoPrenotatiAttività().entrySet()) {
	            System.out.println(entry.toString());
	            }
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
}
