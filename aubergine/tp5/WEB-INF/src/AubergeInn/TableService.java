package AubergeInn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Classe TableService
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui permet d'exploiter
 * la base de données pour les requêtes SQL se rapportant a Service
 *
 * </pre>
 */
public class TableService
{
    // Prepared Statement
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtService;
    private PreparedStatement stmtListeServices;
    private Connexion cx;

    /**
     * Creation d'une instance. Des énoncés SQL pour chaque requête sont
     * précompilés.
     */
    public TableService(Connexion cx) throws SQLException
    {
        this.cx = cx;
        
        stmtExiste = cx.getConnection().prepareStatement("select idService, nomService, prix from Service where idService = ?");   
        stmtInsert = cx.getConnection().prepareStatement("insert into Service (idService, nomService, prix) values (?,?,?)");
        stmtDelete = cx.getConnection().prepareStatement("delete from Service where idService = ?");
        stmtService = cx.getConnection().prepareStatement("select idService from possede where idChambre = ?");
        stmtListeServices = cx.getConnection().prepareStatement("select * from Service");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si un service existe.
     */
    public boolean existe(int idService) throws SQLException
    {
        stmtExiste.setInt(1, idService);
        ResultSet rset = stmtExiste.executeQuery();
        boolean serviceExiste = rset.next();
        rset.close();
        return serviceExiste;
    }

    /**
     * Lecture d'un service.
     */
    public TupleService getService(int idService) throws SQLException
    {
        stmtExiste.setInt(1, idService);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleService tupleService = new TupleService();
            tupleService.setIdService(idService);
            tupleService.setNom(rset.getString(2));
            tupleService.setPrix(rset.getInt(3));
            rset.close();
            return tupleService;
        }
        else
            return null;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données.
     */
    public void ajoutService(int idService, String nomService, int prix) throws SQLException
    {
        
        stmtInsert.setInt(1, idService);
        stmtInsert.setString(2, nomService);
        stmtInsert.setInt(3, prix);
        stmtInsert.executeUpdate();
    }

    /** 
     * Suppression d'une chmabre.
     */
    public void supprimeService(int idService) throws SQLException
    {
        stmtDelete.setInt(1, idService);
        stmtDelete.executeUpdate();
    }

    /** 
     * Retourne la liste des idService pour une chambre donnée.
     */
    public ArrayList<Integer> getListeService(int idChambre)throws SQLException
    {
        stmtService.setInt(1, idChambre);
        ResultSet rset = stmtService.executeQuery();
        ArrayList<Integer> listeService= new ArrayList<Integer>();
        while (rset.next())
        {
            listeService.add(rset.getInt("idService"));
        }
       
        rset.close();
        return listeService;
    }
    
    /** 
     * Retourne la liste des services
     */
    public List<TupleService> getListeServices() throws SQLException
    {
        ResultSet rset = stmtListeServices.executeQuery();
        List<TupleService> listeService = new LinkedList<TupleService>();
        while (rset.next())
        {
            TupleService service = new TupleService(rset.getInt("idService"),
                                              rset.getString("nomService"),
                                              rset.getInt("prix"));

            listeService.add(service);
        }
       
        rset.close();
        return listeService;
    }



}
