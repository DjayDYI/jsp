package AubergeInn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
/**
 * Classe TableClient
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui permet d'exploiter
 * la base de données pour les requêtes SQL se rapportant aux reservations
 *
 * </pre>
 */
public class TableClient
{
    //Prepared Statement
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtListeClient;
    private Connexion cx;

    /**
     * Creation d'une instance. Des énoncés SQL pour chaque requête sont
     * précompilés.
     */
    public TableClient(Connexion cx) throws SQLException
    {
        this.cx = cx;
        
        stmtExiste = cx.getConnection().prepareStatement("select idClient, nomClient, prenomClient, age from Client where idClient = ?");   
        stmtInsert = cx.getConnection().prepareStatement("insert into Client (idClient, nomClient, prenomClient, age) values (?,?,?,?)");
        stmtDelete = cx.getConnection().prepareStatement("delete from Client where idClient = ?");
        stmtListeClient = cx.getConnection().prepareStatement("select * from Client");
        
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si un client existe.
     */
    public boolean existe(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        boolean clientExiste = rset.next();
        rset.close();
        return clientExiste;
    }

    /**
     * Lecture d'un client.
     */
    public TupleClient getClient(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleClient tupleClient = new TupleClient();
            tupleClient.setIdClient(idClient);
            tupleClient.setNom(rset.getString(2));
            tupleClient.setPrenom(rset.getString(3));
            tupleClient.setAge(rset.getInt(4));
            rset.close();
            return tupleClient;
        }
        else
            return null;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données.
     */
    public void ajoutClient(int idClient, String nomClient, String prenomClient, int age) throws SQLException
    {
        stmtInsert.setInt(1, idClient);
        stmtInsert.setString(2, nomClient);
        stmtInsert.setString(3, prenomClient);
        stmtInsert.setInt(4, age);
        stmtInsert.executeUpdate();
    }
    
    /** 
     * Suppression d'une chambre.
     */
    public void supprimeClient(int idClient) throws SQLException
    {
        stmtDelete.setInt(1, idClient);
        stmtDelete.executeUpdate();
    }
    
    /** 
     * Renvoie la liste des clients
     */
    public List<TupleClient> getListeClients() throws SQLException
    {
        ResultSet rset = stmtListeClient.executeQuery();
        List<TupleClient> listeClient = new LinkedList<TupleClient>();
        while (rset.next())
        {
            TupleClient client = new TupleClient(rset.getInt("idClient"),
                                              rset.getString("nomClient"),
                                              rset.getString("prenomClient"),
                                              rset.getInt("age"));

            listeClient.add(client);
        }
       
        rset.close();
        return listeClient;
    }
}
