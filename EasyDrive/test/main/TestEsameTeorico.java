package main;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestEsameTeorico {
	EsameTeorico e;
	
	@Before
	public void initTest() {
		e = new EsameTeorico(LocalDate.of(2023, 04, 15), LocalTime.of(19, 59));
	}
	
	@Test
	public void testIsDisponile() {
		//L'esame teorico non è disponibile poichè ha una data antecedente al momento della chiamata della funzione
		assertFalse(e.isDisponibile());
	}
	
	@Test 
	public void testPrenotaCliente() {
		Cliente c = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		//Il cliente non potrà prenotarsi poichè non ha una frequenza lezioni >= 70%
		System.out.println(c.toString());
		e.prenotaCliente(c);
		assertTrue(e.getElencoPrenotatiEsameTeorico().isEmpty()); //L'elenco è vuoto
		//Il cliente potrà  prenotarsi una volta superata la frequenza desiderata
		Argomento a1 = new Argomento("Segnali di pericolo");
		c.incrementaFrequenzaLezioni(a1, 1);
		System.out.println(c.toString());
		e.prenotaCliente(c);
		assertNotNull(e.getElencoPrenotatiEsameTeorico());
	}
	
	@Test
	public void testPromuoviCliente() {
		Cliente c1 = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		Cliente c2 = new Cliente("MV202051", "Marco", "Verdi", Date.valueOf("2001-1-1"), "095648968", "Alessio.Rossi@gmail.com", 
				"via Rossi 26");
		//Il cliente potrà  prenotarsi una volta superata la frequenza desiderata
		Argomento a1 = new Argomento("Segnali di pericolo");
		c1.incrementaFrequenzaLezioni(a1, 1);
		e.prenotaCliente(c1);
		c2.incrementaFrequenzaLezioni(a1, 1);
		e.prenotaCliente(c2);
		e.promuoviCliente(c1.getCodiceFiscale());
		//Solo il cliente c1 avrà l'attributo "foglioRosa" pari a "true"
		assertTrue(c1.getFoglioRosa());
		assertFalse(c2.getFoglioRosa());
		for (Map.Entry<String, Cliente> entry : e.getElencoPrenotatiEsameTeorico().entrySet()) {
            System.out.println(entry.toString());
            }
	}
	
	@Test
	public void testConfermaEsiti() {
		Cliente c1 = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		Cliente c2 = new Cliente("MV202051", "Marco", "Verdi", Date.valueOf("2001-1-1"), "095648968", "Alessio.Rossi@gmail.com", 
				"via Rossi 26");
		Cliente c3 = new Cliente("GB202051", "Giovanni", "Boccaccio", Date.valueOf("1313-1-1"), "095687456", "Giovanni.Boccaccio@gmail.com", 
				"via Rossi 24");
		Cliente c4 = new Cliente("MB202051", "Mattia", "Blu", Date.valueOf("2005-1-1"), "095145793", "Mattia.Blu@gmail.com", 
				"via Bianchi 36");
		//Il cliente potrà  prenotarsi una volta superata la frequenza desiderata
		Argomento a1 = new Argomento("Segnali di pericolo");
		c1.incrementaFrequenzaLezioni(a1, 1);
		c2.incrementaFrequenzaLezioni(a1, 1);
		c3.incrementaFrequenzaLezioni(a1, 1);
		c4.incrementaFrequenzaLezioni(a1, 1);
		e.prenotaCliente(c1);
		e.prenotaCliente(c2);
		e.prenotaCliente(c3);
		e.prenotaCliente(c4);
		e.promuoviCliente(c1.getCodiceFiscale());
		e.promuoviCliente(c3.getCodiceFiscale());
		//Solo i clienti c1 e c3 avranno l'attributo "foglioRosa" pari a "true"
		e.confermaEsiti();
		//I clienti con l'attributo "foglioRosa" pari a "false" avranno incrementato il loro attributo "numeroBocciature"
		assert c1.getNumeroBocciature() == 0;
		assert c3.getNumeroBocciature() == 0;
		assert c2.getNumeroBocciature() > 0;
		assert c4.getNumeroBocciature() > 0;
		for (Map.Entry<String, Cliente> entry : e.getElencoPrenotatiEsameTeorico().entrySet()) {
            System.out.println(entry.toString());
            }
	}
}
