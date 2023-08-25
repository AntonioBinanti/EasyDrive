package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EsameTeorico extends Attività{
	
	public EsameTeorico(LocalDate data, LocalTime ora) {
		super(data, ora);
	}
	
	public void promuoviCliente(String codiceFiscale) throws Exception {
		Cliente c = this.elencoPrenotatiAttività.get(codiceFiscale);
		if(c != null) {
			c.setFoglioRosa(true);
			c.setNumeroBocciatureEsameFinale(0);
		}else {
			System.out.println("Nessun cliente con il seguente codice fiscale era prenotato per l'esame teorico selezionato");
			throw new Exception("Nessun cliente con il seguente codice fiscale era prenotato per l'esame teorico selezionato");
		}
	}
	
	public void prenotaCliente(Cliente c) throws Exception {
		float frequenza = c.getFrequenzaLezioni();
		if(frequenza >= 70.0) {
			this.elencoPrenotatiAttività.put(c.getCodiceFiscale(), c); 
			System.out.println("Il cliente " + c.getCognome() + " prenotato correttamente per l'esame teorico");
		}else {
			System.out.println("Il cliente " + c.getCognome() + " non può essere prenotato per l'esame poichè ha una frequenza lezioni del "
					+ c.getFrequenzaLezioni() + "(Minore del 70% richiesto)");
			throw new Exception("Il cliente non può essere prenotato per l'esame poichè ha una frequenza lezioni minore del 70% richiesto");
		}
	}
	
	public void confermaEsiti() {
		for (Map.Entry<String, Cliente> entry : elencoPrenotatiAttività.entrySet()) {
            boolean f = entry.getValue().getFoglioRosa();
            if(f == false) {
            	entry.getValue().incrementaNumeroBocciature();
            }
        }
		System.out.println("Esiti esame teorico confermati");
	}

	@Override
	public String toString() {
		return "EsameTeorico [codice=" + codice + ", data=" + data + ", ora=" + ora + ", elencoPrenotatiAttività="
				+ elencoPrenotatiAttività + "]";
	}
}
