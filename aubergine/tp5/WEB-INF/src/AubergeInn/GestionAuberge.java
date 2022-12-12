package AubergeInn;

import java.sql.SQLException;
/**
 * Classe GestionAuberge
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui contient les gestionnaires de
 * transactions 
 * 
 * </pre>
 */

public class GestionAuberge
{
    private Connexion cx;
    
    private TableChambre chambre;
    private TableClient client;
    private TableReserver reservation;
    private TableService service;
    
    private GestionChambre gestionChambre;
    private GestionClient gestionClient;
    private GestionReserver gestionReservation;
    private GestionService gestionService;
    
    /**
     *  Constructeur
     */
    public GestionAuberge()
    {}

    /**
     * Constructeur
     * Ouvre une connexion avec la BD relationnelle et alloue les gestionnaires
     * de transactions et de tables.
     */
    public GestionAuberge(String serveur, String bd, String userId, String motDePasse) throws IFT287Exception, SQLException, Exception
    {
        this.cx = new Connexion(serveur, bd, userId, motDePasse);
        this.init(this.cx);
    }

    /**
     * Constructeur
     * Ouvre une connexion avec la BD relationnelle et alloue les gestionnaires
     * de transactions et de tables.
     */
    public void init(Connexion _cx)
            throws IFT287Exception, SQLException
    {
        // Allocation des objets pour le traitement des transactions
        cx = _cx;
        chambre = new TableChambre(cx);
        client = new TableClient(cx);
        reservation = new TableReserver(cx);
        service = new TableService(cx);
        
        setGestionChambre(new GestionChambre(chambre, reservation, service));
        setGestionClient(new GestionClient(client, reservation));
        setGestionReserver(new GestionReserver(reservation,chambre,client));
        setGestionService(new GestionService(service));
    }

    /**
     * Fermetrue de la connection
     */
    public void fermer() throws SQLException
    {
        // Fermeture de la connexion
        cx.fermer();
    }

    /**
     * Retourne le Gestionnaire de Chambre
     */
    public GestionChambre getGestionChambre()
    {
        return gestionChambre;
    }

    /**
     * Modifie le Gestionnaire de Chambre
     */
    private void setGestionChambre(GestionChambre gestionChambre)
    {
        this.gestionChambre = gestionChambre;
    }

    /**
     * Retourne le Gestionnaire de Client
     */
    public GestionClient getGestionClient()
    {
        return gestionClient;
    }
    
    /**
     * Modifie le Gestionnaire de Client
     */
    private void setGestionClient(GestionClient gestionClient)
    {
        this.gestionClient = gestionClient;
    }

    /**
     * Retourne le Gestionnaire de reservation
     */
    public GestionReserver getGestionReservation()
    {
        return gestionReservation;
    }
    
    /**
     * Modofie le Gestionnaire de reservation
     */
    private void setGestionReserver(GestionReserver gestionReservation)
    {
        this.gestionReservation = gestionReservation;
    }

    /**
     * Retourne le Gestionnaire de service
     */
    public GestionService getGestionService()
    {
        return gestionService;
    }

    /**
     * Modifie le Gestionnaire de service
     */
    private void setGestionService(GestionService gestionService)
    {
        this.gestionService = gestionService;
    }
    
    public Connexion getConnexion()
    {
        return cx;
    }
    
}
