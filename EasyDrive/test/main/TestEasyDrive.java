package main;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert.*;

public class TestEasyDrive {
	
	static EasyDrive easyDrive;
	
	@BeforeClass
	public static void initTest() {
		easyDrive = EasyDrive.getInstance();
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
	public void testAddLezione() {
		ArrayList<Argomento> listaArgomenti = easyDrive.getListaArgomenti();
		easyDrive.addLezione(Date.valueOf("2023-2-28"), Time.valueOf("20:52:00"), listaArgomenti.get(1));
		easyDrive.addLezione(Date.valueOf("2023-3-5"), Time.valueOf("13:45:00"), listaArgomenti.get(2));
		easyDrive.addLezione(Date.valueOf("2023-3-14"), Time.valueOf("18:00:00"), listaArgomenti.get(3));
		Map<String, Lezione> l = easyDrive.getElencoLezioni();
		if(l.isEmpty()) {
			System.out.println("Nessuna lezione in lista");
		}else {
			for(Lezione lez : l.values()) {
			System.out.println(lez);
			}
		}
		assertNull(easyDrive.getLezione(Date.valueOf("2013-3-5"), Time.valueOf("13:45:00")));
		/*Lezione l1 = easyDrive.getLezione(Date.valueOf("2013-3-5"), Time.valueOf("13:45:00"));
		System.out.println("Lezione selezionata= " + l1.toString());*/
	}
}
