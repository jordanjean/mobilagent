package jus.aor.RMI.common;

import java.util.List;

public interface _Chaine {
	/**
	 * Restitue la liste des hotels situés dans la localisation.
	 * @param localisation le lieu où l'on recherche des hotels
	 * @return la liste des hotels trouvés
	 */
	public abstract List<Hotel> get(String localisation) ;
}