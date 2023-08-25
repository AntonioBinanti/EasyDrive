package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class Menù {

	public static void main(String[] args) {
		EasyDrive easyDrive = EasyDrive.getInstance();
		Scanner tastiera = new Scanner(System.in);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateTimeFormatter localDateFormat =  DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		int scelta = 0;
		do {
			System.out.println("\n---------------EASYDRIVE: MENU'---------------");
			System.out.println("\nSeleziona un'operazione: \n1)Gestisci un cliente \n2)Gestisci la programmazione di un esame teorico \n3)Prenota un esame teorico "
					+ "\n4)Prenota un esame finale \n5)Gestisci programmazione lezioni \n6)Aggiorna frequenza clienti \n7)Gestisci programmazione guide \n8)Prenota guida"
					+ "\n9)Aggiorna numero guide sostenute \n10)Pubblica esiti esame teorico \n11)Pubblica esiti esame finale \n12)Gestisci programmazione di un esame finale "
					+ "\n0)Chiudi programma");
			try {
                scelta = tastiera.nextInt();
            } catch (NumberFormatException n) {
                scelta = 0;
            } catch (Exception e) {
                System.out.println(e);
            }
			
			switch(scelta) {
				case 0:{
					System.out.println("Uscita...");
					break;
				}
				case 1:{ //Gestisci un cliente
					int scelta2 = 0;
					do {
						System.out.println("\nGestisci cliente: \n1)Inserisci cliente \n2)Cerca cliente \n3)Rimuovi cliente \n0)Torna al menù");
						try {
			                scelta2 = tastiera.nextInt();
			            } catch (NumberFormatException n) {
			                scelta2 = 0;
			            } catch (Exception e) {
			                System.out.println(e);
			                System.out.println("Chiusura programma...");
			                scelta = 0;
			            }
						
						switch(scelta2) {
							case 0:
								break;
							case 1: //Inserisci cliente 
								boolean corretto = true;
								String codiceFiscale, nome, cognome, numeroTelefono, email, indirizzo;
								Date dataNascita = null;
								
								System.out.println("Inserisci il codice fiscale del cliente");
								codiceFiscale = tastiera.next();
								System.out.println("Inserisci il nome del cliente");
								nome = tastiera.next();
								System.out.println("Inserisci il cognome del cliente");
								cognome = tastiera.next();
								do {
									corretto = true;
									System.out.println("Inserisci la data di nascita del cliente (yyyy/mm/dd)");
									String data = tastiera.next();
									try {
										dataNascita = dateFormat.parse(data);
									} catch (ParseException e) {
										//e.printStackTrace();
										System.out.println("Data non valida, riprova");
										corretto = false;
									}
								}while(!corretto);
								do {
									try {
										System.out.println("Inserisci il numero di telefono del cliente");
										numeroTelefono = tastiera.next();
										if(!numeroTelefono.matches("[0-9]+")) {
											corretto = false ;
											System.out.println("Numero di telefono non valido, riprova");
										}else corretto = true;
									}catch(PatternSyntaxException  e) {
										System.out.println("Numero di telefono non valido, riprova");
										corretto = false;
									}
								}while(!corretto);
								System.out.println("Inserisci l'email del cliente");
								email = tastiera.next();
								System.out.println("Inserisci l'indirizzo del cliente");
								indirizzo = tastiera.next();
								easyDrive.addCliente(codiceFiscale, nome, cognome, dataNascita, cognome, email, indirizzo);
								System.out.println("Il cliente " + nome + " " + cognome + " è stato inserito corretammente");
								break;
								
							case 2: //Cerca cliente
								Cliente c;
								System.out.println("Inserisci il codice fiscale del cliente da cercare (Premere '0' per tornare indietro)");
								codiceFiscale = tastiera.next();
								if(codiceFiscale.equals("0")) {
									break;
								}else {
									try {
										c = easyDrive.getCliente(codiceFiscale);
										System.out.println(c.toString());
									}catch(Exception e) {
										System.out.println(e.getMessage());
									}
									break;
								}
							case 3: //Rimuovi cliente
								System.out.println("Inserisci il codice fiscale del cliente da rimuovere (Premere '0' per tornare indietro)");
								codiceFiscale = tastiera.next();
								if(codiceFiscale.equals("0")){
									break;
								}else {
									try {
										easyDrive.removeCliente(codiceFiscale);
									} catch (Exception e) {
										e.printStackTrace();
									}
									break;
								}
						}
					}while(scelta2 != 0);
					break;
				}
				case 2:{ //Gestisci un esame teorico
					int scelta2 = 0;
					do {
						System.out.println("\nGestisci esame teorico: \n1)Inserisci esame teorico \n2)Cerca esame teorico \n3)Rimuovi esame teorico \n0)Torna al menù");
						try {
			                scelta2 = tastiera.nextInt();
			            } catch (NumberFormatException n) {
			                scelta2 = 0;
			            } catch (Exception e) {
			                System.out.println(e);
			                System.out.println("Chiusura programma...");
			                scelta = 0;
			            }
						
						switch(scelta2) {
						case 0:
							break;
						case 1: {//Inserisci esame teorico 
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data dell'esame teorico (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									//e.printStackTrace();
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora dell'esame teorico (HH:mm)  (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									//e.printStackTrace();
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							easyDrive.addEsameTeorico(localData, localOra);
							break;
						}							
						case 2: { //Cerca esame teorico
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							EsameTeorico esame;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data dell'esame teorico da cercare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora dell'esame teorico da cercare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								if(easyDrive.getAttività(localData, localOra) instanceof EsameTeorico) {
									esame = (EsameTeorico)easyDrive.getAttività(localData, localOra);
									System.out.println(esame.toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						case 3:{ //Rimuovi esame teorico
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							EsameTeorico esame;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data dell'esame teorico da eliminare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora dell'esame teorico da eliminare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								easyDrive.removeAttività(localData, localOra);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						}
					}while(scelta2 != 0);
					break;
				}
				case 3:{ //Prenota un esame teorico
					ArrayList<EsameTeorico> esamiTeoriciDisponibili;
					try {
						System.out.println("\nEsami teorici disponibili:");
						esamiTeoriciDisponibili = easyDrive.prenotaEsameTeorico();
						for (EsameTeorico e : esamiTeoriciDisponibili) {
							System.out.println(e.toString());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						break;
					}
					boolean corretto = true;
					boolean exit = false;
					String data, ora;
					LocalDate localData = null;
					LocalTime localOra = null;
					do {
						corretto = true;
						exit = false;
						System.out.println("\nInserisci la data dell'esame teorico a cui il cliente vuole prenotarsi (yyyy/MM/dd) (Premere 0 per tornare indietro)");
						data = tastiera.next();
						if(data.equals("0")) {
							exit = true;
							break;
						}
						try {
							localData = LocalDate.parse(data, localDateFormat);
						} catch (DateTimeParseException e) {
							System.out.println("Data non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci l'ora dell'esame teorico a cui il cliente vuole prenotarsi (HH:mm) (Premere 0 per tornare indietro)");
						ora = tastiera.next();
						if(ora.equals("0")) {
							exit = true;
							break;
						}
						try {
							localOra = LocalTime.parse(ora);
						} catch (DateTimeParseException  e) {
							System.out.println("Ora non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					try {
						easyDrive.selezionaAttività(localData, localOra);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Inserisci il codice fiscale del cliente da prenotare (Premere '0' per annullare l'operazione)");
					String codiceFiscale;
					codiceFiscale = tastiera.next();
					if(codiceFiscale.equals("0")) break;
					try {
						easyDrive.inserisciCliente(codiceFiscale);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Premere:\n1)Per confermare l'operazione\n0)Per annullare l'operazione");
					int scelta2;
					try {
		                scelta2 = tastiera.nextInt();
		            } catch (NumberFormatException n) {
		                scelta2 = 0;
		            } catch (Exception e) {
		                System.out.println(e);
		                break;
		            }
					switch(scelta2) {
					case 0:{
						break;
					}
					case 1:{
						try {
							easyDrive.confermaPrenotazioneEsameTeorico();
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
					break;
					}
				}
				case 4:{ //Prenota un esame finale
					 ArrayList<EsameFinale> esamiFinaliDisponibili;
					try {
						System.out.println("\nEsami finali disponibili:");
						esamiFinaliDisponibili = easyDrive.prenotaEsameFinale();
						for (EsameFinale e : esamiFinaliDisponibili) {
							System.out.println(e.toString());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						break;
					}
					boolean corretto = true;
					boolean exit = false;
					String data, ora;
					LocalDate localData = null;
					LocalTime localOra = null;
					do {
						corretto = true;
						exit = false;
						System.out.println("\nInserisci la data dell'esame finale a cui il cliente vuole prenotarsi (yyyy/MM/dd) (Premere 0 per tornare indietro)");
						data = tastiera.next();
						if(data.equals("0")) {
							exit = true;
							break;
						}
						try {
							localData = LocalDate.parse(data, localDateFormat);
						} catch (DateTimeParseException e) {
							System.out.println("Data non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci l'ora dell'esame finale a cui il cliente vuole prenotarsi (HH:mm) (Premere 0 per tornare indietro)");
						ora = tastiera.next();
						if(ora.equals("0")) {
							exit = true;
							break;
						}
						try {
							localOra = LocalTime.parse(ora);
						} catch (DateTimeParseException  e) {
							System.out.println("Ora non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					try {
						easyDrive.selezionaAttività(localData, localOra);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Inserisci il codice fiscale del cliente da prenotare (Premere '0' per annullare l'operazione)");
					String codiceFiscale;
					codiceFiscale = tastiera.next();
					if(codiceFiscale.equals("0")) break;
					try {
						easyDrive.inserisciCliente(codiceFiscale);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Premere:\n1)Per confermare l'operazione\n0)Per annullare l'operazione");
					int scelta2;
					try {
		                scelta2 = tastiera.nextInt();
		            } catch (NumberFormatException n) {
		                scelta2 = 0;
		            } catch (Exception e) {
		                System.out.println(e);
		                break;
		            }
					switch(scelta2) {
					case 0:{
						break;
					}
					case 1:{
						try {
							easyDrive.confermaPrenotazioneEsameFinale();
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
					break;
					}
				}
				case 5:{ //Gestisci programmazione lezioni
					int scelta2 = 0;
					do {
						System.out.println("\nGestisci lezione: \n1)Inserisci lezione \n2)Cerca lezione \n3)Rimuovi lezione \n0)Torna al menù");
						try {
			                scelta2 = tastiera.nextInt();
			            } catch (NumberFormatException n) {
			                scelta2 = 0;
			            } catch (Exception e) {
			                System.out.println(e);
			                System.out.println("Chiusura programma...");
			                scelta = 0;
			            }
						
						switch(scelta2) {
						case 0:
							break;
						case 1: {//Inserisci lezione
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data della lezione (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									//e.printStackTrace();
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora della lezione (HH:mm)  (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									//e.printStackTrace();
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							int scelta3 = 0;
							Argomento a = null;
							System.out.println("Seleziona un numero per scegliere l'argomento da trattare nella lezione:");
							System.out.println("1)Segnali di pericolo\n2)Segnali di divieto\n3)Segnali di obbligo\n4)Segnali di precedenza"
									+ "\n5)Semafori\n6)Distanza di sicurezza\n7)Norme di circolazione dei veicoli\n8)Precedenza negli incroci"
									+ "\n9)Norme di sorpasso\n10)Cinture di sicurezza e altri dispositivi\n0)Annulla operazione");
							try {
				                scelta3 = tastiera.nextInt();
				            } catch (NumberFormatException n) {
				                scelta3 = 0;
				            } catch (Exception e) {
				                System.out.println(e);
				                System.out.println("Chiusura programma...");
				                scelta = 0;
				            }
							switch(scelta3) {
							case 0:{
								break;
							}
							case 1:{
								a = easyDrive.getListaArgomenti().get(0);
								break;
							}
							case 2:{
								a = easyDrive.getListaArgomenti().get(1);
								break;
							}
							case 3:{
								a = easyDrive.getListaArgomenti().get(2);
								break;
							}
							case 4:{
								a = easyDrive.getListaArgomenti().get(3);
								break;
							}
							case 5:{
								a = easyDrive.getListaArgomenti().get(4);
								break;
							}
							case 6:{
								a = easyDrive.getListaArgomenti().get(5);
								break;
							}
							case 7:{
								a = easyDrive.getListaArgomenti().get(6);
								break;
							}
							case 8:{
								a = easyDrive.getListaArgomenti().get(7);
								break;
							}
							case 9:{
								a = easyDrive.getListaArgomenti().get(8);
								break;
							}
							case 10:{
								a = easyDrive.getListaArgomenti().get(9);
								break;
							}
							}
							easyDrive.addLezione(localData, localOra, a);
							break;
						}							
						case 2: { //Cerca lezione
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							Lezione l;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data della lezione da cercare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora della lezione da cercare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								l = easyDrive.getLezione(localData, localOra);
								System.out.println(l.toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						case 3:{ //Rimuovi lezione
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data della lezione da eliminare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora della lezione da eliminare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								easyDrive.removeLezione(localData, localOra);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						}
					}while(scelta2 != 0);
					break;
				}
				case 6:{ //Aggiorna frequenza clienti
					boolean corretto = true;
					boolean exit = false;
					boolean fine = false;
					String data, ora, cliente;
					LocalDate localData = null;
					LocalTime localOra = null;
					Lezione l;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci la data della lezione della quale si vogliono aggiornare le frequenze (yyyy/MM/dd) (Premere 0 per tornare indietro)");
						data = tastiera.next();
						if(data.equals("0")) {
							exit = true;
							break;
						}
						try {
							localData = LocalDate.parse(data, localDateFormat);
						} catch (DateTimeParseException e) {
							System.out.println("Data non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci l'ora della lezione della quale si vogliono aggiornare le frequenze (HH:mm) (Premere 0 per tornare indietro)");
						ora = tastiera.next();
						if(ora.equals("0")) {
							exit = true;
							break;
						}
						try {
							localOra = LocalTime.parse(ora);
						} catch (DateTimeParseException  e) {
							System.out.println("Ora non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					easyDrive.aggiornaFrequenzaClienti(localData, localOra);
					System.out.println("Inserire i codici fiscali dei clienti presenti alla lezione: (Digitare '1' per indicare di aver finito)");
					do {
						cliente = tastiera.next();
						if(cliente.equals("1")) {
							fine = true;
							break;
						}
						try {
							easyDrive.inserisciClienteFrequentante(cliente);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							easyDrive.confermaInserimento();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}while(fine == false);
					break;
				}
				case 7:{ //Gestisci programmazione guide
					int scelta2 = 0;
					do {
						System.out.println("\nGestisci guide: \n1)Inserisci guida \n2)Cerca guida \n3)Rimuovi guida \n0)Torna al menù");
						try {
			                scelta2 = tastiera.nextInt();
			            } catch (NumberFormatException n) {
			                scelta2 = 0;
			            } catch (Exception e) {
			                System.out.println(e);
			                System.out.println("Chiusura programma...");
			                scelta = 0;
			            }
						
						switch(scelta2) {
						case 0:
							break;
						case 1: {//Inserisci guida 
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data della guida (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									//e.printStackTrace();
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora della guida (HH:mm)  (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									//e.printStackTrace();
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							easyDrive.addGuida(localData, localOra);
							break;
						}							
						case 2: { //Cerca guida
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							Guida g;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data della guida da cercare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora della guida da cercare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								if(easyDrive.getAttività(localData, localOra) instanceof Guida) {
									g = (Guida)easyDrive.getAttività(localData, localOra);
									System.out.println(g.toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						case 3:{ //Rimuovi guida
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							EsameTeorico esame;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data della guida da eliminare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora della guida da eliminare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								easyDrive.removeAttività(localData, localOra);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						}
					}while(scelta2 != 0);
					break;
				}
				case 8:{ //Prenota guida
					ArrayList<Guida> guideDisponibili;
					try {
						System.out.println("\nGuide disponibili:");
						guideDisponibili = easyDrive.prenotaGuida();
						for (Guida g : guideDisponibili) {
							System.out.println(g.toString());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						break;
					}
					boolean corretto = true;
					boolean exit = false;
					String data, ora;
					LocalDate localData = null;
					LocalTime localOra = null;
					do {
						corretto = true;
						exit = false;
						System.out.println("\nInserisci la data della guida alla quale il cliente vuole prenotarsi (yyyy/MM/dd) (Premere 0 per tornare indietro)");
						data = tastiera.next();
						if(data.equals("0")) {
							exit = true;
							break;
						}
						try {
							localData = LocalDate.parse(data, localDateFormat);
						} catch (DateTimeParseException e) {
							System.out.println("Data non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci l'ora della guida alla quale il cliente vuole prenotarsi (HH:mm) (Premere 0 per tornare indietro)");
						ora = tastiera.next();
						if(ora.equals("0")) {
							exit = true;
							break;
						}
						try {
							localOra = LocalTime.parse(ora);
						} catch (DateTimeParseException  e) {
							System.out.println("Ora non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					try {
						easyDrive.selezionaAttività(localData, localOra);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Inserisci il codice fiscale del cliente da prenotare (Premere '0' per annullare l'operazione)");
					String codiceFiscale;
					codiceFiscale = tastiera.next();
					if(codiceFiscale.equals("0")) break;
					try {
						easyDrive.inserisciCliente(codiceFiscale);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Premere:\n1)Per confermare l'operazione\n0)Per annullare l'operazione");
					int scelta2;
					try {
		                scelta2 = tastiera.nextInt();
		            } catch (NumberFormatException n) {
		                scelta2 = 0;
		            } catch (Exception e) {
		                System.out.println(e);
		                break;
		            }
					switch(scelta2) {
					case 0:{
						break;
					}
					case 1:{
						try {
							easyDrive.confermaPrenotazioneGuida();
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
					break;
					}
				
				}
				case 9:{ //Aggiorna numero guide sostenute
					ArrayList<Guida> guideDisponibili;
					HashMap<String,Cliente> prenotatiAttività;
					try {
						System.out.println("\nGuide disponibili:");
						guideDisponibili = easyDrive.aggiornaNumeroGuide();
						for (Guida g : guideDisponibili) {
							System.out.println(g.toString());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						break;
					}
					boolean corretto = true;
					boolean exit = false;
					String data, ora;
					LocalDate localData = null;
					LocalTime localOra = null;
					do {
						corretto = true;
						exit = false;
						System.out.println("\nInserisci la data della guida da aggiornare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
						data = tastiera.next();
						if(data.equals("0")) {
							exit = true;
							break;
						}
						try {
							localData = LocalDate.parse(data, localDateFormat);
						} catch (DateTimeParseException e) {
							System.out.println("Data non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci l'ora della guida da aggiornare (HH:mm) (Premere 0 per tornare indietro)");
						ora = tastiera.next();
						if(ora.equals("0")) {
							exit = true;
							break;
						}
						try {
							localOra = LocalTime.parse(ora);
						} catch (DateTimeParseException  e) {
							System.out.println("Ora non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					System.out.println("Elenco prenotati alla guida selezionata:");
					try {
						prenotatiAttività = easyDrive.selezionaGuida(localData, localOra);
						for (Map.Entry<String, Cliente> entry : prenotatiAttività.entrySet()) {
							System.out.println(entry.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Inserire i codici fiscali dei clienti che hanno partecipato alla guida: (Digitare '1' per indicare di aver finito)");
					String cliente;
					boolean fine = false;
					do {
						cliente = tastiera.next();
						if(cliente.equals("1")) {
							easyDrive.aggiornaGuideConferma();
							fine = true;
							break;
						}
						try {
							easyDrive.aggiornaGuideInserisciCliente(cliente);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							easyDrive.aggiornaGuideConferma();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}while(fine == false);
					break;
				}
				case 10:{ //Pubblica esiti esame teorico
					ArrayList<EsameTeorico> EsamiTeoriciDisponibili;
					HashMap<String,Cliente> prenotatiAttività;
					try {
						System.out.println("\nEsami teorici disponibili:");
						EsamiTeoriciDisponibili = easyDrive.esitiEsameTeoricoPubblica();
						for (EsameTeorico e : EsamiTeoriciDisponibili) {
							System.out.println(e.toString());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						break;
					}
					boolean corretto = true;
					boolean exit = false;
					String data, ora;
					LocalDate localData = null;
					LocalTime localOra = null;
					do {
						corretto = true;
						exit = false;
						System.out.println("\nInserisci la data dell'esame teorico del quale si vogliono pubblicare gli esiti (yyyy/MM/dd) (Premere 0 per tornare indietro)");
						data = tastiera.next();
						if(data.equals("0")) {
							exit = true;
							break;
						}
						try {
							localData = LocalDate.parse(data, localDateFormat);
						} catch (DateTimeParseException e) {
							System.out.println("Data non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci l'ora dell'esame teorico del quale si vogliono pubblicare gli esiti (HH:mm) (Premere 0 per tornare indietro)");
						ora = tastiera.next();
						if(ora.equals("0")) {
							exit = true;
							break;
						}
						try {
							localOra = LocalTime.parse(ora);
						} catch (DateTimeParseException  e) {
							System.out.println("Ora non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					System.out.println("Elenco prenotati all'esame teorico selezionato:");
					try {
						prenotatiAttività = easyDrive.esitiEsameTeoricoSeleziona(localData, localOra);
						for (Map.Entry<String, Cliente> entry : prenotatiAttività.entrySet()) {
							System.out.println(entry.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Inserire i codici fiscali dei clienti che hanno superato l'esame teorico: (Digitare '1' per indicare di aver finito)");
					String cliente;
					boolean fine = false;
					do {
						cliente = tastiera.next();
						if(cliente.equals("1")) {
							easyDrive.esitiEsameTeoricoConferma();
							fine = true;
							break;
						}
						try {
							easyDrive.esitoEsameTeoricoInserisciCliente(cliente);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}while(fine == false);
					break;
				}
				case 11:{  //Pubblica esiti esame finale
					ArrayList<EsameFinale> EsamiFinaliDisponibili;
					HashMap<String,Cliente> prenotatiAttività;
					try {
						System.out.println("\nEsami finali disponibili:");
						EsamiFinaliDisponibili = easyDrive.esitiEsameFinalePubblica();
						for (EsameFinale e : EsamiFinaliDisponibili) {
							System.out.println(e.toString());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						break;
					}
					boolean corretto = true;
					boolean exit = false;
					String data, ora;
					LocalDate localData = null;
					LocalTime localOra = null;
					do {
						corretto = true;
						exit = false;
						System.out.println("\nInserisci la data dell'esame finale del quale si vogliono pubblicare gli esiti (yyyy/MM/dd) (Premere 0 per tornare indietro)");
						data = tastiera.next();
						if(data.equals("0")) {
							exit = true;
							break;
						}
						try {
							localData = LocalDate.parse(data, localDateFormat);
						} catch (DateTimeParseException e) {
							System.out.println("Data non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					do {
						corretto = true;
						exit = false;
						System.out.println("Inserisci l'ora dell'esame finale del quale si vogliono pubblicare gli esiti (HH:mm) (Premere 0 per tornare indietro)");
						ora = tastiera.next();
						if(ora.equals("0")) {
							exit = true;
							break;
						}
						try {
							localOra = LocalTime.parse(ora);
						} catch (DateTimeParseException  e) {
							System.out.println("Ora non valida, riprova");
							corretto = false;
						}
					}while(!corretto);
					if(exit == true) break;
					System.out.println("Elenco prenotati all'esame finale selezionato:");
					try {
						prenotatiAttività = easyDrive.esitiEsameFinaleSeleziona(localData, localOra);
						for (Map.Entry<String, Cliente> entry : prenotatiAttività.entrySet()) {
							System.out.println(entry.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
					System.out.println("Inserire i codici fiscali dei clienti che hanno superato l'esame finale: (Digitare '1' per indicare di aver finito)");
					String cliente;
					boolean fine = false;
					do {
						cliente = tastiera.next();
						if(cliente.equals("1")) {
							easyDrive.esitiEsameFinaleConferma();
							fine = true;
							break;
						}
						try {
							easyDrive.esitoEsameFinaleInserisciCliente(cliente);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}while(fine == false);
					break;
				}
				case 12:{ //Gestisci un esame finale
					int scelta2 = 0;
					do {
						System.out.println("\nGestisci esame finale: \n1)Inserisci esame finale \n2)Cerca esame finale \n3)Rimuovi esame finale \n0)Torna al menù");
						try {
			                scelta2 = tastiera.nextInt();
			            } catch (NumberFormatException n) {
			                scelta2 = 0;
			            } catch (Exception e) {
			                System.out.println(e);
			                System.out.println("Chiusura programma...");
			                scelta = 0;
			            }
						
						switch(scelta2) {
						case 0:
							break;
						case 1: {//Inserisci esame finale 
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data dell'esame finale (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									//e.printStackTrace();
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora dell'esame finale (HH:mm)  (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									//e.printStackTrace();
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							easyDrive.addEsameFinale(localData, localOra);
							break;
						}							
						case 2: { //Cerca esame teorico
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							EsameFinale esame;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data dell'esame finale da cercare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora dell'esame finale da cercare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								if(easyDrive.getAttività(localData, localOra) instanceof EsameFinale) {
									esame = (EsameFinale)easyDrive.getAttività(localData, localOra);
									System.out.println(esame.toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						case 3:{ //Rimuovi esame finale
							boolean corretto = true;
							boolean exit = false;
							String data, ora;
							LocalDate localData = null;
							LocalTime localOra = null;
							EsameTeorico esame;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci la data dell'esame finale da eliminare (yyyy/MM/dd) (Premere 0 per tornare indietro)");
								data = tastiera.next();
								if(data.equals("0")) {
									exit = true;
									break;
								}
								try {
									localData = LocalDate.parse(data, localDateFormat);
								} catch (DateTimeParseException e) {
									System.out.println("Data non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							do {
								corretto = true;
								exit = false;
								System.out.println("Inserisci l'ora dell'esame finale da eliminare (HH:mm) (Premere 0 per tornare indietro)");
								ora = tastiera.next();
								if(ora.equals("0")) {
									exit = true;
									break;
								}
								try {
									localOra = LocalTime.parse(ora);
								} catch (DateTimeParseException  e) {
									System.out.println("Ora non valida, riprova");
									corretto = false;
								}
							}while(!corretto);
							if(exit == true) break;
							try {
								easyDrive.removeAttività(localData, localOra);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
						}
					}while(scelta2 != 0);
					break;
				}
			}
		}while (scelta != 0);
	}

}
