package main;

import java.sql.Time;
import java.util.Date;

public class Lezione {
	
	private String codice;
	private Date data;
	private Time ora;
	private Argomento argomentoTrattato;
	
	public Lezione(Date data, Time ora, Argomento argomentoTrattato) {
		super();		
		this.data = data;
		this.ora = ora;
		this.argomentoTrattato = argomentoTrattato;
		this.codice = this.data.toString() + this.ora.toString();
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getOra() {
		return ora;
	}

	public void setOra(Time ora) {
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
