package AubergeInn;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Classe GestionReserver
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui contient le gestionnaire pour
 * les transactions se rapportant aux reservations
 * 
 * </pre>
 */
public class GestionReserver
{
    private TableReserver reservation;
    private TableChambre chambre;
    private TableClient client;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionReserver(TableReserver reservation, TableChambre chambre, TableClient client) throws IFT287Exception
    {
        this.cx = client.getConnexion();
        if (reservation.getConnexion() != chambre.getConnexion() || reservation.getConnexion() != client.getConnexion())
            throw new IFT287Exception("Les instances de client et de reservation n'utilisent pas la même connexion au serveur");
        this.reservation = reservation;
        this.chambre = chambre;
        this.client = client;
    }

    /**
     * Retourne la liste des reservations pour un client
     */
    public ArrayList<TupleReserver> getReservationClient(int idClient)
            throws SQLException, IFT287Exception, Exception
    {
        ArrayList<TupleReserver> listReservationClient = new ArrayList<TupleReserver>();

        try
        {
            // Si le client n'existe pas, une excpetion est levé
            if(!client.existe(idClient))
                throw new IFT287Exception("Client inexistant:" + idClient);
            
            // Pour toutes les reservations
            for(int i=0; i < reservation.getListeReservationClient(idClient).size(); i++)
            {            
                // Ajout du livre dans la table des livres
                listReservationClient.add(reservation.getListeReservationClient(idClient).get(i));
            }
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
        
        return listReservationClient;
    }

    
    /**
     * Reservation d'une chambre
     */
    public void Reserver(int idChambre, int idClient, Date dateDebut, Date dateFin)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Si la chambre n'existe pas, une exception est levé
            if(!chambre.existe(idChambre))
                throw new IFT287Exception("Chambre inexistante : " + idChambre);
            
            // Si le client n'existe pas, une exception est levé
            if(!client.existe(idClient))
                throw new IFT287Exception("Client inexistant : " + idClient);
            
            // Si la chambre est déjuà reservé dans les dates demandées
            ArrayList<TupleReserver> res =  reservation.getListeReservation(idChambre);
            for (int i = 0; i < res.size(); i++)
            {
                if(res.get(i) != null)
                {    
                    if(!((dateDebut.before(res.get(i).getDateDebut()) && dateFin.before(res.get(i).getDateDebut())) || (dateFin.after(res.get(i).getDateFin()) && dateDebut.after(res.get(i).getDateFin()))))
                        throw new IFT287Exception("La chambre est déja reservé pour les dates demandé");     
                }
            }
            
            // Si les dates de reservations sont incoherentes (date fin avant date début)
            if(dateFin.before(dateDebut))
                throw new IFT287Exception("La date de fin doit être supérieur à la date de début");

            
            /** ----------------------------------------------------------------------------------------------------
             *                  CONTRAINTES TEMP REEL
             * Ces validations sont fonctionelles mais en commentaire 
             * par souci de compatibilité avec les fichiers test
             * -----------------------------------------------------------------------------------------------------
            
            // Si la date de reservation est avant aujourd'hui
            if(dateDebut.before(new Date(System.currentTimeMillis())))
                throw new IFT287Exception("Réservation ne peuvent commencer à des dates antérieurs à aujourd'hui");
            
            // Si la date de reservation se termine avant aujourd'hui
            if(dateFin.before(new Date(System.currentTimeMillis())))
                throw new IFT287Exception("Réservation ne peuvent se terminer à des dates antérieurs à aujourd'hui");

             * ------------------------------------------------------------------------------------------------------
             */
            
            // Ajout du livre dans la table des livres
            reservation.ajoutReservation(idChambre, idClient, dateDebut, dateFin);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    
    /**
     * Annuler un reservation pour un client seulement
     * si elle n'est pas passé
     */
    public void annulerReservation(int idClient, int idChambre, Date dateDebut)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Si la chambre n'existe pas, une exception est levé
            if(!chambre.existe(idChambre))
                throw new IFT287Exception("Chambre inexistante : " + idChambre);
            
            // Si le client n'existe pas, une exception est levé
            if(!client.existe(idClient))
                throw new IFT287Exception("Client inexistant : " + idClient);
            
            // Si la date de reservation est avant aujourd'hui
            if(dateDebut.before(new Date(System.currentTimeMillis())))
                throw new IFT287Exception("La reservation est passé et ne peut pas etre annuler");
            
            // Ajout du livre dans la table des livres
            reservation.annulerReservation(idClient,idChambre,dateDebut);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
  
}
