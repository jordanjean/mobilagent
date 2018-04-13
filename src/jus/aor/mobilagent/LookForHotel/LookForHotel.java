package jus.aor.mobilagent.LookForHotel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import jus.aor.mobilagent.hotels.Hotel;
import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel.Numero;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel._Service;

public class LookForHotel extends Agent {
    /**
    * 
    */
    private static final long serialVersionUID = 8584911057064016328L;
    /** le critère de localisaton choisi */
    private String localisation;
    private List<Hotel> hotels = new ArrayList<Hotel>();
    private HashMap<Hotel, Numero> numeros;

    public LookForHotel(Object... args) {
	super();
	if (args.length != 1) {
	    System.out.println("Entrer une localisation en argument");
	    System.exit(1);
	}
	System.out.println("Location ciblée : " + args[0]);
	localisation = (String) args[0];
	hotels = new ArrayList<Hotel>();
	numeros = new HashMap<Hotel, Numero>();
    }

    protected _Action getHotel = new _Action() {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() {
	    _Service<?> service = agentServer.getService("hotels");
	    hotels.addAll((Collection<? extends Hotel>) service.call(localisation));

	}

	public String toString() {
	    return "getHotel";
	}
    };

    /*
     * (non-Javadoc)
     * 
     * @see jus.aor.mobilagent.kernel.Agent#retour()
     */
    protected _Action getPhoneNumber = new _Action() {

	/**
	* 
	*/
	private static final long serialVersionUID = 4820823648024054381L;

	public void execute() {
	    _Service<?> service = agentServer.getService("annuaire");
	    for (Hotel hotel : hotels) {
		Numero num = (Numero) service.call(hotel.name);
		numeros.put(hotel, num);
	    }

	}

	public String toString() {
	    return "getPhoneNumber";
	}
    };
    
    protected _Action retour = new _Action() {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4421014474224233096L;
	
	public void execute() {
	    int cpt = 0;
	    System.out.println("Résultats de la recherche (10 premiers résultats) : ");
	    for (Hotel hotel : hotels) {
		if(cpt < 10 && hotel.localisation.equals(localisation)){
		    cpt++;
		    System.out.println("Hotel : " +  hotel.name + " Tel : " + numeros.get(hotel) );
		}
	    }
	    System.out.println("...");

	}

	public String toString() {
	    return "Affichage résultats";
	}
    };

    @Override
    /*
     * (non-Javadoc)
     * 
     * @see jus.aor.mobilagent.kernel.Agent#retour()
     */
    protected _Action retour() {
	return retour;
    }

}