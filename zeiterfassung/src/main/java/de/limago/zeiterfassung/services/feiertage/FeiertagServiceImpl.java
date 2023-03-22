package de.limago.zeiterfassung.services.feiertage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class FeiertagServiceImpl {

	
	
	public List<Feiertag> berechneFeiertageFuerJahrUndBundesland(final int jahr, final Bundesland bundesland) {
		final List<Feiertag> feiertage = new ArrayList<>();
		addiereFesteFeiertage(feiertage, jahr);
		addiereBeweglicheFeiertage(feiertage,jahr);
		return feiertage.stream().filter(f->f.getBundeslaender().contains(bundesland)).sorted((f1, f2) -> f1.getDatum().compareTo(f2.getDatum())).collect(Collectors.toList());
	}

	private void addiereFesteFeiertage(final List<Feiertag> feiertage, final int jahr) {
		feiertage.add(Feiertag.builder().bezeichnung("Neujahr")						.datum(LocalDate.of(jahr, 1,  1)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Heilige drei Könige")			.datum(LocalDate.of(jahr, 1,  6)).bundeslaender(Arrays.asList(Bundesland.BW,Bundesland.BY,Bundesland.ST)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Frauentag")					.datum(LocalDate.of(jahr, 3,  8)).bundeslaender(Arrays.asList(Bundesland.BE)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Tag der Arbeit")				.datum(LocalDate.of(jahr, 5,  1)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Mariä Himmelfahrt")			.datum(LocalDate.of(jahr, 8,  15)).bundeslaender(Arrays.asList(Bundesland.SL)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Weltkindertag")				.datum(LocalDate.of(jahr, 9,  20)).bundeslaender(Arrays.asList(Bundesland.TH)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Tag der deutschen Einheit")	.datum(LocalDate.of(jahr, 10, 3)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Reformationstag")				.datum(LocalDate.of(jahr, 10, 31)).bundeslaender(Arrays.asList(Bundesland.BB,Bundesland.HB,Bundesland.HH,Bundesland.MV,Bundesland.NI,Bundesland.SN,Bundesland.ST,Bundesland.SH,Bundesland.TH)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Allerheiligen")				.datum(LocalDate.of(jahr, 11, 1)).bundeslaender(Arrays.asList(Bundesland.BW,Bundesland.BY,Bundesland.NW,Bundesland.RP,Bundesland.SL)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Erster Weihnachtstag")		.datum(LocalDate.of(jahr, 12, 25)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Zweiter Weihnachtstag")		.datum(LocalDate.of(jahr, 12, 26)).build());
	}

	
	private void addiereBeweglicheFeiertage(final List<Feiertag> feiertage, final int jahr) {
		
		final LocalDate ostern = berechneOstern(jahr);
		feiertage.add(Feiertag.builder().bezeichnung("Karfreitag")		.datum(ostern.minus(2,ChronoUnit.DAYS)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Ostersonntag")	.datum(ostern).build());
		feiertage.add(Feiertag.builder().bezeichnung("Ostermontag")		.datum(ostern.plus(1,ChronoUnit.DAYS)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Himmelfahrt")		.datum(ostern.plus(39,ChronoUnit.DAYS)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Pfingstsonntag")	.datum(ostern.plus(49,ChronoUnit.DAYS)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Pfingstmontag")	.datum(ostern.plus(50,ChronoUnit.DAYS)).build());
		feiertage.add(Feiertag.builder().bezeichnung("Fronleichnam")	.datum(ostern.plus(60,ChronoUnit.DAYS)).build());

		feiertage.add( berechneBussUndBetTag(jahr) );
	}

	
	private Feiertag berechneBussUndBetTag(final int jahr) /* Mittwoch vor dem 23. November */ {
		final LocalDate november23 = LocalDate.of(jahr, 11, 23);
		final int tage = november23.getDayOfWeek().getValue() + 4;
		final LocalDate bussUndBetTag = november23.minus(tage > 7 ? tage - 7 : tage,ChronoUnit.DAYS);
		return Feiertag.builder().bezeichnung("Buß- und Bettag").datum(bussUndBetTag).bundeslaender(Arrays.asList(Bundesland.SN)).build();
	}
	
	
	
	private LocalDate berechneOstern(final int jahr) {
		return osterFormelNachHaroldSpencerJones(jahr);
	}

	/*
	 * siehe: https://de.wikipedia.org/wiki/Spencers_Osterformel
	 */
	private LocalDate osterFormelNachHaroldSpencerJones(final int jahr) {
		final int a = jahr % 19;
		final int b = jahr / 100;
		final int c = jahr % 100;
		final int d = b / 4;
		final int e = b % 4;
		final int f = (b + 8) / 25;
		final int g = (b - f + 1) / 3;
		final int h = (19 * a + b - d - g + 15) % 30;
		final int i = c / 4;
		final int k = c % 4;
		final int l = (32 + 2 * e + 2 * i - h - k) % 7;
		final int m = (a + 11 * h + 22 * l) / 451;
		final int n = (h + l - 7 * m + 114) / 31;
		final int p = (h + l - 7 * m + 114) % 31;

		
		
		return LocalDate.of(jahr, n, p + 1);
	}

}
