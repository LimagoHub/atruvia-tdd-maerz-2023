package de.limago.zeiterfassung.repositories.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblmitarbeiter")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = "Mitarbeiter.findAllMitarbeiterByNameDescending", query = "SELECT distinct m FROM Mitarbeiter m left join fetch m.rollen where m.hersteller = false ORDER BY m.username DESC")
@NamedQuery(name = "Mitarbeiter.findUserWithRolesByUsername", query = "SELECT distinct m FROM Mitarbeiter m left join fetch m.rollen where m.username=:username")
@NamedQuery(name = "Mitarbeiter.findMitarbeitertoGivenZeitmodell", query = "SELECT distinct m FROM Mitarbeiter m left join fetch m.zeitplaene zp left join fetch zp.zeitmodell where zp.zeitmodell=:zeitmodell")

public class Mitarbeiter implements UserDetails {

	
	private static final long serialVersionUID = -7180903052203616099L;

	@Id	@Column(length = 50)
	@Pattern(regexp="^[a-zA-Z0-9ÄÖÜäüö]{6,50}$",message="username braucht min. 6 Stellen, nur Buchstaben und Ziffern sind erlaubt.")  
	private String username;

	@JsonIgnore
	//@Pattern(regexp="^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,255})$",message="Password braucht min. 6 Stellen, muss min. einen Gross- und einen Kleinbuchstaben, sowie eine Ziffer und eines dieser Zeichen '@#$%' enthalten.")  
	@Size(min = 6, max=255)
	@NotEmpty(message = "Password darf nicht leer sein.")
	private String password;

	@Column(length = 50)
	@Size(min = 2, max=50)
	@NotEmpty 
	private String fullname;

	@NotNull(message = "Beginn Arbeitsverhältnis darf nicht null sein.")
	//@Column(columnDefinition = "DATE DEFAULT ('1900-01-01')")
	private LocalDate beginnArbeitverhaeltnis = LocalDate.parse("1900-01-01");

	@NotNull(message = "Ende Arbeitsverhältnis darf nicht null sein.")
	//@Column(columnDefinition = "DATE DEFAULT ('2200-01-01')")
	private LocalDate endeArbeitverhaeltnis = LocalDate.of(2200, 1, 1);

	//@Version
	//@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime lastUpdate;
	
	@PrePersist
	public void setCreateTime() {
		lastUpdate = LocalDateTime.now();
	}
	
	@PreUpdate
	public void setUpdateTime() {
		lastUpdate = LocalDateTime.now();
	}
	
	@Version
	private int version = 0;

	
	@ElementCollection() @Enumerated(EnumType.STRING) @Column(name = "rolle", length = 10)
	@JoinTable(name = "tblmitarbeiterrollen", foreignKey = @ForeignKey(name = "fk_mitarbeiter_rollen"), joinColumns = @JoinColumn(name = "username"))
	private Set<Rolle> rollen = new HashSet<>();
	
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "mitarbeiter")
	private List<MonteurZeitplaene> zeitplaene = new ArrayList<>();

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRollen().stream().map(rolle -> new SimpleGrantedAuthority("ROLE_" + rolle.name()))
				.collect(Collectors.toList());
	}
 



	@Column(columnDefinition = "boolean default true")
	private boolean hersteller = false;

	@Column(columnDefinition = "boolean default true")
	private boolean enabled = true;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Set<Rolle> getRollen() {
		return rollen;
	}

	public void setRollen(Set<Rolle> rollen) {
		this.rollen = rollen;
	}

	public boolean isHersteller() {
		return hersteller;
	}

	public void setHersteller(boolean hersteller) {
		this.hersteller = hersteller;
	}

	@Override
	public boolean isAccountNonExpired() {
		return (! ( getBeginnArbeitverhaeltnis().isAfter(LocalDate.now()) ||  getEndeArbeitverhaeltnis().isBefore(LocalDate.now()))); 
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	


	public List<MonteurZeitplaene> getZeitplaene() {
		return zeitplaene;
	}

	public void setZeitplaene(List<MonteurZeitplaene> zeitplaene) {
		this.zeitplaene = zeitplaene;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mitarbeiter other = (Mitarbeiter) obj;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", fullname=" + fullname + "]";
	}

	public LocalDate getBeginnArbeitverhaeltnis() {
		return beginnArbeitverhaeltnis;
	}

	public void setBeginnArbeitverhaeltnis(LocalDate beginnArbeitverhaeltnis) {
		this.beginnArbeitverhaeltnis = beginnArbeitverhaeltnis;
	}

	public LocalDate getEndeArbeitverhaeltnis() {
		return endeArbeitverhaeltnis;
	}

	public void setEndeArbeitverhaeltnis(LocalDate endeArbeitverhaeltnis) {
		this.endeArbeitverhaeltnis = endeArbeitverhaeltnis;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public void addMonteurZeitmodell(Zeitmodell modell) {
		zeitplaene.add(new MonteurZeitplaene(this, modell, LocalDate.now()));
	}
	
	public void addMonteurZeitmodell(Zeitmodell modell, LocalDate beginnGueltigkeit) {
		zeitplaene.add(new MonteurZeitplaene(this, modell, beginnGueltigkeit));
	}
	

}