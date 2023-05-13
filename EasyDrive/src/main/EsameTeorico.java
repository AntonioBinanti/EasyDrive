package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class EsameTeorico {
	
	private String codice;
	private LocalDate data;
	private LocalTime ora;
	private HashMap<String, Cliente> elencoPrenotatiEsameTeorico; 
	
	public EsameTeorico(LocalDate data, LocalTime ora) {
		this.codice = LocalDateTime.of(data, ora).toString();
		this.data = data;
		this.ora = ora;
		this.elencoPrenotatiEsameTeorico = new HashMap<>(); 
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOra() {
		return ora;
	}

	public void setOra(LocalTime ora) {
		this.ora = ora;
	}

	public HashMap<String, Cliente> getElencoPrenotatiEsameTeorico() { 
		return elencoPrenotatiEsameTeorico;
	}
	
	public boolean isDisponibile() {
		LocalDateTime dateTime = LocalDateTime.of(this.data, this.ora);
        LocalDateTime dateTimeCorrente = LocalDateTime.now();
        return dateTime.isAfter(dateTimeCorrente); 
	}
	
	public void prenotaCliente(Cliente c) {
		float frequenza = c.getFrequenzaLezioni();
		if(frequenza >= 70.0) {
			this.elencoPrenotatiEsameTeorico.put(c.getCodiceFiscale(), c); 
		}else {
			System.out.println("Il cliente " + c.getCognome() + " non può essere prenotato per l'esame poichè ha una frequenza lezioni del "
					+ c.getFrequenzaLezioni() + "(Minore del 70% richiesto)");
		}
	}

	@Override
	public String toString() {
		return "EsameTeorico [codice=" + codice + ", data=" + data + ", ora=" + ora + "]";
	}
}
