package de.limago.zeiterfassung.services.feiertage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feiertag {

	private String bezeichnung;
	private LocalDate datum;
	@Builder.Default private String bemerkung="";
	@Builder.Default private double faktor = 1.0;
	@Builder.Default private List<Bundesland> bundeslaender = Arrays.asList(Bundesland.values());
	
	
}
