package main;

import java.time.LocalDate;
import java.time.LocalTime;

public class Guida extends Attività{

	public Guida(LocalDate data, LocalTime ora) {
		super(data, ora);
	}

	@Override
	public void prenotaCliente(Cliente c) throws Exception {
		if(c.getFoglioRosa()) {
			this.elencoPrenotatiAttività.put(c.getCodiceFiscale(), c);
			System.out.println("Il cliente " + c.getCognome() + " prenotato correttamente per la guida");
		}else {
			System.out.println("Il cliente " + c.getNome() + " " + c.getCognome() + " non può essere prenotato poichè non ha ancora superato l'esame teorico");
			throw new Exception("Il cliente non può essere prenotato poichè non ha ancora superato l'esame teorico");
		}
	}

	@Override
	public String toString() {
		return "Guida [codice=" + codice + ", data=" + data + ", ora=" + ora + ", elencoPrenotatiAttività="
				+ elencoPrenotatiAttività + "]";
	}

	
}
