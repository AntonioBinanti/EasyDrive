package main;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
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
		easyDrive.setAttivitàCorrente(null);
		easyDrive.setLezioneCorrente(null);
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
		try {
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.removeCliente("AR202051");
			assertNull(easyDrive.getCliente("AR202051"));
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessun cliente trovato con il codice fiscale selezionato");
		}
		//Se easyDrive non trova il cliente in listaClienti avverte l'amministratore
		try {
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.removeCliente("MR5181515");
			assertNotNull(easyDrive.getCliente("AR202051"));
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Impossibile rimuovere il cliente con il codice fiscale selezionato");
		}
		
	}
	
	@Test
	public void testAddLezione() {
		try {
			ArrayList<Argomento> listaArgomenti = easyDrive.getListaArgomenti();
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
			assertNull(easyDrive.getLezione(LocalDate.of(2013, 3, 5), LocalTime.of(13, 45)));
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna lezione trovata con la data e ora selezionate");
		}
	}
	
	@Test
	public void testRemoveLezione() {
		//La lezione presente in elencoLezioni viene rimossa sè trovata
		try {
			easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
			easyDrive.removeLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			assertNull(easyDrive.getLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)));
		}catch(Exception e){
			assertEquals(e.getMessage(),"Nessuna lezione trovata con la data e ora selezionate");
		}
		//Se easyDrive non trova la lezione in elencoLezioni avverte l'amministratore
		try {
			easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
			easyDrive.removeLezione(LocalDate.of(2023, 2, 27), LocalTime.of(20, 52)); //La data selezionata non esiste in elenco
			assertNotNull(easyDrive.getLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)));
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Impossibile rimuovere la lezione con la data e l'ora selezionate");
		}
	}
	
	@Test
	public void testAggiornaFrequenzaClienti() {
		try {
			easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
			//La lezione selezionata diventa corrente in easyDrive
			easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			assertNotNull(easyDrive.getLezioneCorrente());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testInserisciClienteFrequentante() {
		try {
			easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
		}catch(Exception e) {
			fail("Unexpected exception");
		}		
		//Il cliente selezionato diventa corrente se esiste una lezione corrente
		try {
			easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			assertNotNull(easyDrive.getLezioneCorrente());
			easyDrive.inserisciClienteFrequentante("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			fail("Unexpected exception");
		}		
		//Il cliente selezionato non diventa corrente se non esiste una lezione corrente
		try {
			easyDrive.setClienteCorrente(null);
			easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 3, 1), LocalTime.of(20, 52)); //La data della lezione non è inserita in elencoLezioni
			assertNull(easyDrive.getLezioneCorrente());
			easyDrive.inserisciClienteFrequentante("AR202051");
			assertNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna lezione selezionata");
		}
	}

	@Test
	public void testConfermaInserimento() {
		try {
			easyDrive.addLezione(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52), easyDrive.getListaArgomenti().get(0));
			easyDrive.addLezione(LocalDate.of(2023, 3, 5), LocalTime.of(13, 45), easyDrive.getListaArgomenti().get(1));
			easyDrive.addLezione(LocalDate.of(2023, 3, 14), LocalTime.of(18, 00), easyDrive.getListaArgomenti().get(2));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
		}catch(Exception e) {
			fail("Unexpected exception");
		}		
		//Selezionando una lezione e un cliente corretti si aggiorna la frequenza se clienteCorrente non ha già seguito lezioneCorrente
		try {
			easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciClienteFrequentante("AR202051");
			easyDrive.confermaInserimento();
			System.out.println(easyDrive.getClienteCorrente().getFrequenzaLezioni() + "%");
			easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 3, 14), LocalTime.of(18, 00));
			easyDrive.inserisciClienteFrequentante("AR202051");
			easyDrive.confermaInserimento();
			System.out.println(easyDrive.getClienteCorrente().getFrequenzaLezioni() + "%");
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se clienteCorrente ha già seguito lezioneCorrente la sua frequenza non varia
		try {
			easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciClienteFrequentante("AR202051");
			easyDrive.confermaInserimento();
			System.out.println(easyDrive.getClienteCorrente().getFrequenzaLezioni() + "%");
		}catch(Exception e) {
			assertEquals(e.getMessage(),"L'argomento è stato già seguito, la frequenza lezioni non è stata aggiornata");
		}
		//Se clienteCorrente non esiste non avviene la conferma dell'inserimento
		try {
			easyDrive.aggiornaFrequenzaClienti(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciClienteFrequentante("MR651515"); //Il codice fiscale non è presente all'interno di listaClienti
			assertNull(easyDrive.getClienteCorrente());
			easyDrive.confermaInserimento();
		}catch(Exception e){
			assertEquals(e.getMessage(),"Nessun cliente selezionato");
		}
	}
	
	@Test
	public void testAddEsameTeorico() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 4, 15), LocalTime.of(20, 29));
			easyDrive.addEsameTeorico(LocalDate.of(2022, 5, 14), LocalTime.of(20, 29));
			easyDrive.addEsameTeorico(LocalDate.of(2013, 12, 1), LocalTime.of(05, 15));
			
			Map<String, Attività> a = easyDrive.getElencoAttività() ;
			if(a.isEmpty()) {
				System.out.println("Nessuna esame teorico in lista");
			}else {
				for(Attività esame : a.values()) {
				System.out.println(esame);
				}
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		try {
			assertNotNull(easyDrive.getAttività(LocalDate.of(2023, 4, 15), LocalTime.of(20, 29))); //Data di un esame esistente
			assertNull(easyDrive.getAttività(LocalDate.of(2013, 3, 5), LocalTime.of(13, 45))); //data di un esame non esistente
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
	}
	
	@Test
	public void testRemoveEsameTeorico() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.removeAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			assertNull(easyDrive.getAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)));
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
		//Se easyDrive non trova l'esame teorico in elencoAttività avverte l'amministratore
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.removeAttività(LocalDate.of(2013, 1, 14), LocalTime.of(10, 28)); //Impossibile rimuovere poichè non è registrato nessun esame per la data e ora salezionati
			assertNotNull(easyDrive.getAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)));
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Impossibile rimuovere l'attività con la data e l'ora selezionate");
		}
	}
	
	@Test
	public void testPrenotaEsameTeorico() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2053, 2, 28), LocalTime.of(20, 52));
			easyDrive.addEsameTeorico(LocalDate.of(2053, 12, 4), LocalTime.of(07, 30));
			ArrayList<EsameTeorico> lista; //verrà riempita in seguito dal metodo "prenotaEsameTeorico" poichè esso scorrerà la mappa "elencoAttività" e ritornerà solo quelli con data successiva a quella odierna
			assertNotNull(easyDrive.prenotaEsameTeorico());
			lista = easyDrive.prenotaEsameTeorico();
			for(Attività a: lista) {
				System.out.println(a.toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testSelezionaEsame() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2053, 2, 28), LocalTime.of(20, 52));
			easyDrive.selezionaAttività(LocalDate.of(2053, 2, 28), LocalTime.of(20, 52));
			assertNotNull(easyDrive.getAttivitàCorrente());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se passiamo come parametro la data e l'ora di un esame teorico non registrato il sistema ci avvertirà
		try {
			easyDrive.setAttivitàCorrente(null);
			easyDrive.selezionaAttività(LocalDate.of(2013, 4, 30), LocalTime.of(17, 15)); //Data e ora di un esame teorico non esistente
			assertNull(easyDrive.getAttivitàCorrente());
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
	}
	
	@Test
	public void testInserisciCliente() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//se selezioniamo un cliente non registrato nel sistema, non sarà possibile completare l'operazione
		try {
			easyDrive.inserisciCliente("ZZ151515");
			assertNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessun cliente trovato con il codice fiscale selezionato");
		}
		//selezioniamo un cliente registrato nel sistema
		try {
			easyDrive.inserisciCliente("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se selezioniamo un esame non esistente non è possibile completare l'operazione
		try {
			easyDrive.setClienteCorrente(null);
			easyDrive.setAttivitàCorrente(null);
			easyDrive.selezionaAttività(LocalDate.of(2021, 3, 15), LocalTime.of(04, 30));
			easyDrive.inserisciCliente("AR202051");
			assertNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
	}
	
	@Test
	public void testConfermaPrenotazione() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			Argomento a1 = new Argomento("Segnali di pericolo");
			Cliente c = easyDrive.getCliente("AR202051");
			c.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciCliente("AR202051");
			easyDrive.confermaPrenotazioneEsameTeorico();
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testEsitiEsameTeoricoPubblica() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)); 
			easyDrive.addEsameTeorico(LocalDate.of(2024, 5, 18), LocalTime.of(20, 52)); //Esame teorico con data non antecedente, non verrà inserito nella lista "esamiTeoriciDisponibili"
			easyDrive.addEsameTeorico(LocalDate.of(2023, 7, 20), LocalTime.of(20, 52));
			ArrayList<EsameTeorico> esamiTeoriciDisponibili = new ArrayList<>();
			esamiTeoriciDisponibili = easyDrive.esitiEsameTeoricoPubblica();
			assertNotNull(esamiTeoriciDisponibili);
			for (Attività a : esamiTeoriciDisponibili) {
				System.out.println(a.toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testEsitiEsameTeoricoSeleziona() {
		HashMap<String, Cliente> prenotati = new HashMap<>();
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addCliente("MB202051", "Alessio", "Bianchi", Date.valueOf("2001-1-1"), "0954789562", "Alessio.Bianchi@gmail.com", 
					"via Rossi 26");
			easyDrive.addCliente("MV202051", "Marco", "Verdi", Date.valueOf("2002-1-1"), "0951616129", "Marco.Verdi@gmail.com", 
					"via Rossi 24");
			
			Argomento a1 = new Argomento("Segnali di pericolo"); //Utilizzato per incrementare la frequenza lezioni dei clienti in modo da permettere la prenotazione all'esame teorico 
			//Prenotiamo i clienti all'esame teorico
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			Cliente c1 = easyDrive.getCliente("AR202051");
			Cliente c2 = easyDrive.getCliente("MB202051");
			Cliente c3 = easyDrive.getCliente("MV202051");
			//Inserimento clienti all'esame teorico
			c1.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c1.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			c2.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c2.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			c3.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c3.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se inseriamo la data di un esame teorico non registrato nel sistema verremo avvertiti
		try {
			prenotati = easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2022, 3, 10), LocalTime.of(20, 52));
			assertNull(easyDrive.getAttivitàCorrente());
			assertNull(prenotati);
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
		try {
			prenotati = easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			assertNotNull(easyDrive.getAttivitàCorrente());
			assertNotNull(prenotati);
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testEsameTeoricoInserisciCliente() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addCliente("MB202051", "Alessio", "Bianchi", Date.valueOf("2001-1-1"), "0954789562", "Alessio.Bianchi@gmail.com", 
					"via Rossi 26");
			easyDrive.addCliente("MV202051", "Marco", "Verdi", Date.valueOf("2002-1-1"), "0951616129", "Marco.Verdi@gmail.com", 
					"via Rossi 24");
			
			Argomento a1 = new Argomento("Segnali di pericolo"); //Utilizzato per incrementare la frequenza lezioni dei clienti in modo da permettere la prenotazione all'esame teorico 
			
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			Cliente c1 = easyDrive.getCliente("AR202051");
			Cliente c2 = easyDrive.getCliente("MB202051");
			Cliente c3 = easyDrive.getCliente("MV202051");
			//Inserimento clienti all'esame teorico
			c1.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c1.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			c2.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c2.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			c3.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c3.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			HashMap<String, Cliente> prenotati = new HashMap<>();
			prenotati =	easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.esitoEsameTeoricoInserisciCliente(c1.getCodiceFiscale());
			assertTrue(c1.getFoglioRosa());
			easyDrive.esitoEsameTeoricoInserisciCliente(c2.getCodiceFiscale());
			assertTrue(c2.getFoglioRosa());
			assertFalse(c3.getFoglioRosa());
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testEsitiEsameTeoricoConferma() {
		try {
			easyDrive.addEsameTeorico(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addCliente("MB202051", "Alessio", "Bianchi", Date.valueOf("2001-1-1"), "0954789562", "Alessio.Bianchi@gmail.com", 
					"via Rossi 26");
			easyDrive.addCliente("MV202051", "Marco", "Verdi", Date.valueOf("2002-1-1"), "0951616129", "Marco.Verdi@gmail.com", 
					"via Rossi 24");
			
			Argomento a1 = new Argomento("Segnali di pericolo"); //Utilizzato per incrementare la frequenza lezioni dei clienti in modo da permettere la prenotazione all'esame teorico 
			
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			Cliente c1 = easyDrive.getCliente("AR202051");
			Cliente c2 = easyDrive.getCliente("MB202051");
			Cliente c3 = easyDrive.getCliente("MV202051");
			//Inserimento clienti all'esame teorico
			c1.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c1.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			c2.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c2.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			c3.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.inserisciCliente(c3.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			
			HashMap<String, Cliente> prenotati = new HashMap<>(); 
			prenotati =	easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)); //Lista Prenotati all'esame teorico (NON LISTA CLIENTI)
			easyDrive.esitoEsameTeoricoInserisciCliente(c1.getCodiceFiscale());
			assertTrue(c1.getFoglioRosa());
			easyDrive.esitoEsameTeoricoInserisciCliente(c2.getCodiceFiscale());
			assertTrue(c2.getFoglioRosa());
			assertFalse(c3.getFoglioRosa());
			easyDrive.esitiEsameTeoricoConferma();
			assert c1.getNumeroBocciature() == 0;
			assert c2.getNumeroBocciature() == 0;
			assert c3.getNumeroBocciature() > 0;
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
			//Ripetiamo nuovcamente i metodi in modo da incrementare il numero di bocciature di c3
			easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.esitiEsameTeoricoConferma();
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testAggiornaBoccciatiDebitori() {
		try {
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addCliente("MB202051", "Alessio", "Bianchi", Date.valueOf("2001-1-1"), "0954789562", "Alessio.Bianchi@gmail.com", 
					"via Rossi 26");
			easyDrive.addCliente("MV202051", "Marco", "Verdi", Date.valueOf("2002-1-1"), "0951616129", "Marco.Verdi@gmail.com", 
					"via Rossi 24");
			Cliente c1 = easyDrive.getCliente("AR202051");
			Cliente c2 = easyDrive.getCliente("MB202051");
			c1.setNumeroBocciature(2);
			c2.setNumeroBocciature(3);
			easyDrive.aggiornaBocciatiDebitori();
			System.out.println("Lista clienti:");
			for (Map.Entry<String, Cliente> entry : easyDrive.getListaClienti().entrySet()) {
				System.out.println(entry.getValue().toString());
			}
			System.out.println("Lista debitori:");
			for (Map.Entry<String, Cliente> entry : easyDrive.getListaBocciatiDebitori().entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testAddGuida() {
		try {
			easyDrive.addGuida(LocalDate.of(2023, 4, 15), LocalTime.of(20, 29));
			easyDrive.addGuida(LocalDate.of(2022, 5, 14), LocalTime.of(20, 29));
			easyDrive.addGuida(LocalDate.of(2013, 12, 1), LocalTime.of(05, 15));
			
			Map<String, Attività> a = easyDrive.getElencoAttività();
			if(a.isEmpty()) {
				System.out.println("Nessuna guida in lista");
			}else {
				for(Attività esame : a.values()) {
				System.out.println(esame);
				}
			}
			assertNotNull(easyDrive.getAttività(LocalDate.of(2023, 4, 15), LocalTime.of(20, 29))); //Data di una guida esistente
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se cerchiamo una guida non esistente il sistema ci avvertirà 
		try {
			assertNull(easyDrive.getAttività(LocalDate.of(2013, 3, 5), LocalTime.of(13, 45))); //data di una guida non esistente
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
	}
	
	@Test
	public void testPrenotaGuida() {
		try {
			easyDrive.addGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)); //non verrà inserito nella lista "guideDisponibili"
			easyDrive.addGuida(LocalDate.of(2024, 5, 18), LocalTime.of(20, 52)); 
			easyDrive.addGuida(LocalDate.of(2023, 7, 20), LocalTime.of(20, 52)); //non verrà inserito nella lista "guideDisponibili"
			ArrayList<Guida> guideDisponibili = new ArrayList<>();
			guideDisponibili = easyDrive.prenotaGuida();
			assertNotNull(guideDisponibili);
			for (Attività a : guideDisponibili) {
				System.out.println(a.toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testConfermaPrenotazioneGuida() {
		try {
			easyDrive.addGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addEsameTeorico(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			Argomento a1 = new Argomento("Segnali di pericolo"); //Utilizzato per incrementare la frequenza lezioni dei clienti in modo da permettere la prenotazione all'esame teorico 
			Cliente c = easyDrive.getCliente("AR202051");
			c.incrementaFrequenzaLezioni(a1, 1);
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se selezioniamo un attività con data non registrata nel sistema saremo avvisati
		try {
			easyDrive.selezionaAttività(LocalDate.of(2019, 7, 14), LocalTime.of(20, 52));
			assertNull(easyDrive.getAttivitàCorrente());
			
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciCliente("AR202051");
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
		//Il cliente non può essere prenotato se non ha ancora superato l'esame teorico
		try {
			easyDrive.confermaPrenotazioneGuida();
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Il cliente non può essere prenotato poichè non ha ancora superato l'esame teorico");
		}
		//Prenotiamo e facciamo superare un esame teorico al cliente in modo da poter prenotare una guida
		try {
			Cliente c = easyDrive.getCliente("AR202051");
			easyDrive.selezionaAttività(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.inserisciCliente(c.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.esitoEsameTeoricoInserisciCliente(c.getCodiceFiscale());
			assertTrue(c.getFoglioRosa());
			easyDrive.esitiEsameTeoricoConferma();
			//prenotazione guida
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se inseriamo un cliente non registrato nel sistema saremo avvisati
		try {
			easyDrive.inserisciCliente("ABC1234");
			assertNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessun cliente trovato con il codice fiscale selezionato");
		}
		try {
			easyDrive.inserisciCliente("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
			easyDrive.confermaPrenotazioneGuida();
			System.out.println("Prenotati alla guida:");
			for (Map.Entry<String, Cliente> entry : easyDrive.getAttivitàCorrente().getElencoPrenotatiAttività().entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testAggiornNumeroGuide() {
		try {
			easyDrive.addGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addGuida(LocalDate.of(2184, 5, 18), LocalTime.of(20, 52)); //non verrà inserito nella lista "guideDisponibili" poichè non ha una data antecedente a quella odierna
			easyDrive.addGuida(LocalDate.of(2023, 7, 20), LocalTime.of(20, 52)); 
			ArrayList<Guida> guideDisponibili = new ArrayList<>();
			guideDisponibili = easyDrive.aggiornaNumeroGuide();
			assertNotNull(guideDisponibili);
			for (Attività a : guideDisponibili) {
				System.out.println(a.toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testSelezionaGuida() {
		try {
			easyDrive.addGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addEsameTeorico(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			Argomento a1 = new Argomento("Segnali di pericolo"); //Utilizzato per incrementare la frequenza lezioni dei clienti in modo da permettere la prenotazione all'esame teorico 
			Cliente c = easyDrive.getCliente("AR202051");
			c.incrementaFrequenzaLezioni(a1, 1);
			//Prenotiamo e facciamo superare un esame teorico al cliente in modo da poter prenotare una guida
			easyDrive.selezionaAttività(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.inserisciCliente(c.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.esitoEsameTeoricoInserisciCliente(c.getCodiceFiscale());
			assertTrue(c.getFoglioRosa());
			easyDrive.esitiEsameTeoricoConferma();
			//Prenotazione guida
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));				
			easyDrive.inserisciCliente("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
			easyDrive.confermaPrenotazioneGuida();
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		HashMap<String, Cliente> prenotati = new HashMap<>();
		//Se inseriamo la data di una guida non registrata nel sistema verremo avvertiti
		try {
			prenotati = easyDrive.selezionaGuida(LocalDate.of(2022, 3, 10), LocalTime.of(20, 52));
			assertNull(easyDrive.getAttivitàCorrente());
			assertNull(prenotati);
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
		try {
			prenotati = easyDrive.selezionaGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			assertNotNull(easyDrive.getAttivitàCorrente());
			assertNotNull(prenotati);
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testAggiornaGuideInserisciCliente() {
		try {
			easyDrive.addGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addEsameTeorico(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			Argomento a1 = new Argomento("Segnali di pericolo"); //Utilizzato per incrementare la frequenza lezioni dei clienti in modo da permettere la prenotazione all'esame teorico 
			Cliente c = easyDrive.getCliente("AR202051");
			c.incrementaFrequenzaLezioni(a1, 1);
			//Prenotiamo e facciamo superare un esame teorico al cliente in modo da poter prenotare una guida
			easyDrive.selezionaAttività(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.inserisciCliente(c.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.esitoEsameTeoricoInserisciCliente(c.getCodiceFiscale());
			assertTrue(c.getFoglioRosa());
			easyDrive.esitiEsameTeoricoConferma();
			//Prenotazione guida
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));				
			easyDrive.inserisciCliente("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
			easyDrive.confermaPrenotazioneGuida();
			//Selezioniamo il cliente che ha effettuato la guida 
			easyDrive.selezionaGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se il cliente non è registrato nel sistema verremo avvisati
		try {
			easyDrive.aggiornaGuideInserisciCliente("XYZ9876");
			assertNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessun cliente trovato con il codice fiscale selezionato");
		}
		try {
			easyDrive.aggiornaGuideInserisciCliente("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testAggiornaGuideConferma() {
		try {
			easyDrive.addGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addEsameTeorico(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			Argomento a1 = new Argomento("Segnali di pericolo"); //Utilizzato per incrementare la frequenza lezioni dei clienti in modo da permettere la prenotazione all'esame teorico 
			Cliente c = easyDrive.getCliente("AR202051");
			c.incrementaFrequenzaLezioni(a1, 1);
			//Prenotiamo e facciamo superare un esame teorico al cliente in modo da poter prenotare una guida
			easyDrive.selezionaAttività(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.inserisciCliente(c.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameTeorico();
			easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2023, 1, 13), LocalTime.of(20, 52));
			easyDrive.esitoEsameTeoricoInserisciCliente(c.getCodiceFiscale());
			assertTrue(c.getFoglioRosa());
			easyDrive.esitiEsameTeoricoConferma();
			//Prenotazione guida
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));				
			easyDrive.inserisciCliente("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
			easyDrive.confermaPrenotazioneGuida();
			//Selezioniamo il cliente che ha effettuato la guida 
			easyDrive.selezionaGuida(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.aggiornaGuideInserisciCliente("AR202051");
			assertNotNull(easyDrive.getClienteCorrente());
			//Dopo la conferma il numero guide del cliente è aumentato
			easyDrive.aggiornaGuideConferma();
			assert c.getNumeroGuide() == 1;
			System.out.println(c.toString());
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testAddEsameFinale() {
		try {
			easyDrive.addEsameFinale(LocalDate.of(2023, 4, 15), LocalTime.of(20, 29));
			easyDrive.addEsameFinale(LocalDate.of(2022, 5, 14), LocalTime.of(20, 29));
			easyDrive.addEsameFinale(LocalDate.of(2013, 12, 1), LocalTime.of(05, 15));
			
			Map<String, Attività> a = easyDrive.getElencoAttività() ;
			if(a.isEmpty()) {
				System.out.println("Nessuna esame finale in lista");
			}else {
				for(Attività esame : a.values()) {
				System.out.println(esame);
				}
			}
			assertNotNull(easyDrive.getAttività(LocalDate.of(2023, 4, 15), LocalTime.of(20, 29))); //Data di un esame finale esistente
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//Se cerchiamo la data e l'ora di un esame finale non inserito il sistema ci avvertirà
		try {
			assertNull(easyDrive.getAttività(LocalDate.of(2013, 3, 5), LocalTime.of(13, 45))); //data di un esame finale non esistente
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
	}
	
	@Test
	public void testPrenotaEsameFinale() {
		try {
			easyDrive.addEsameFinale(LocalDate.of(2053, 2, 28), LocalTime.of(20, 52));
			easyDrive.addEsameFinale(LocalDate.of(2053, 12, 4), LocalTime.of(07, 30));
			ArrayList<EsameFinale> lista; //verrà riempita in seguito dal metodo "prenotaEsameFinale" poichè esso scorrerà la mappa "elencoAttività" e ritornerà solo quelli con data successiva a quella odierna
			assertNotNull(easyDrive.prenotaEsameFinale());
			lista = easyDrive.prenotaEsameFinale();
			for(Attività a: lista){
				System.out.println(a.toString());
				}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testConfermaPrenotazioneEsameFinale() {
		try {
			easyDrive.addEsameFinale(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addEsameTeorico(LocalDate.of(2022, 12, 10), LocalTime.of(20, 00));
			easyDrive.addGuida(LocalDate.of(2023, 1, 15), LocalTime.of(12, 00));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			Argomento a1 = new Argomento("Segnali di pericolo");
			Cliente c = easyDrive.getCliente("AR202051");
			c.incrementaFrequenzaLezioni(a1, 1);
			easyDrive.prenotaEsameFinale();
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciCliente("AR202051");
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		//se il cliente non ha ne un numero di guide sufficienti ne il foglio rosa, non potrà essere prenotato per l'esame finale
		try {
			easyDrive.prenotaEsameFinale();
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciCliente("AR202051");
			easyDrive.confermaPrenotazioneEsameFinale();
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Il cliente non può essere prenotato per l'esame poichè ha un numero di guide minore delle 15 richieste");
		}
		//prenotiamo il cliente ad un esame teorico
		try {
			easyDrive.prenotaEsameTeorico();
			easyDrive.selezionaAttività(LocalDate.of(2022, 12, 10), LocalTime.of(20, 00));
			easyDrive.inserisciCliente("AR202051");
			easyDrive.confermaPrenotazioneEsameTeorico();
			//promuoviamo il cliente all'esame teorico (foglioRosa=True)
			easyDrive.esitiEsameTeoricoPubblica();
			easyDrive.esitiEsameTeoricoSeleziona(LocalDate.of(2022, 12, 10), LocalTime.of(20, 00));
			easyDrive.esitoEsameTeoricoInserisciCliente("AR202051");
			easyDrive.esitiEsameTeoricoConferma();
			//incrementiamo il numero di guide del cliente e prenotiamo il cliente all'esame finale
			Cliente c = easyDrive.getCliente("AR202051");
			c.setNumeroGuide(15);
			easyDrive.prenotaEsameFinale();
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.inserisciCliente("AR202051");
			easyDrive.confermaPrenotazioneEsameFinale();
			assertNotNull(easyDrive.getAttivitàCorrente().getElencoPrenotatiAttività());
		}catch(Exception e) {
			fail("Unexpected exception");
		}		
	}
	
	@Test
	public void testEsitiEsameFinalePubblica() {
		try {
			easyDrive.addEsameFinale(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)); 
			easyDrive.addEsameFinale(LocalDate.of(2024, 5, 18), LocalTime.of(20, 52)); //Esame finale con data non antecedente, non verrà inserito nella lista "esamiFinaliDisponibili"
			easyDrive.addEsameFinale(LocalDate.of(2023, 7, 20), LocalTime.of(20, 52));
			ArrayList<EsameFinale> esamiFinaliDisponibili = new ArrayList<>();
			esamiFinaliDisponibili = easyDrive.esitiEsameFinalePubblica();
			assertNotNull(esamiFinaliDisponibili);
			for (Attività a : esamiFinaliDisponibili) {
				System.out.println(a.toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testEsitiEsameFinaleSeleziona() {
		try {
			easyDrive.addEsameFinale(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addCliente("MB202051", "Alessio", "Bianchi", Date.valueOf("2001-1-1"), "0954789562", "Alessio.Bianchi@gmail.com", 
					"via Rossi 26");
			easyDrive.addCliente("MV202051", "Marco", "Verdi", Date.valueOf("2002-1-1"), "0951616129", "Marco.Verdi@gmail.com", 
					"via Rossi 24");
			
			//Prenotiamo i clienti all'esame finale
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			Cliente c1 = easyDrive.getCliente("AR202051");
			Cliente c2 = easyDrive.getCliente("MB202051");
			Cliente c3 = easyDrive.getCliente("MV202051");
			//Inserimento clienti all'esame finale
			c1.setFoglioRosa(true);
			c1.setNumeroGuide(15);
			easyDrive.inserisciCliente(c1.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
			
			c2.setFoglioRosa(true);
			c2.setNumeroGuide(15);
			easyDrive.inserisciCliente(c2.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
			
			c3.setFoglioRosa(true);
			c3.setNumeroGuide(15);
			easyDrive.inserisciCliente(c3.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
		}catch(Exception e) {
			fail("Unexpected exception");
		}
		HashMap<String, Cliente> prenotati = new HashMap<>();
		//Se inseriamo la data di un esame finale non registrato nel sistema verremo avvertiti
		try {
			prenotati = easyDrive.esitiEsameFinaleSeleziona(LocalDate.of(2022, 3, 10), LocalTime.of(20, 52));
			assertNull(easyDrive.getAttivitàCorrente());
			assertNull(prenotati);
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Nessuna attività trovata con la data e ora selezionate");
		}
		try {
			prenotati = easyDrive.esitiEsameFinaleSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			assertNotNull(easyDrive.getAttivitàCorrente());
			assertNotNull(prenotati);
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testEsameFinaleInserisciCliente() {
		try {
			easyDrive.addEsameFinale(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addCliente("MB202051", "Alessio", "Bianchi", Date.valueOf("2001-1-1"), "0954789562", "Alessio.Bianchi@gmail.com", 
					"via Rossi 26");
			easyDrive.addCliente("MV202051", "Marco", "Verdi", Date.valueOf("2002-1-1"), "0951616129", "Marco.Verdi@gmail.com", 
					"via Rossi 24");
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			Cliente c1 = easyDrive.getCliente("AR202051");
			Cliente c2 = easyDrive.getCliente("MB202051");
			Cliente c3 = easyDrive.getCliente("MV202051");
			//Inserimento clienti all'esame finale
			c1.setFoglioRosa(true);
			c1.setNumeroGuide(15);
			easyDrive.inserisciCliente(c1.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
					
			c2.setFoglioRosa(true);
			c2.setNumeroGuide(15);
			easyDrive.inserisciCliente(c2.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
					
			c3.setFoglioRosa(true);
			c3.setNumeroGuide(15);
			easyDrive.inserisciCliente(c3.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
			
			HashMap<String, Cliente> prenotati = new HashMap<>();
			prenotati =	easyDrive.esitiEsameFinaleSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.esitoEsameFinaleInserisciCliente(c1.getCodiceFiscale());
			assertTrue(c1.getPatente());
			easyDrive.esitoEsameFinaleInserisciCliente(c2.getCodiceFiscale());
			assertTrue(c2.getPatente());
			assertFalse(c3.getPatente());
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void testEsitiEsameFinaleConferma() {
		try {
			easyDrive.addEsameFinale(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.addCliente("AR202051", "Alessio", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Alessio.Rossi@gmail.com", 
					"via Rossi 25");
			easyDrive.addCliente("MB202051", "Alessio", "Bianchi", Date.valueOf("2001-1-1"), "0954789562", "Alessio.Bianchi@gmail.com", 
					"via Rossi 26");
			easyDrive.addCliente("MV202051", "Marco", "Verdi", Date.valueOf("2002-1-1"), "0951616129", "Marco.Verdi@gmail.com", 
					"via Rossi 24");
			
			easyDrive.selezionaAttività(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			Cliente c1 = easyDrive.getCliente("AR202051");
			Cliente c2 = easyDrive.getCliente("MB202051");
			Cliente c3 = easyDrive.getCliente("MV202051");
			
			//Inserimento clienti all'esame finale
			c1.setFoglioRosa(true);
			c1.setNumeroGuide(15);
			easyDrive.inserisciCliente(c1.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
							
			c2.setFoglioRosa(true);
			c2.setNumeroGuide(15);
			easyDrive.inserisciCliente(c2.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
							
			c3.setFoglioRosa(true);
			c3.setNumeroGuide(15);
			easyDrive.inserisciCliente(c3.getCodiceFiscale());
			easyDrive.confermaPrenotazioneEsameFinale();
					
			HashMap<String, Cliente> prenotati = new HashMap<>(); 
			prenotati =	easyDrive.esitiEsameFinaleSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52)); //Lista Prenotati all'esame finale (NON LISTA CLIENTI)
			easyDrive.esitoEsameFinaleInserisciCliente(c1.getCodiceFiscale());
			assertTrue(c1.getPatente());
			easyDrive.esitoEsameFinaleInserisciCliente(c2.getCodiceFiscale());
			assertTrue(c2.getPatente());
			assertFalse(c3.getPatente());
			easyDrive.esitiEsameFinaleConferma();
			assert c1.getNumeroBocciatureEsameFinale() == 0;
			assert c2.getNumeroBocciatureEsameFinale() == 0;
			assert c3.getNumeroBocciatureEsameFinale() > 0;
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
			//Se ripetiamo i metodi verrà incrementato il numero di bocciature di c3
			easyDrive.esitiEsameFinaleSeleziona(LocalDate.of(2023, 2, 28), LocalTime.of(20, 52));
			easyDrive.esitiEsameFinaleConferma();
			assertFalse(c3.getFoglioRosa());
			assert c3.getNumeroGuide() == 0; 
			for (Map.Entry<String, Cliente> entry : prenotati.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}catch(Exception e) {
			fail("Unexpected exception");
		}
	}
}