package it.unifi.ing.stlab.empedocle.model.health.drugs;

public enum PrescriptionType {
	
	SOP,	//Ricetta non richiesta (Farmaco Etico Senza Obbligo di Prescrizione)
	OTC,	//Ricetta non richiesta (Farmaco Da banco)
	RRL,	//Ricetta Ripetibile Limitativa
	RR,		//Ricetta Ripetibile
	RR1,	//Ricetta Ripetibile - Validita 30 gg
	RNRL,	//Ricetta Non Ripetibile Limitativa
	RNR, 	//Ricetta Non Ripetibile
	RNR1,	//Ricetta Non Ripetibile - Vale 7 gg
	RMR,	//Ricetta Ministeriale a Ricalco
	OSPL,	//Uso Ospedaliero Specialistico
	USPL,	//Uso Specialistico
	OSP,	//Distribuzione Ospedaliera
	P0000N
}
