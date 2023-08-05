package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public abstract class Attività {
	protected String codice;
	protected LocalDate data;
	protected LocalTime ora;
	protected HashMap<String, Cliente> elencoPrenotatiAttività; 
	
	public Attività(LocalDate data, LocalTime ora) {
		this.codice = LocalDateTime.of(data, ora).toString();
		this.data = data;
		this.ora = ora;
		this.elencoPrenotatiAttività = new HashMap<>(); 
	}
	
	public boolean isDisponibile() {
		LocalDateTime dateTime = LocalDateTime.of(this.data, this.ora);
        LocalDateTime dateTimeCorrente = LocalDateTime.now();
        return dateTime.isAfter(dateTimeCorrente); 
	}
	
	public abstract void prenotaCliente(Cliente c);
	
	public boolean isAntecedente() {
		LocalDateTime dateTime = LocalDateTime.of(this.data, this.ora);
        LocalDateTime dateTimeCorrente = LocalDateTime.now();
        return dateTime.isBefore(dateTimeCorrente);
	}
	
	public HashMap<String, Cliente> getElencoPrenotatiAttività() { 
		return elencoPrenotatiAttività;
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

	@Override
	public String toString() {
		return "Attività [codice=" + codice + ", data=" + data + ", ora=" + ora + "]";
	}
}
