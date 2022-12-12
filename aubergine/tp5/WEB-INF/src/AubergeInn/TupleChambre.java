package AubergeInn;
/**
 * Classe TupleChambre
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe permet d'instancier un tuple chambre
 *
 * </pre>
 */
public class TupleChambre
{
    // Membre
    private int idChambre;
    private String nomChambre;
    private int prix;
    private String typeLit;
    
    /**
     * Constructeur
     */
    public TupleChambre()
    {}
    
    /**
     * Constructeur
     */
    public TupleChambre(int _idChambre, String _nomChambre, int _prix, String _tLit)
    {
        this.setIdChambre(_idChambre);
        this.setNomChambre(_nomChambre);
        this.setPrix(_prix);
        this.setTypeLit(_tLit);
    }
    
    /**
     * Retourne la valeur de idChambre
     */
    public int getIdChambre()
    {
        return idChambre;
    }
    
    /**
     * Retourne la valeur de nomChambre
     */
    public String getNomChambre()
    {
        return nomChambre;
    }
    
    /**
     * Retourne la valeur de prix
     */
    public int getPrix()
    {
        return prix;
    }
    
    /**
     * Retourne la valeur de type lit
     */
    public String getTypeLit()
    {
        return typeLit;
    }
    
    /**
     * Modifie la valeur de idChambre
     */
    public void setIdChambre(int _idChambre)
    {
        this.idChambre = _idChambre;
    }
    
    /**
     * Modifie la valeur de nomChambre
     */
    public void setNomChambre(String _nomChambre)
    {
        this.nomChambre = _nomChambre;
    }
    
    /**
     * Modifie la valeur de prix
     */
    public void setPrix(int _prix)
    {
        this.prix = _prix;
    }
    
    /**
     * Modifie la valeur de type lit
     */
    public void setTypeLit(String _tLit)
    {
        this.typeLit = _tLit;
    }
    
}
