package de.limago.zeiterfassung.repositories.models;

import java.io.Serializable;
import java.time.LocalDate;

public class MonteurZeitplaeneId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3629391051195202123L;

	
	private Mitarbeiter mitarbeiter;
	private Zeitmodell zeitmodell;
	private LocalDate beginnGueltigkeit;
	
	public MonteurZeitplaeneId() {
		
	}

	public MonteurZeitplaeneId(final Mitarbeiter mitarbeiter, final Zeitmodell zeitmodell, final LocalDate beginnGueltigkeit) {
		this.mitarbeiter = mitarbeiter;
		this.zeitmodell = zeitmodell;
		this.beginnGueltigkeit = beginnGueltigkeit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beginnGueltigkeit == null) ? 0 : beginnGueltigkeit.hashCode());
		result = prime * result + ((mitarbeiter == null) ? 0 : mitarbeiter.hashCode());
		result = prime * result + ((zeitmodell == null) ? 0 : zeitmodell.hashCode());
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
		MonteurZeitplaeneId other = (MonteurZeitplaeneId) obj;
		if (beginnGueltigkeit == null) {
			if (other.beginnGueltigkeit != null)
				return false;
		} else if (!beginnGueltigkeit.equals(other.beginnGueltigkeit))
			return false;
		if (mitarbeiter == null) {
			if (other.mitarbeiter != null)
				return false;
		} else if (!mitarbeiter.equals(other.mitarbeiter))
			return false;
		if (zeitmodell == null) {
			if (other.zeitmodell != null)
				return false;
		} else if (!zeitmodell.equals(other.zeitmodell))
			return false;
		return true;
	}

	
	

}
