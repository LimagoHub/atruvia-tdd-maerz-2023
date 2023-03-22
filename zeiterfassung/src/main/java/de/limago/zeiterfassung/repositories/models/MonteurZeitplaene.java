package de.limago.zeiterfassung.repositories.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(MonteurZeitplaeneId.class)
@Table(name="tblmonteure_zeitmodelle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonteurZeitplaene implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 861650823596584756L;
	@Id
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Mitarbeiter mitarbeiter;
	@Id
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Zeitmodell zeitmodell;
	@Id
	private LocalDate beginnGueltigkeit;

}
