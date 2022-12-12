package AubergeInn;
/**
 * Classe TupleService
 *
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe permet d'instancier un tuple service
 *
 * </pre>
 */
public class TupleService
{
    // Membres
    private int idService;
    private String nomService;
    private int prix;
    
    /**
     * Constructeur 
     */
    public TupleService(){}
    
    /**
    * Constructeur 
    */
    public TupleService(int _idService, String _nom, int _prix)
    {
        this.setIdService(_idService);
        this.setNom(_nom);
        this.setPrix(_prix);
    }
    
    /**
     * Renvoie la valeur de idService 
     */
    public int getIdService()
    {
        return idService;
    }
    
    /**
     * Renvoie la valeur de prix 
     */
    public int getPrix()
    {
        return prix;
    }
    
    /**
     * Renvoie la valeur de nom 
     */
    public String getNom()
    {
        return nomService;
    }
    
    /**
     * Modifie la valeur de idService 
     */
    public void setIdService(int _idService)
    {
        this.idService = _idService;
    }
    
    /**
     * Modifie la valeur de prix 
     */
    public void setPrix(int _prix)
    {
        this.prix = _prix;
    }
    
    /**
     * Modifie la valeur de nom 
     */
    public void setNom(String _nom)
    {
        this.nomService = _nom;
    }   
}
