package main;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class EasyDrive {
	
	private static EasyDrive easyDrive;
	private HashMap<String, Lezione> elencoLezioni;
	private Lezione lezioneCorrente;
	private ArrayList<Argomento> listaArgomenti;
	private HashMap<String, Cliente> listaClienti;
	private Cliente clienteCorrente;
	private HashMap<String, Attività> elencoAttività;
	private Attività attivitàCorrente;
	private HashMap<String, Cliente> listaBocciatiDebitori;
	
	private EasyDrive() {
		this.elencoLezioni = new HashMap<>();
		this.listaArgomenti = new ArrayList<>();
		this.listaClienti = new HashMap<>();
		this.elencoAttività = new HashMap<>();
		this.listaBocciatiDebitori = new HashMap<>();
		loadArgomenti();
		loadLezioni();
		loadClienti();
		loadAttività();
	}
	
	public static EasyDrive getInstance() {
		if (easyDrive == null)
			easyDrive = new EasyDrive();
		else
			System.out.println("Istanza già creata");

		return easyDrive;
	}
	
	public void addCliente(String codiceFiscale, String nome, String cognome, java.util.Date dataNascita, String numeroTelefono, String email, String indirizzo) {
		Cliente c = new Cliente(codiceFiscale, nome, cognome, dataNascita, numeroTelefono, email, indirizzo);
		this.listaClienti.put(codiceFiscale, c);
		System.out.println("Cliente "+ c.getNome() + " " + c.getCognome() + " inserito correttamente");
	}
	
	public Cliente getCliente(String codiceFiscale) throws Exception {
		Cliente c = this.listaClienti.get(codiceFiscale);
		if(c == null) {
			//System.out.println("Nessun cliente trovato con il codice fiscale selezionato");
			throw new Exception("Nessun cliente trovato con il codice fiscale selezionato");
		}else {
		return c;
		}
	}
	
	public void removeCliente(String codiceFiscale) throws Exception {
		if(this.listaClienti.remove(codiceFiscale) != null){
			System.out.println("Cliente eliminato correttamente");
		}else {
			//System.out.println("Impossibile rimuovere il cliente con il codice fiscale selezionato");
			throw new Exception("Impossibile rimuovere il cliente con il codice fiscale selezionato");
		}
	}
	
	public void addLezione(LocalDate data, LocalTime ora, Argomento argomentoTrattato) {
		Lezione l = new Lezione(data, ora, argomentoTrattato);
		this.elencoLezioni.put(l.getCodice(), l);
		System.out.println("Lezione inserita correttamente in data: " + l.getData().toString() + " e ora: " + l.getOra().toString());
	}
	
	public Lezione getLezione(LocalDate data, LocalTime ora) throws Exception {
		String dataOra = LocalDateTime.of(data, ora).toString();
		Lezione l = this.elencoLezioni.get(dataOra);
		if(l == null) {
			//System.out.println("Nessuna lezione trovata con la data e ora selezionate");
			throw new Exception("Nessuna lezione trovata con la data e ora selezionate");
		}else {
		return l;
		}
	}
	
	public void removeLezione(LocalDate data, LocalTime ora) throws Exception {
		String dataOra = LocalDateTime.of(data, ora).toString();
		if(this.elencoLezioni.remove(dataOra) != null) {
			System.out.println("Lezione eliminata correttamente");
		}else {
			//System.out.println("Impossibile rimuovere la lezione con la data e l'ora selezionate");
			throw new Exception("Impossibile rimuovere la lezione con la data e l'ora selezionate");
		}
	}
	
	public void aggiornaFrequenzaClienti(LocalDate data, LocalTime ora){
		String dataOra = LocalDateTime.of(data, ora).toString();
		Lezione l = this.elencoLezioni.get(dataOra);
		lezioneCorrente = l;
	}
	
	public void inserisciClienteFrequentante(String codiceFiscale) throws Exception {
		if(this.lezioneCorrente != null) {
			this.clienteCorrente = this.listaClienti.get(codiceFiscale);
		}else {
			throw new Exception("Nessuna lezione selezionata");
		}
	}
	
	public void confermaInserimento() throws Exception {
		if(this.clienteCorrente != null) {
			Argomento argomento = this.lezioneCorrente.getArgomentoTrattato();
			int numArgomentiTotali = this.listaArgomenti.size();
			this.clienteCorrente.incrementaFrequenzaLezioni(argomento, numArgomentiTotali);
		}else throw new Exception("Nessun cliente selezionato");
	}
	
	public void addEsameTeorico(LocalDate data, LocalTime ora) {
		Attività a = new EsameTeorico(data, ora);
		this.elencoAttività.put(a.getCodice(), a);
		System.out.println("Esame teorico fissato correttamente per la data: " + a.getData().toString() + " e ora: " + a.getOra().toString());
	}
	
	public Attività getAttività(LocalDate data, LocalTime ora) throws Exception {
		String dataOra = LocalDateTime.of(data, ora).toString();
		Attività a = this.elencoAttività.get(dataOra);
		if(a == null) {
			//System.out.println("Nessuna attività trovata con la data e ora selezionate");
			throw new Exception("Nessuna attività trovata con la data e ora selezionate");
		}else {
		return a;
		}
	}
	
	public void removeAttività(LocalDate data, LocalTime ora) throws Exception {
		String dataOra = LocalDateTime.of(data, ora).toString();
		if(this.elencoAttività.remove(dataOra) != null) {
			System.out.println("Attività eliminata correttamente");
		}else {
			//System.out.println("Impossibile rimuovere l'attività con la data e l'ora selezionate");
			throw new Exception("Impossibile rimuovere l'attività con la data e l'ora selezionate");
		}
	}
	
	public ArrayList<EsameTeorico> prenotaEsameTeorico() throws Exception {
		ArrayList<EsameTeorico> esamiTeoriciDisponibili = new ArrayList<>();
		for (Map.Entry<String, Attività> entry : elencoAttività.entrySet()) {
			if(entry.getValue() instanceof EsameTeorico) {
				if(entry.getValue().isDisponibile()){
                esamiTeoriciDisponibili.add((EsameTeorico)entry.getValue());
                }
			}
        }
		if(esamiTeoriciDisponibili.isEmpty()) {
			throw new Exception("Nessun esame teorico disponibile");
		}
		return esamiTeoriciDisponibili;
	}
	
	public void selezionaAttività(LocalDate data, LocalTime ora) throws Exception {
		this.attivitàCorrente = this.getAttività(data, ora);
	}
	
	public void inserisciCliente(String codiceFiscale) throws Exception {
		if(this.attivitàCorrente != null) {
			this.clienteCorrente = this.getCliente(codiceFiscale);
		}
	}
	
	public void confermaPrenotazioneEsameTeorico() throws Exception {
		if(this.clienteCorrente != null && this.attivitàCorrente instanceof EsameTeorico) {
			this.attivitàCorrente.prenotaCliente(this.clienteCorrente);
		}
	}
	
	public ArrayList<EsameTeorico> esitiEsameTeoricoPubblica() {
		ArrayList<EsameTeorico> esamiTeoriciDisponibili = new ArrayList<>();
		for (Map.Entry<String, Attività> entry : elencoAttività.entrySet()) {
			if(entry.getValue() instanceof EsameTeorico) {
				if(entry.getValue().isAntecedente() ){
                esamiTeoriciDisponibili.add((EsameTeorico)entry.getValue());
                }
			} 
        }
		return esamiTeoriciDisponibili;
	}
	
	public HashMap<String, Cliente> esitiEsameTeoricoSeleziona(LocalDate data, LocalTime ora) throws Exception{
		this.attivitàCorrente = this.getAttività(data, ora);
		if(this.attivitàCorrente != null) {
			return this.attivitàCorrente.getElencoPrenotatiAttività();
		}else {
			return null;
		}
	}
	
	public void esitoEsameTeoricoInserisciCliente(String codiceFiscale) throws Exception {
		if(this.attivitàCorrente != null && this.attivitàCorrente instanceof EsameTeorico) {
			EsameTeorico a = (EsameTeorico)this.attivitàCorrente;
			a.promuoviCliente(codiceFiscale);
		}else {
			//System.out.println("esame teorico non selezionato");
			throw new Exception("esame teorico non selezionato");
		}
	}
	
	public void esitiEsameTeoricoConferma() {
		if(this.attivitàCorrente instanceof EsameTeorico) {
			EsameTeorico e = (EsameTeorico)this.attivitàCorrente;
			e.confermaEsiti();
			this.aggiornaBocciatiDebitori();
		}
	}
	
	public void aggiornaBocciatiDebitori() {
		Iterator<Map.Entry<String, Cliente>> it = this.listaClienti.entrySet().iterator();
        while (it.hasNext()) {
        	Cliente c = it.next().getValue();
            if (c.getNumeroBocciature() >= 2){
            	this.listaBocciatiDebitori.put(c.getCodiceFiscale(), c);
                it.remove();
            }
        }
	}
	
	public void addGuida(LocalDate data, LocalTime ora) {
		Attività a = new Guida(data, ora);
		this.elencoAttività.put(a.getCodice(), a);
		System.out.println("Guida fissata correttamente per la data: " + a.getData().toString() + " e ora: " + a.getOra().toString());
	}
	
	public ArrayList<Guida> prenotaGuida(){
		ArrayList<Guida> guideDisponibili = new ArrayList<>();
		for (Map.Entry<String, Attività> entry : elencoAttività.entrySet()) {
			if(entry.getValue() instanceof Guida) {
				if(entry.getValue().isDisponibile()){
                guideDisponibili.add((Guida)entry.getValue());
                }
			}
        }
		return guideDisponibili;
	}
	
	public void confermaPrenotazioneGuida() throws Exception {
		if(this.clienteCorrente != null && this.attivitàCorrente instanceof Guida) {
			this.attivitàCorrente.prenotaCliente(this.clienteCorrente);
			}
	}
	
	public ArrayList<Guida> aggiornaNumeroGuide(){
		ArrayList<Guida> guideDisponibili = new ArrayList<>();
		for (Map.Entry<String, Attività> entry : elencoAttività.entrySet()) {
			if(entry.getValue() instanceof Guida) {
				if(entry.getValue().isAntecedente()){
                guideDisponibili.add((Guida)entry.getValue());
                }
			}
        }
		return guideDisponibili;
	}
	
	public HashMap<String, Cliente> selezionaGuida(LocalDate data, LocalTime ora) throws Exception{
		this.attivitàCorrente = this.getAttività(data, ora);
		if(this.attivitàCorrente != null) {
			return this.attivitàCorrente.getElencoPrenotatiAttività();
		}else {
			return null;
		}
	}
	
	public void aggiornaGuideInserisciCliente(String codiceFiscale) throws Exception {
		if(this.attivitàCorrente != null) {
			this.clienteCorrente = this.getCliente(codiceFiscale);
		}
	}
	
	public void aggiornaGuideConferma() {
		if(this.clienteCorrente != null) {
			this.clienteCorrente.aggiornaNumeroGuide();
		}
	}
	
	public void addEsameFinale(LocalDate data, LocalTime ora) {
		Attività a = new EsameFinale(data, ora);
		this.elencoAttività.put(a.getCodice(), a);
		System.out.println("Esame finale fissato correttamente per la data: " + a.getData().toString() + " e ora: " + a.getOra().toString());
	}
	
	public ArrayList<EsameFinale> prenotaEsameFinale() {
		ArrayList<EsameFinale> esamiFinaliDisponibili = new ArrayList<>();
		for (Map.Entry<String, Attività> entry : elencoAttività.entrySet()) {
			if(entry.getValue() instanceof EsameFinale) {
				if(entry.getValue().isDisponibile()){
                esamiFinaliDisponibili.add((EsameFinale)entry.getValue());
                }
			}
        }
		return esamiFinaliDisponibili;
	}
	
	public void confermaPrenotazioneEsameFinale() throws Exception {
		if(this.clienteCorrente != null && this.attivitàCorrente instanceof EsameFinale) {
			this.attivitàCorrente.prenotaCliente(this.clienteCorrente);
		}
	}
	
	public ArrayList<EsameFinale> esitiEsameFinalePubblica() {
		ArrayList<EsameFinale> esamiFinaliDisponibili = new ArrayList<>();
		for (Map.Entry<String, Attività> entry : elencoAttività.entrySet()) {
			if(entry.getValue() instanceof EsameFinale) {
				if(entry.getValue().isAntecedente() ){
                esamiFinaliDisponibili.add((EsameFinale)entry.getValue());
                }
			} 
        }
		return esamiFinaliDisponibili;
	}
	
	public HashMap<String, Cliente> esitiEsameFinaleSeleziona(LocalDate data, LocalTime ora) throws Exception{
		this.attivitàCorrente = this.getAttività(data, ora);
		if(this.attivitàCorrente != null) {
			return this.attivitàCorrente.getElencoPrenotatiAttività();
		}else {
			return null;
		}
	}
	
	public void esitoEsameFinaleInserisciCliente(String codiceFiscale) throws Exception {
		if(this.attivitàCorrente != null && this.attivitàCorrente instanceof EsameFinale) {
			EsameFinale a = (EsameFinale)this.attivitàCorrente;
			a.promuoviCliente(codiceFiscale);
		}else {
			//System.out.println("esame finale non selezionato");
			throw new Exception("esame finale non selezionato");
		}
	}
	
	public void esitiEsameFinaleConferma() {
		if(this.attivitàCorrente instanceof EsameFinale) {
			EsameFinale e = (EsameFinale)this.attivitàCorrente;
			e.confermaEsiti();
		}
	}
	
	public void loadArgomenti() {
		Argomento a1 = new Argomento("Segnali di pericolo");
		Argomento a2 = new Argomento("Segnali di divieto");
		Argomento a3 = new Argomento("Segnali di obbligo");
		Argomento a4 = new Argomento("Segnali di precedenza");
		Argomento a5 = new Argomento("Semafori");
		Argomento a6 = new Argomento("Distanza di sicurezza");
		Argomento a7 = new Argomento("Norme di circolazione dei veicoli");
		Argomento a8 = new Argomento("Precedenza negli incroci");
		Argomento a9 = new Argomento("Norme di sorpasso");
		Argomento a10 = new Argomento("Cinture di sicurezza e altri dispositivi");
		this.listaArgomenti.add(a1);
		this.listaArgomenti.add(a2);
		this.listaArgomenti.add(a3);
		this.listaArgomenti.add(a4);
		this.listaArgomenti.add(a5);
		this.listaArgomenti.add(a6);
		this.listaArgomenti.add(a7);
		this.listaArgomenti.add(a8);
		this.listaArgomenti.add(a9);
		this.listaArgomenti.add(a10);
		System.out.println("Caricamento argomenti completato");
	}
	
	public void loadLezioni() {
		Lezione l1 = new Lezione(LocalDate.of(2023, 4, 15), LocalTime.of(10, 00), this.listaArgomenti.get(1));
		Lezione l2 = new Lezione(LocalDate.of(2022, 12, 20), LocalTime.of(11, 00), this.listaArgomenti.get(2));
		Lezione l3 = new Lezione(LocalDate.of(2023, 1, 10), LocalTime.of(18, 00), this.listaArgomenti.get(3));
		this.elencoLezioni.put(l1.getCodice(), l1);
		this.elencoLezioni.put(l2.getCodice(), l2);
		this.elencoLezioni.put(l3.getCodice(), l3);
		System.out.println("Caricamento lezioni completato");
	}
	
	public void loadClienti() {
		Cliente c1 = new Cliente("MV155489", "Mario", "Verdi", Date.valueOf("2000-1-1"), "0951616161", "Mario.Verdi@gmail.com", 
				"via Rossi 25");
		Cliente c2 = new Cliente("SB159546", "Sergio", "Bianchi", Date.valueOf("2000-1-1"), "0951616161", "Sergio.Bianchi@gmail.com", 
				"via Rossi 25");
		Cliente c3 = new Cliente("AV159875", "Alex", "Viola", Date.valueOf("2000-1-1"), "0951616161", "Alex.Viola@gmail.com", 
				"via Rossi 25");
		Cliente c4 = new Cliente("MR457800", "Marta", "Rossi", Date.valueOf("2000-1-1"), "0951616161", "Marta.Rossi@gmail.com", 
				"via Rossi 25");
		c1.setArgomentiSeguiti(this.listaArgomenti);
		c1.setFoglioRosa(true);
		c3.setArgomentiSeguiti(this.listaArgomenti);
		c3.setFoglioRosa(true);
		c3.setNumeroGuide(16);
		c4.setFrequenzaLezioni(90);
		this.listaClienti.put(c1.getCodiceFiscale(), c1); //ha foglio rosa ma 0 guide, può prenotarsi a guide
		this.listaClienti.put(c2.getCodiceFiscale(), c2); //non ha frequentato nessuna lezione
		this.listaClienti.put(c3.getCodiceFiscale(), c3); //ha fatto tutte le guide, può prenotarsi all'esame finale
		this.listaClienti.put(c4.getCodiceFiscale(), c4); //ha seguito quasi tutte le lezioni, può prenotarsi all' esame teorico
		System.out.println("Caricamento clienti completato");
	}
	
	public void loadAttività() {
		Attività t1 = new EsameTeorico(LocalDate.of(2023, 5, 10), LocalTime.of(20, 30));
		Attività t2 = new EsameTeorico(LocalDate.of(2022, 12, 14), LocalTime.of(10, 00));
		Attività t3 = new EsameTeorico(LocalDate.of(2024, 12, 1), LocalTime.of(10, 00));
		Attività f1 = new EsameFinale(LocalDate.of(2023, 4, 12), LocalTime.of(20, 29));
		Attività f2 = new EsameFinale(LocalDate.of(2022, 12, 20), LocalTime.of(20, 29));
		Attività f3 = new EsameFinale(LocalDate.of(2024, 12, 16), LocalTime.of(05, 15));
		Attività g1 = new Guida(LocalDate.of(2023, 4, 10), LocalTime.of(20, 29));
		Attività g2 = new Guida(LocalDate.of(2022, 12, 10), LocalTime.of(20, 29));
		Attività g3 = new Guida(LocalDate.of(2024, 12, 3), LocalTime.of(05, 15));
		this.elencoAttività.put(t1.getCodice(), t1);
		this.elencoAttività.put(t2.getCodice(), t2);
		this.elencoAttività.put(t3.getCodice(), t3);
		this.elencoAttività.put(f1.getCodice(), f1);
		this.elencoAttività.put(f2.getCodice(), f2);
		this.elencoAttività.put(f3.getCodice(), f3);
		this.elencoAttività.put(g1.getCodice(), g1);
		this.elencoAttività.put(g2.getCodice(), g2);
		this.elencoAttività.put(g3.getCodice(), g3);
		System.out.println("Caricamento attività completato");
	}

	public HashMap<String, Lezione> getElencoLezioni() {
		return elencoLezioni;
	}

	public void setLezioneCorrente(Lezione lezioneCorrente) {
		this.lezioneCorrente = lezioneCorrente;
	}	

	public Lezione getLezioneCorrente() {
		return lezioneCorrente;
	}

	public ArrayList<Argomento> getListaArgomenti() {
		return listaArgomenti;
	}

	public HashMap<String, Cliente> getListaClienti() {
		return listaClienti;
	}
	
	public void setClienteCorrente(Cliente clienteCorrente) {
		this.clienteCorrente = clienteCorrente;
	}
	
	public Cliente getClienteCorrente() {
		return clienteCorrente;
	}

	public Attività getAttivitàCorrente() {
		return attivitàCorrente;
	}

	public void setAttivitàCorrente(Attività attivitàCorrente) {
		this.attivitàCorrente = attivitàCorrente;
	}

	public HashMap<String, Attività> getElencoAttività() {
		return elencoAttività;
	}

	public HashMap<String, Cliente> getListaBocciatiDebitori() {
		return listaBocciatiDebitori;
	}
}
