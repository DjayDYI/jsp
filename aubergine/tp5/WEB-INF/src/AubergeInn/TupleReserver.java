package AubergeInn;

import java.sql.Date;
/**
 * Classe TupleReserver
 *
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe permet d'instancier un tuple reserver
 *
 * </pre>
 */
public class TupleReserver
{
    // Membres
    private int idChambre;
    private int idClient;
    private Date dateDebut;
    private Date dateFin;

    /**
     * Constructeur
     */
    public TupleReserver(){}
    
    /**
     * Constructeur
     */
    public TupleReserver(int _idChambre, int _idClient, Date _dDebut, Date _dFin)
    {
        this.setIdChambre(_idChambre);
        this.setIdClient(_idClient);
        this.setDateDebut(_dDebut);
        this.setDateFin(_dFin);
    }
    
    /**
     * Retourne la valeur de idClient 
     */
    public int getIdClient()
    {
        return idClient;
    }
    
    /**
     * Retourne la valeur de idChambre 
     */
    public int getIdChambre()
    {
        return idChambre;
    }
    
    /**
     * Retourne la valeur de dateDebut 
     */
    public Date getDateDebut()
    {
        return dateDebut;
    }
    
    /**
     * Retourne la valeur de dateFin 
     */
    public Date getDateFin()
    {
        return dateFin;
    }

    /**
     * Modifie la valeur de idChambre 
     */
    public void setIdChambre(int _idChambre)
    {
        this.idChambre = _idChambre;
    }
    
    /**
     * Modifie la valeur de idClient 
     */
    public void setIdClient(int _idClient)
    {
        this.idClient = _idClient;
    }
    
    /**
     * Modifie la valeur de dateDebut 
     */
    public void setDateDebut(Date _dDebut)
    {
        this.dateDebut = _dDebut;
    }

    /**
     * Modifie la valeur de dateFin 
     */
    public void setDateFin(Date _dFin)
    {
        this.dateFin = _dFin;
    }
}
