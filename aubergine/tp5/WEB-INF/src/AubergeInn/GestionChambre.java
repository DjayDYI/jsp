package AubergeInn;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe GestionChambre
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui contient le gestionnaire pour
 * les transactions se rapportant aux chambres
 * 
 * </pre>
 */
public class GestionChambre
{
    private TableChambre chambre;
    private TableReserver reservation;
    private TableService service;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionChambre(TableChambre chambre, TableReserver reservation, TableService service) throws IFT287Exception
    {
        this.cx = chambre.getConnexion();
        if (chambre.getConnexion() != reservation.getConnexion() || chambre.getConnexion() != service.getConnexion())
            throw new IFT287Exception("Les instances de chambre et de reservation n'utilisent pas la même connexion au serveur");
        this.chambre = chambre;
        this.reservation = reservation;
        this.service = service;
    }

    /**
     * Ajout d'une chambre dans la base de données.
     * Si elle existe déjà, une exception est levée.
     * Si son prix est négatif, une exception est levée.
     */
    public void ajouterChambre(int idChambre, String nomChambre, String typeLit, int prix)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le chambre existe déja
            if (chambre.existe(idChambre))
                throw new IFT287Exception("Chambre existe déjà: " + idChambre);

            // Verifie Si le prix est superieur a zero
            if(prix < 0)
                throw new IFT287Exception("Le prix doit être supérieur à 0$");
            
            // Ajout de la chambre
            chambre.ajoutChambre(idChambre, nomChambre, typeLit, prix);
            
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
     * Suppression d'une chambre.
     * Si la chambre n'existe pas, une exception est levée
     * Si la chambre a deja une reservation en cours, ou future on leve une exception
     */
    public void supprimerChambre(int idChambre)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le chambre n'existe pas
            if (!chambre.existe(idChambre))
                throw new IFT287Exception("Chambre inexistante : "+ idChambre);
            
            // Verifie si la chambre n'a pas de reservation future
            if(reservation.getReservation(idChambre)!= null && reservation.getReservation(idChambre).getDateDebut().after(new Date(System.currentTimeMillis())))
                throw new IFT287Exception("La chambre " + idChambre + " a une reservation future. ");

            // Verifie si la chambre n'est pas présentement reservée
            if(reservation.getReservation(idChambre)!= null && reservation.getReservation(idChambre).getDateFin().after(new Date(System.currentTimeMillis())))
                throw new IFT287Exception("La chambre " + idChambre + " est présentement réservé. ");
            
            // Supprimer les reservations antérieus
            reservation.supprimerReservation(idChambre);
            
            // Supprimer la chambre
            chambre.supprimeChambre(idChambre);
            
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
     * Inclure un service dans une chambre.
     * Si la chambre n'existe pas une exception est levée
     * Si le service n'existe pas une exception est levée
     * Si la chambre possede deja le service, une exception est levée
     * 
     */
    public void inclureCommodite(int idChambre, int idService)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la chambre existe
            if (!chambre.existe(idChambre))
                throw new IFT287Exception("Chambre inexistante : "+ idChambre);
            
            // Vérifie si le service existe
            if (!service.existe(idService))
                throw new IFT287Exception("Service inexistante : "+ idService);
            
            // Vérifie si la chambre possède déjà le service
            if(chambre.possedeService(idChambre, idService))
                throw new IFT287Exception("La chambre : "+ idChambre +" possede deja le service : " + idService);
            
            // Ajout du livre dans la table des livres
            chambre.inclureService(idChambre, idService);
            
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
     * Enlever un service dans une chambre.
     * Si la chambre n'existe pas une exception est levée
     * Si le service n'existe pas une exception est levée
     * Si la chambre possede deja le service, une exception est levée
     * 
     */
    public void enleverCommodite(int idChambre, int idService)            
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la chambre n'existe pas
            if (!chambre.existe(idChambre))
                throw new IFT287Exception("Chambre inexistante : "+ idChambre);
            
            // Vérifie si le service n'existe pas
            if(!service.existe(idService))
                throw new IFT287Exception("Service inexistante : "+ idService);
            
            // Vérifie si la chambre possède déjà le service
            if(!chambre.possedeService(idChambre, idService))
                throw new IFT287Exception("La chambre : "+ idChambre +" ne possede pas le service : " + idService);
            
            // Ajout du livre dans la table des livres
            chambre.enleverService(idChambre, idService);
            
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
     * Affichage d'une chambre.
     * S'il elle n'existe pas une exception est levé
     */
    public TupleChambre afficherChambre(int idChambre)            
            throws SQLException, IFT287Exception, Exception
    {
        TupleChambre tc = new TupleChambre();
        
        try
        {
            // Vérifie si la chambre n'existe pas
            if (!chambre.existe(idChambre))
                throw new IFT287Exception("Chambre inexistante : "+ idChambre);
            
            // Ajout du livre dans la table des livres
            tc = chambre.getChambre(idChambre);
            
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
     * Retourne une liste de toutes
     * les chambres libres
     */
    public ArrayList<TupleChambre> afficherChambresLibres() throws SQLException, IFT287Exception, Exception
    {
        ArrayList<TupleChambre> listChambreLibre = new ArrayList<TupleChambre>();
                
        try
        {
            // Pour toute les chambres
            for(int i=0; i < chambre.getListeChambres().size(); i++)
            {   
                List<TupleReserver> r = new ArrayList<TupleReserver>();
                r.add(reservation.getReservation(chambre.getListeChambres().get(i).getIdChambre()));
       
                // Pour toutes les reservations concernant cette chambre
                for(int j = 0; j < r.size(); j++)
                {
                    TupleReserver res = reservation.getReservation(chambre.getListeChambres().get(i).getIdChambre());
                    
                    // S'il y a une reservation
                    if(res != null)
                    {
                        Date curDate = new Date(System.currentTimeMillis());
                        
                        // Si la chambres n'est pas reserver
                        if(!(curDate.after(res.getDateDebut()) && curDate.before(res.getDateFin())))
                        {
                            // La chambre est libre
                            listChambreLibre.add(chambre.getListeChambres().get(i));
                        }
                    }
                    else
                    {
                        // La chambre est n'est pas libre
                        listChambreLibre.add(chambre.getListeChambres().get(i));
                    }
                }
            }
            
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
        
        return listChambreLibre;
    }
    
    /**
     * Retourne la liste des services pour
     * une chambre donnée
     */
    public ArrayList<TupleService> getListeService(int idChambre)
            throws SQLException, IFT287Exception, Exception
    {
        ArrayList<TupleService> listReservationService = new ArrayList<TupleService>();

        try
        {
            // Vérifie si la chambre existe
           if(!chambre.existe(idChambre))
                throw new IFT287Exception("Chambre inexistant:" + idChambre);

           // Pour tout les services de la chambre
           for(int i=0; i < service.getListeService(idChambre).size(); i++)
           {
                // Ajout du livre dans la table des livres
                listReservationService.add(service.getService(service.getListeService(idChambre).get(i)));
            }
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
        
        return listReservationService;
    }
    
    /**
     * Retourne la liste de toutes les chambres
     */
    public List<TupleChambre> getListeChambre() throws SQLException, IFT287Exception, Exception
    {
        List<TupleChambre> listTC = new ArrayList<TupleChambre>(); 
        try
        {
            listTC = chambre.getListeChambres();
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
