package main;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

public class TestGuida {
	Guida g;
	
	@Before
	public void initTest() {
		g = new Guida(LocalDate.of(2023, 04, 15), LocalTime.of(19, 59));
	}
	
	@Test
	public void testIsDisponile() {
		//La guida non è disponibile poichè ha una data antecedente al momento della chiamata della funzione
		assertFalse(g.isDisponibile());
	}
	
	@Test 
	public void testPrenotaCliente() {
		Cliente c = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		//Il cliente non potrà prenotarsi poichè non ha ancora superato l'esame teorico (foglioRosa == false)
		System.out.println(c.toString());
		g.prenotaCliente(c);
		assertTrue(g.getElencoPrenotatiAttività().isEmpty()); //L'elenco è vuoto
		//Il cliente potrà  prenotarsi una volta superato l'esame teorico (foglioRosa == true)
		c.setFoglioRosa(true);
		System.out.println(c.toString());
		g.prenotaCliente(c);
		assertNotNull(g.getElencoPrenotatiAttività());
	}
	
	@Test
	public void testIsAntecedente() {
		//La guida ha una data antecedente al momento della chiamata della funzione
		assertTrue(g.isAntecedente());
	}
}
