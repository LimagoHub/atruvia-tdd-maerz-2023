package de.limago.zeiterfassung.repositories.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tblzeitmodelle")
@NamedQuery(name = "Zeitmodell.findAllZeitmodelle", query = "SELECT distinct z FROM Zeitmodell z ORDER BY z.bezeichnung DESC")
public class Zeitmodell implements Serializable{
	
	
	private static final long serialVersionUID = 4402808315716691690L;

	@Column(length = 36, nullable = false)
	@Size(min=36, max=36, message = "Id muss genau 36 Zeichen lang sein")
	@Id
	@NotEmpty(message = "ID darf nicht leer sein.")
	private String id;

	@Column(nullable = false, unique = true)
	@Size(min = 6, max = 255, message = "Bezeichnung muss mindestens 6 und maximal 255 Buchstaben haben.")
	@NotEmpty
	private String bezeichnung;
	
	//@Version
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@Builder.Default
	@NotNull
	private LocalDateTime lastUpdate = LocalDateTime.now();
	@PrePersist
	public void setCreateTime() {
		lastUpdate = LocalDateTime.now();
	}
	
	@PreUpdate
	public void setUpdateTime() {
		lastUpdate = LocalDateTime.now();
	}

	@Version
	@Builder.Default
	private int version = 0;
	
		
	
	
	@DecimalMax("24.0") @DecimalMin("0.0") @Builder.Default @Column(columnDefinition="Decimal(6,2) default '7.5'")
	private double montag = 7.5;
	@DecimalMax("24.0") @DecimalMin("0.0") @Builder.Default @Column(columnDefinition="Decimal(6,2) default '7.5'")
	private double dienstag = 7.5;
	@DecimalMax("24.0") @DecimalMin("0.0") @Builder.Default @Column(columnDefinition="Decimal(6,2) default '7.5'")
	private double mittwoch = 7.5;
	@DecimalMax("24.0") @DecimalMin("0.0") @Builder.Default @Column(columnDefinition="Decimal(6,2) default '7.5'")
	private double donnerstag = 7.5;
	@DecimalMax("24.0") @DecimalMin("0.0") @Builder.Default @Column(columnDefinition="Decimal(6,2) default '7.0'")
	private double freitag = 7;
	@DecimalMax("24.0") @DecimalMin("0.0") @Builder.Default @Column(columnDefinition="Decimal(6,2) default '0.0'")
	private double samstag = 0;
	@DecimalMax("24.0") @DecimalMin("0.0") @Builder.Default @Column(columnDefinition="Decimal(6,2) default '0.0'")
	private double sonntag = 0;
}
