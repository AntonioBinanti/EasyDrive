package main;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Lezione {
	
	private String codice;
	/*private Date data;
	private Time ora;*/
	private LocalDate data;
	private LocalTime ora;
	private Argomento argomentoTrattato;
	
	public Lezione(/*Date data, Time ora*/ LocalDate data, LocalTime ora, Argomento argomentoTrattato) {	
		this.data = data;
		this.ora = ora;
		this.argomentoTrattato = argomentoTrattato;
		this.codice = LocalDateTime.of(data, ora).toString();
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

	public Argomento getArgomentoTrattato() {
		return argomentoTrattato;
	}

	public void setArgomentoTrattato(Argomento argomentoTrattato) {
		this.argomentoTrattato = argomentoTrattato;
	}

	@Override
	public String toString() {
		return "Lezione [codice=" + codice + ", data=" + data + ", ora=" + ora + ", argomentoTrattato="
				+ argomentoTrattato + "]";
	}
	
}
