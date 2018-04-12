package jus.aor.mobilagent.LookForHotel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import jus.aor.mobilagent.hotels.Hotel;
import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel.Numero;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel._Service;

public class LookForHotel extends Agent {
	/** le critère de localisaton choisi */
	private String localisation;
	private int port = 2001;
	private int nbChaines = 1;
	private List<Hotel> hotels = new ArrayList<Hotel>();
	private HashMap<Hotel,Numero> numeros;

	/**
	 * construction d'un agent de type lookForHotel.
	 * @param args
	 *            les arguments n'en comportant qu'un seul qui indique le
	 *            critère de localisation
	 */
	public LookForHotel(String[] args) {
		super();
		if (args.length != 1) {
			System.out.println("Error: please select a valid location");
			System.exit(1);
		}
		System.out.println("Location: " + args[0]);
		localisation = args[0];
		hotels = new ArrayList<Hotel>();
		numeros = new HashMap<>();
	}

	
	 /**
	 * l'action à entreprendre sur les serveurs visités  
	 */
	protected _Action findHotel = new _Action(){

	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;

	    @Override
	    public void execute() {
			_Service<?> service = agentServer.getService("Hotels");
			hotels.addAll((Collection<? extends Hotel>) service.call(localisation));
		
	    }
	    public String toString(){return "LookForHotel getHotel :";}
	 };	
	
	
	/* (non-Javadoc)
	 * @see jus.aor.mobilagent.kernel.Agent#retour()
	 */
		protected _Action findPhone = new _Action(){

			public void execute() {
				_Service<?> service = agentServer.getService("Telephones");
				for(Hotel hotel : hotels) {
					
					Numero num = (Numero) service.call(hotel.name);
					
					numeros.put(hotel,num);
				}
				
			}
			
			public String toString(){return "LookForHotel getNumero:";}
		 };	
	
}