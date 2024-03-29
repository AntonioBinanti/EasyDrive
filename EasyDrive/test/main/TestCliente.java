package main;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class TestCliente {
	
	Cliente c;
	
	@Before
	public void initTest() {
		c = new Cliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
	}
	
	@Test
	public void testIncrementaFrequenza() {
		Argomento a1 = new Argomento("Segnali di pericolo");
		Argomento a2 = new Argomento("Segnali di divieto");
		//Se il cliente non aveva seguito in precedenza l'argomento viene incrementata la sua frequenzaLezioni
		try {
			c.incrementaFrequenzaLezioni(a1, 10);
			assertEquals(c.getFrequenzaLezioni(), 10.0, 0);
			System.out.println(c.getFrequenzaLezioni() + "%");
			c.incrementaFrequenzaLezioni(a2, 10);
			assertEquals(c.getFrequenzaLezioni(), 20.0, 0);
			System.out.println(c.getFrequenzaLezioni() + "%");
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se il cliente aveva seguito in precedenza l'argomento non viene incrementata la sua frequenzaLezioni
		try {
			c.incrementaFrequenzaLezioni(a2, 10);
			System.out.println(c.getFrequenzaLezioni() + "%");
			assertEquals(c.getFrequenzaLezioni(), 20.0, 0);
		}catch(Exception e) {
			assertEquals(e.getMessage(),"L'argomento è stato già seguito, la frequenza lezioni non è stata aggiornata");
		}
	}
	
	@Test
	public void testAggiornaFrequenzaLezioni() {
		try {
			c.aggiornaFrequenzaLezioni(1, 10);
			assertEquals(c.getFrequenzaLezioni(), 10.0, 0);
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testIncrementaNumeroBocciature() {
		try {
			System.out.println("numero bocciature iniziali: " + c.getNumeroBocciature());
			assert c.getNumeroBocciature() == 0;
			c.incrementaNumeroBocciature();
			System.out.println("numero bocciature: " + c.getNumeroBocciature());
			assert c.getNumeroBocciature() == 1;
			c.incrementaNumeroBocciature();
			System.out.println("numero bocciature: " + c.getNumeroBocciature());
			assert c.getNumeroBocciature() == 2;
			c.incrementaNumeroBocciature();
			System.out.println("numero bocciature: " + c.getNumeroBocciature());
			assert c.getNumeroBocciature() == 3;
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testAggiornaNumeroGuide() {
		try {
			System.out.println("numero guide iniziali: " + c.getNumeroGuide());
			assert c.getNumeroGuide() == 0;
			c.aggiornaNumeroGuide();
			System.out.println("numero guide: " + c.getNumeroGuide());
			assert c.getNumeroGuide() == 1;
			c.aggiornaNumeroGuide();
			System.out.println("numero guide: " + c.getNumeroGuide());
			assert c.getNumeroGuide() == 2;
			c.aggiornaNumeroGuide();
			System.out.println("numero guide: " + c.getNumeroGuide());
			assert c.getNumeroGuide() == 3;
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testIncrementaNumeroBocciatureEsameFinale() {
		try {
			System.out.println("numero bocciature iniziali: " + c.getNumeroBocciatureEsameFinale());
			assert c.getNumeroBocciatureEsameFinale() == 0;
			c.incrementaNumeroBocciatureEsameFinale();
			System.out.println("numero bocciature: " + c.getNumeroBocciatureEsameFinale());
			assert c.getNumeroBocciatureEsameFinale() == 1;
			c.incrementaNumeroBocciatureEsameFinale();
			System.out.println("numero bocciature: " + c.getNumeroBocciatureEsameFinale());
			assert c.getNumeroBocciatureEsameFinale() == 2;
			c.incrementaNumeroBocciatureEsameFinale();
			System.out.println("numero bocciature: " + c.getNumeroBocciatureEsameFinale());
			assert c.getNumeroBocciatureEsameFinale() == 3;
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
}
