package AubergeInn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
/**
 * Classe TableChambre
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui permet d'exploiter
 * la base de données pour les requêtes SQL se rapportant aux chambres
 *
 * </pre>
 */
public class TableChambre
{
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtInsertService;
    private PreparedStatement stmtDeleteService;
    private PreparedStatement stmtExisteService;
    private Connexion cx;
    private PreparedStatement stmtListeChambres;

    /**
     * Creation d'une instance. Des énoncés SQL pour chaque requête sont
     * précompilés.
     */
    public TableChambre(Connexion cx) throws SQLException
    {
        this.cx = cx;
        
        stmtExiste = cx.getConnection().prepareStatement("select idChambre, nomChambre, prix, typeLit from Chambre where idChambre = ?");   
        stmtInsert = cx.getConnection().prepareStatement("insert into Chambre (idChambre, nomChambre, typeLit, prix) values (?,?,?,?)");
        stmtDelete = cx.getConnection().prepareStatement("delete from Chambre where idChambre = ?");
        stmtInsertService = cx.getConnection().prepareStatement("insert into Possede (idChambre, idService) values (?,?)");
        stmtDeleteService = cx.getConnection().prepareStatement("delete from Possede where idChambre = ? and idService = ?");
        stmtExisteService = cx.getConnection().prepareStatement("select idChambre, idService from Possede where idChambre = ? and idService = ?");
        stmtListeChambres = cx.getConnection().prepareStatement("select idChambre, nomChambre, prix, typeLit from chambre");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si une chambre existe.
     */
    public boolean existe(int idChambre) throws SQLException
    {
        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean chambreExiste = rset.next();
        rset.close();
        return chambreExiste;
    }
    
    /**
     * Lecture d'une chambre.
     */
    public TupleChambre getChambre(int idChambre) throws SQLException
    {
        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleChambre tupleChambre = new TupleChambre();
            tupleChambre.setIdChambre(idChambre);
            tupleChambre.setNomChambre(rset.getString(2));
            tupleChambre.setPrix(rset.getInt(3));
            tupleChambre.setTypeLit(rset.getString(4));
            rset.close();
            return tupleChambre;
        }
        else
            return null;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données.
     */
    public void ajoutChambre(int idChambre, String nomChambre, String typeLit, int prix) throws SQLException
    {
        stmtInsert.setInt(1, idChambre);
        stmtInsert.setString(2, nomChambre);
        stmtInsert.setString(3, typeLit);
        stmtInsert.setInt(4, prix);
        stmtInsert.executeUpdate();
    }

    /** 
     * Suppression d'une chambre.
     */
    public void supprimeChambre(int idChambre) throws SQLException
    {
        stmtDelete.setInt(1, idChambre);
        stmtDelete.executeUpdate();
    }
    
    /** 
     * Inclure un service dans la chambre.
     */
    public void inclureService(int idChambre, int idService) throws SQLException
    {
        stmtInsertService.setInt(1, idChambre);
        stmtInsertService.setInt(2, idService);
        stmtInsertService.executeUpdate();
    }
    
    /** 
     * Suppression d'un service pour une chambre.
     */
    public void enleverService(int idChambre, int idService) throws SQLException
    {
        stmtDeleteService.setInt(1, idChambre);
        stmtDeleteService.setInt(2, idService);
        stmtDeleteService.executeUpdate();
    }
    
    /** 
     * Retourne vrai si la chambre possède déj<a le service
     */
    public boolean possedeService(int idChambre, int idService) throws SQLException
    {
        stmtExisteService.setInt(1, idChambre);
        stmtExisteService.setInt(2, idService);
        ResultSet rset = stmtExisteService.executeQuery();
        boolean serviceExiste = rset.next();
        rset.close();
        return serviceExiste;
    }
    
    /** 
     * Renvoie la liste des chambres
     */
    public List<TupleChambre> getListeChambres() throws SQLException
    {
        ResultSet rset = stmtListeChambres.executeQuery();
        List<TupleChambre> listeChambre = new LinkedList<TupleChambre>();
        while (rset.next())
        {
            TupleChambre chambre = new TupleChambre(rset.getInt("idChambre"),
                                              rset.getString("nomChambre"),
                                              rset.getInt("prix"),
                                              rset.getString("typeLit"));

            listeChambre.add(chambre);
        }
       
        rset.close();
        return listeChambre;
    }

}
