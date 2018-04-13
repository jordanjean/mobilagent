package jus.aor.mobilagent.hello;

import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel._Action;

/**
 * Classe de test élémentaire pour le bus à agents mobiles
 * @author  Morat
 */
public class Hello extends Agent{

	 /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String feuille_de_route = "";
	/**
	  * construction d'un agent de type hello.
	  * @param args aucun argument n'est requis
	  */
	 public Hello(Object... args) {
		 // ....
	 }
	 /**
	 * l'action à entreprendre sur les serveurs visités  
	 */
	protected _Action doIt = new _Action(){

	    /**
	     * 
	     */
	    private static final long serialVersionUID = -9129644307555501553L;

	    @Override
	    public void execute() {
		feuille_de_route = feuille_de_route.concat(" -> " + serverName);
		System.out.println("Serveur : " + serverName);
	    }
	    
	    @Override
	    public String toString(){
		return "doIt";
	    }
	};
	
	protected _Action retour = new _Action(){

	    /**
	     * 
	     */
	    private static final long serialVersionUID = 8112403583439231794L;

	    @Override
	    public void execute() {
		System.out.println("Route : " + feuille_de_route);
	    }
	    
	    @Override
	    public String toString(){
		return "retour";
	    }
	};

	@Override
	/*
	 * (non-Javadoc)
	 * @see jus.aor.mobilagent.kernel.Agent#retour()
	 */
	protected _Action retour(){
	    return this.retour;
	}
}
