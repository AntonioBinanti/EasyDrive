package main;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Assert.*;

public class TestEasyDrive {
	
	static EasyDrive easyDrive;
	
	@BeforeClass
	public static void initTest() {
		easyDrive = EasyDrive.getInstance();
    }
	
	@After
	public void clearTest() {
		easyDrive.setClienteCorrente(null);
	}
	
	@Test
	public void testAddCliente() {
		easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		easyDrive.addCliente("MR272074", "Marco", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Marco.Rossi@gmail.com", 
				"via Rossi 25");
		Map<String, Cliente> l = easyDrive.getListaClienti();
		if(l.isEmpty()) {
			System.out.println("Nessun cliente in lista");
		}else {
			for(Cliente c : l.values()) {
			System.out.println(c);
			}
		}
	}
	
	@Test
	public void testRemoveCliente() {
		//Il cliente presente in listaClienti viene rimosso sè trovato
		easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		easyDrive.removeCliente("AR202051");
		assertNull(easyDrive.getCliente("AR202051"));
		//Se easyDrive non trova il cliente in listaClienti avverte l'amministratore
		easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		easyDrive.removeCliente("MR5181515");
		assertNotNull(easyDrive.getCliente("AR202051"));
	}
	
	@Test
	public void testAddLezione() {
		ArrayList<Argomento> listaArgomenti = easyDrive.getListaArgomenti();
		/*easyDrive.addLezione(Date.valueOf("2023-2-28"), Time.valueOf("20:52:00"), listaArgomenti.get(1));
		easyDrive.addLezione(Date.valueOf("2023-3-5"), Time.valueOf("13:45:00"), listaArgomenti.get(2));
		easyDrive.addLezione(Date.valueOf("2023-3-14"), Time.valueOf("18:00:00"), listaArgomenti.get(3));*/
		easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), listaArgomenti.get(1));
		easyDrive.addLezione(LocalDate.of(2023, 3, 5), LocalTime.of(13,45), listaArgomenti.get(2));
		easyDrive.addLezione(LocalDate.of(2023, 3, 14), LocalTime.of(18, 00), listaArgomenti.get(3));
		Map<String, Lezione> l = easyDrive.getElencoLezioni();
		if(l.isEmpty()) {
			System.out.println("Nessuna lezione in lista");
		}else {
			for(Lezione lez : l.values()) {
			System.out.println(lez);
			}
		}
		//assertNull(easyDrive.getLezione(Date.valueOf("2013-3-5"), Time.valueOf("13:45:00")));
		assertNull(easyDrive.getLezione(LocalDate.of(2013, 3, 5), LocalTime.of(13, 45)));
		/*Lezione l1 = easyDrive.getLezione(Date.valueOf("2013-3-5"), Time.valueOf("13:45:00"));
		System.out.println("Lezione selezionata= " + l1.toString());*/
	}
	
	@Test
	public void testRemoveLezione() {
		//La lezione presente in elencoLezioni viene rimossa sè trovata
		/*easyDrive.addLezione(Date.valueOf("2023-2-28"), Time.valueOf("20:52:00"), easyDrive.getListaArgomenti().get(0));
		easyDrive.removeLezione(Date.valueOf("2023-2-28"), Time.valueOf("20:52:00"));*/
		easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
		easyDrive.removeLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		//assertNull(easyDrive.getLezione(Date.valueOf("2023-2-28"), Time.valueOf("20:52:00")));
		assertNull(easyDrive.getLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)));
		//Se easyDrive non trova la lezione in elencoLezioni avverte l'amministratore
		easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
		easyDrive.removeLezione(LocalDate.of(2023, 2, 27), LocalTime.of(20, 52)); //La data selezionata non esiste in elenco
		assertNotNull(easyDrive.getLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)));
	}
	
	@Test
	public void testAggiornaFrequenzaClienti() {
		easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
		//La lezione selezionata diventa corrente in easyDrive
		easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		assertNotNull(easyDrive.getLezioneCorrente());
	}
	
	@Test
	public void testInserisciClienteFrequentante() {
		easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
		easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		//Il cliente selezionato diventa corrente se esiste una lezione corrente
		easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		assertNotNull(easyDrive.getLezioneCorrente());
		easyDrive.inserisciClienteFrequentante("AR202051");
		assertNotNull(easyDrive.getClienteCorrente());
		//Il cliente selezionato non diventa corrente se non esiste una lezione corrente
		easyDrive.setClienteCorrente(null);
		easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 3, 1), LocalTime.of(20, 52)); //La data della lezione non è inserita in elencoLezioni
		assertNull(easyDrive.getLezioneCorrente());
		easyDrive.inserisciClienteFrequentante("AR202051");
		assertNull(easyDrive.getClienteCorrente());
	}
	
	@Test
	public void testConfermaInserimento() {
		easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
		easyDrive.addLezione(LocalDate.of(2023, 3, 5), LocalTime.of(13, 45), easyDrive.getListaArgomenti().get(1));
		easyDrive.addLezione(LocalDate.of(2023, 3, 14), LocalTime.of(18, 00), easyDrive.getListaArgomenti().get(2));
		easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
				"via Rossi 25");
		//Selezionando una lezione e un cliente corretti si aggiorna la frequenza se clienteCorrente non ha già seguito lezioneCorrente
		easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		easyDrive.inserisciClienteFrequentante("AR202051");
		easyDrive.confermaInserimento();
		System.out.println(easyDrive.getClienteCorrente().getFrequenzaLezioni() + "%");
		easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 3, 14), LocalTime.of(18, 00));
		easyDrive.inserisciClienteFrequentante("AR202051");
		easyDrive.confermaInserimento();
		System.out.println(easyDrive.getClienteCorrente().getFrequenzaLezioni() + "%");
		//Se clienteCorrente ha già seguito lezioneCorrente la sua frequenza non varia
		easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		easyDrive.inserisciClienteFrequentante("AR202051");
		easyDrive.confermaInserimento();
		System.out.println(easyDrive.getClienteCorrente().getFrequenzaLezioni() + "%");
		//Se clienteCorrente non esiste non avviene la conferma dell'inserimento
		easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		easyDrive.inserisciClienteFrequentante("MR651515"); //Il codice fiscale non è presente all'interno di listaClienti
		assertNull(easyDrive.getClienteCorrente());
		easyDrive.confermaInserimento();
	}
}
