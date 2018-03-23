package jus.aor.RMI.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _Annuaire extends Remote{
	/**
	 * restitue le numéro de téléphone de l'abonné
	 * @param abonne l'abonné
	 * @return le numéro de télephone de l'abonné
	 * @throws RemoteException 
	 */
	public Numero get(String abonne) throws RemoteException;
}
