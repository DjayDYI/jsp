package AubergeInn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe GestionClient
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui contient le gestionnaire pour
 * les transactions se rapportant aux clients
 * 
 * </pre>
 */
public class GestionClient
{
    private TableClient client;
    private TableReserver reservation;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionClient(TableClient client, TableReserver reservation) throws IFT287Exception
    {
        this.cx = client.getConnexion();
        if (client.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les instances de client et de reservation n'utilisent pas la même connexion au serveur");
        this.client = client;
        this.reservation = reservation;
    }

    /**
     * Ajout d'un client dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajouterClient(int idClient, String nomClient, String prenomClient, int age)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le client existe déja
            if (client.existe(idClient))
                throw new IFT287Exception("Client existe déjà: " + idClient);
         
            // Vérifie si l'age du client est supérieur à zéro
            if (age < 0)
                throw new IFT287Exception("L'âge doit être supérieur à 0");
            
            // Ajout du client
            client.ajoutClient(idClient,nomClient,prenomClient,age);
            
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
     * Suppression d'un client.
     */
    public void supprimerClient(int idClient)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le client existe
            if (!client.existe(idClient))
                throw new IFT287Exception("Client inexistant : "+ idClient);
            
            // Vérifie si le client a une reservation en cours
            if(reservation.existe(idClient))
               throw new IFT287Exception("Le client : " + idClient + " a une réservation");
            
            // Ajout du client
            client.supprimeClient(idClient);
            
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
     * Affichage d'un client.
     */
    public TupleClient afficherClient(int idClient)
            throws SQLException, IFT287Exception, Exception
    {
        TupleClient tc = new TupleClient();
        
        try
        {
            // Vérifie si le client existe
            if (!client.existe(idClient))
                throw new IFT287Exception("Client inexistant : "+ idClient);
            
            tc = client.getClient(idClient);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
        
        return tc;
    }

    /**
     * Retourne la liste de tout
     * les clients
     */
    public List<TupleClient> getListeClient() throws SQLException, IFT287Exception, Exception
    {
        List<TupleClient> listTC = new ArrayList<TupleClient>(); 
        try
        {
            listTC = client.getListeClients();
            cx.commit();
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
        return listTC;
    }
    
}
