package AubergeInn;
/**
 * Classe TupleClient
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe permet d'instancier un tuple client
 *
 * </pre>
 */
public class TupleClient
{
    // Membres
    private int idClient;
    private String nom;
    private String prenom;
    private int age;
    
    /**
     * Constructeur
     */
    public TupleClient()
    {}
    
    /**
     * Constructeur
     */
    public TupleClient(int _idClient, String _nom, String _prenom, int _age)
    {
        this.setIdClient(_idClient);
        this.setAge(_age);
        this.setNom(_nom);
        this.setPrenom(_prenom);
    }  
    
    /**
     * Retourne la valeur de idClient
     */
    public int getIdClient()
    {
        return idClient;
    }
    
    /**
     * Retourne la valeur de age
     */
    public int getAge()
    {
        return age;
    }
    
    /**
     * Retourne la valeur de nom
     */
    public String getNom()
    {
        return nom;
    }
    
    /**
     * Retourne la valeur de prenom
     */
    public String getPrenom()
    {
        return prenom;
    }
        
    /**
     * Modifie la valeur de idClient
     */
    public void setIdClient(int _idClient)
    {
        this.idClient = _idClient;
    }
    
    /**
     * Modifie la valeur de age
     */
    public void setAge(int _age)
    {
        this.age = _age;
    }
    
    /**
     * Modifie la valeur de nom
     */
    public void setNom(String _nom)
    {
        this.nom = _nom;
    }
    
    /**
     * Modifie la valeur de prenom
     */
    public void setPrenom(String _prenom)
    {
        this.prenom = _prenom;
    }
    
}
