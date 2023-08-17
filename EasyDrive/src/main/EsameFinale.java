package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class EsameFinale extends Attività {
	
	public EsameFinale(LocalDate data, LocalTime ora) {
		super(data, ora);
	}
	
	public void promuoviCliente(String codiceFiscale) {
		Cliente c = this.elencoPrenotatiAttività.get(codiceFiscale);
		if(c != null) {
			c.setPatente(true);
		}else {
			System.out.println("Nessun cliente con il seguente codice fiscale era prenotato per l'esame finale selezionato");
		}
	}
	
	public void prenotaCliente(Cliente c) {
		int numGuide= c.getNumeroGuide();
		if(numGuide>=15) {
			this.elencoPrenotatiAttività.put(c.getCodiceFiscale(), c);
			System.out.println("Il cliente " + c.getCognome() + " prenotato correttamente per l'esame finale");
		}else {
			System.out.println("Il cliente " + c.getCognome() + " non può essere prenotato per l'esame poichè ha un numero di guide pari a "
					+ c.getNumeroGuide() + " (Minore delle 15 richieste)");
		}
	}
	
	public void confermaEsiti() {
		for (Map.Entry<String, Cliente> entry : elencoPrenotatiAttività.entrySet()) {
            boolean p = entry.getValue().getPatente();
            if(p == false) {
            	int numBocciature=entry.getValue().incrementaNumeroBocciatureEsameFinale();
            		if(numBocciature>=2) {
            		entry.getValue().setFoglioRosa(false);
            		entry.getValue().setNumeroGuide(0); 
            	    }
            }
        }
		System.out.println("Esiti esame finale confermati");
	}

	@Override
	public String toString() {
		return "EsameFinale [codice=" + codice + ", data=" + data + ", ora=" + ora + ", elencoPrenotatiAttività="
				+ elencoPrenotatiAttività + "]";
	}
	
}
