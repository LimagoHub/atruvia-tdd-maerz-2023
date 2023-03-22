package de.limago.zeiterfassung.services.feiertage;

public enum Bundesland {
		
	BW("Baden-Württemberg")
	,BY("Freistaat Bayern")
	,BE("Berlin")
	,BB("Brandenburg")
	,HB("Freie Hansestadt Bremen")
	,HH("Freie und Hansestadt Hamburg ")
	,HE("Hessen")
	,MV("Mecklenburg-Vorpommern")
	,NI("Niedersachsen")
	,NW("Nordrhein-Westfalen")
	,RP("Rheinland-Pfalz")
	,SL("Saarland")
	,SN("Freistaat Sachsen")
	,ST("Sachsen-Anhalt")
	,SH("Schleswig-Holstein")
	,TH("Freistaat Thüringen");

	private String fullname; 
	
	Bundesland(String fullname) {
		this.fullname = fullname;
	}
	
	public String fullname() {
		return this.fullname;
	}
}
