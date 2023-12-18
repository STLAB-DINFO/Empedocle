package it.unifi.ing.stlab.empedocle.model.health.drugs;

public enum UpdateInfo {
	
	AAPT, // Concedibile SSN su autorizzazione ASL e copia Piano Terapeutico allegata
	AIR, // Assistenza integrativa regionale
	AP, // Verifica della appropriatezza prescrittiva
	AW, // Prima dell'assunzione sospendere terapia con ACE-i almeno 36h prima. Non associare ad ACE-i o ARB.
	BO, // 
	CA, // Rimborsabile per i dodicenni maschi e femmine
	CCD, // Non in vendita in farmacia, ma in canali commerciali diversi.
	CM, // Se in esenzione T16 erogazione gratuita su ricetta SSN
	CQ, // Se in esenzione T16 erogazione gratuita su ricetta SSN cartacea
	CS, // Necessaria esibizione di documento di identit� per la dispensazione SOP
	CT, // Continuita terapeutica Ospedale-Territorio (Det. AIFA 29/10/2004 All. 2)
	CTPT, // Continuita terapeutica Ospedale-Territorio. Prescrivibile con diagnosi e Piano Terapeutico (Det. AIFA 29/10/2004 All. 2)
	D0, // Distribuzione Per Conto (Piano Terapeutico On-Line)
	DA2, // Riportare i dati acquirente (nome, cognome, documento di identita) su ricettario privato. Conservare per due anni (L. 38/10). Comunicazione Ordine Provinciale dei Medici (DM 31/3/10)
	DF, // Distribuzione Per Conto - Concedibile in farmacia in assenza della sigla DP nella casella apposita della ricetta SSN
	DPFU, // Distribuzione Per Conto - Concedibile in farmacia SSN in presenza di dicitura URGENTE su ricetta
	DPU1, // Distribuzione Per Conto - Concedibile SSN una confezione in presenza di dicitura URGENTE su ricetta
	EA, // Piano Terapeutico valido 6 mesi da parte di allergologo, internista, geriatra, pneumologo
	EB, // Prescrivibile SSN su PT AIFA diversificato a seconda delle indicazioni terapeutiche
	ED, // Piano terapeutico di specialista centri di neurologia ASL
	EH, // PHT per la profilassi della TVP e continuazione terapia ospedaliera dopo intervento di chirurgia maggiore ortopedica o generale.
	EI, // Piano Terapeutico per una o piu indicazioni
	EM, // Distribuzione diretta solo per l'indicazione di riduzione del rischio edema maculare post intervento cataratta in diabetici. Altrimenti non concedibile
	F7, // Solo per esenti G01 e G02 distribuzione diretta.
	IL, // Se in esenzione T16 erogazione gratuita
	IP, // Se esente T16, erogazione protesica senza preventivo
	IT, // Alcune indicazioni terapeutiche non sono rimborsate dal SSN
	MA, // Farmaco soggetto a monitoraggio addizionale. Segnalare eventi avversi (Art 23 Regolamento (CE) 726/2004)
	MD, // Non rimborsabile se mancante in DPC.
	MI, // Medicinale sottoposto a monitoraggio intensivo: segnalare ogni sospetto effetto indesiderato (DL n. 95 8/4/2003)
	O2, // Farmaco ex Osp2
	O3, // Medicinale analogo a quelli gia classificati OSP2
	OW, // Distribuzione diretta se diagnosi di demenza. Prescrivibile dai centri individuati dalla regione
	P000DA, // Distribuzione da ASL e Distribuzione per Conto non ammesse
	P000NG, // Fornitura diretta alle ASL: non in vendita in farmacia
	P000PX, // Prescrizione su diagnosi e PT secondo accordi regionali
	PA, // Precrivibile con PT AIFA dedicato
	PE, // Prescrivibile su Piano Terapeutico AIFA specifico per Ticagrelor (Allegato 1 Det.AIFA 25/11/2011 GU285 7/12/2011)
	PHT, // Distribuzione Prontuario Ospedale-Territorio (PHT) per conto delle ASL
	PL, // PT AIFA dedicato (validita 6 mesi)
	PM, // Prescrivibile dopo il sesto mese di età su PT AIFA dedicato.
	PQ, // Prescrivibile SSN su Piano Terapeutico AIFA dedicato
	PR, // Prontuari terapeutico regionale
	PT, // Prescrivibile su Diagnosi e Piano Terapeutico
	PTN6, // Prescrivibile su Piano Terapeutico, valido max 6 mesi, redatto da neurologo, neuropsichiatra, geriatra.
	PTNI, // Bambini (di eta superiore agli 8 anni) ed adolescenti: prescrivibile su Piano Terapeutico di neuropsichiatra infantile.
	PTSD, // Prescrivibile su Piano Terapeutico AIFA specifico per Dronedarone
	PTSE, // Prescrivibile su Piano Terapeutico AIFA specifico per Eritropoietina (ex Nota 12)
	PTSF, // Prescrivibile su Piano Terapeutico AIFA specifico per fattori di crescita granulocitari (ex Note 30 e 30bis).
	PTSI, // Prescrivibile su Piano Terapeutico AIFA specifico per Interferoni (ex Nota 32)
	PTSL, // Prescrivibile su Piano Terapeutico AIFA specifico per Lamivudina (ex Nota 32 bis)
	PTSN, // Prescrivibile su Piano Terapeutico AIFA specifico per Incretine ed Inibitori DPP-4 nel trattamento del diabete Mellito di Tipo 2
	PTTD, // Prescrivibile su Piano Terapeutico di struttura sanitaria pubblica o privata autorizzata, per il trattamento di disassuefazione da tossicodipendenza
	PU, // Piano Terapeutico valido 12 mesi da parte di Internista, geriatra, reumatologo, endocrinologo di struttura pubblicaAssistenza Integrativa Regionale
	PV, // Prescrivibile SSN su PT specialistico (endocrinologo, andrologo, urologo, neurologo) limitatamente alle indicazioni AIFA
	QS, // Quote di spettanza sul prezzo di vendita al pubblico al netto IVA: per le aziende farmaceutiche 58,65 per cento, per i grossisti 6,65 per cento e per i farmacisti 26,7 per cento (D.L. n.39 del 28/4/09 e D.L. n. 78 del 31/5/10).
	RE, // Prescrizione soggetta a piano di trattamento con diagnosi valido 6 mesi
	RI, // Prescrivibile SSN solo per indicazioni di cui al Piano Terapeutico dedicato
	RP, // Prescrivibile SSN a bambini > 5 settimane in cura presso centri pediatrici con diagnosi ospedaliera
	SG, // Prescrivibile SSN con compilazione di scheda di appropriatezza prescrittiva specifica
	SH, // In DP se presente un piano terapeutico on-line antecedente il 1 febbraio 2017
	SI, // A carico del cittadino nei bambini e adolescenti fino a 17 anni
	SM, // Obbligatoria la compilazione di scheda di monitoraggio AIFA per le prescrizioni SSN
	ST, // Farmaco stupefacente trasportabile in Italia e all estero senza necessità di certificazione medica ( D.M. 16 Nov 2007, All. 2, Art. 4)
	TK, // Se in esenzione T16 differenza di prezzo non dovuta su ricetta SSN cartacea
	UD, // Le indicazioni prevedono uso anche nella donna
	UO, // Le indicazioni prevedono uso anche nell uomo
	UP, // Solo uso professionale
	VD, // Vendita destinata esclusivamente ai dentisti.
	VE, // Vendibile anche al di fuori di farmacie e parafarmacie
	VF, // Vietata la prescrizione e la preparazione magistrale galenica
	VG, // Vietata la prescrizione o la preparazione galenica a scopo dimagrante
	VH, // Vietate le preparazioni galeniche a base dei principi attivi di cui al DM 4/8/2015 in associazione o piu prearazioni singole allo stesso paziente
	VM, // Non utilizzare al di sotto dei 12 anni
	VN, // Principi attivi vietati nelle preparazioni galeniche magistrali
	VR, // Non in cani di peso inferiore a 6 Kg
	VS, // Preparazione magistrale a scopo dimagrante: obbligo per la farmacia di invio ricetta alla ASL (art.5 L.94/98)
	VV,	// Prescrivibile dal veterinario solo per animali sotto i 6 Kg per uso in deroga
	DPPT,
	P000PE,
	PHTP,
	P000DF,
	PTSV,
	PTSR
}
