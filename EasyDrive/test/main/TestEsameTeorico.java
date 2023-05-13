package main;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

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
}
