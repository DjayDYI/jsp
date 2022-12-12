package AubergeInn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe GestionService
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui contient le gestionnaire pour
 * les transactions se rapportant a Service
 * 
 * </pre>
 */
public class GestionService
{
    private TableService service;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionService(TableService service) throws IFT287Exception
    {
        this.cx = service.getConnexion();
        this.service = service;
    }

    /**
     * Ajout d'un service dans la base de données.
     * S'il existe déjà, une exception est levée.
     * Si le prix est inférieur à zéro, une exception est levée.
     */
    public void ajouterCommodite(int idService,String nomService, int prix)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le service existe déja
            if (service.existe(idService))
                throw new IFT287Exception("Service existe déjà: " + idService);
            
            // Vérifie si le prix est inférieur à zéro
            if (prix <0)
                throw new IFT287Exception("Le prix doit etre supérieur a 0$");

            // Ajout du service dans la table des services
            service.ajoutService(idService,nomService, prix);
            
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
     * Suppression d'un service dans la base de données.
     * S'il existe déjà, une exception est levée.
     * Si le prix est inférieur à zéro, une exception est levée.
     */
    public void supprimerCommodite(int idService)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le service existe déja
            if (!service.existe(idService))
                throw new IFT287Exception("Service inexistant :" + idService);
            
            // Ajout du service dans la table des services
            service.supprimeService(idService);
            
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
     * Retourne un service demandé
     */
    public TupleService getService(int idService)
            throws SQLException, IFT287Exception, Exception
    {
        TupleService ts;
        try
        {
            // Vérifie si le service existe déja
            if (!service.existe(idService))
                throw new IFT287Exception("Service inexistant: " + idService);
            
            // Retourne le service
            ts = service.getService(idService);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
        
        return ts;
    }
    
    /**
     * Retourne la liste de tous les services
     */
    public List<TupleService> getListeService() throws SQLException, IFT287Exception, Exception
    {
        List<TupleService> listTC = new ArrayList<TupleService>(); 
        try
        {
            // Retourne la liste des services
            listTC = service.getListeServices();
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
