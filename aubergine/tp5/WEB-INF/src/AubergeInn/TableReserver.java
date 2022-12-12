package AubergeInn;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Classe TableReserver
 * 
 * <pre>
 * 
 * Jérôme Boucher-Veilleux
 * Patrick-Olivier Tété
 * Universite de Sherbrooke
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe est la couche qui permet d'exploiter
 * la base de données pour les requêtes SQL se rapportant aux client
 *
 * </pre>
 */
public class TableReserver
{
    //Prepared Statement
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtListe;
    private PreparedStatement stmtDeleteChambre;

    private Connexion cx;

    /**
     * Creation d'une instance. Des énoncés SQL pour chaque requête sont
     * précompilés.
     */
    public TableReserver(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtExiste = cx.getConnection().prepareStatement("select idChambre, idClient, dateDebut, dateFin from Reserver where idClient = ?");
        stmtInsert = cx.getConnection().prepareStatement("insert into Reserver (idChambre, idClient, dateDebut, dateFin) values (?,?,?,?)");
        stmtDelete = cx.getConnection().prepareStatement("delete from Reserver where idClient = ? and idChambre = ? and dateDebut = ?");
        stmtDeleteChambre = cx.getConnection().prepareStatement("delete from Reserver where idChambre = ?");
        stmtListe = cx.getConnection().prepareStatement("select idChambre, idClient, dateDebut, dateFin from Reserver where idChambre = ?");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Retourne vrai si la reservation existe.
     */
    public boolean existe(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        boolean reservationExiste = rset.next();
        rset.close();
        return reservationExiste;    
    }
    
    /**
     * Lecture d'une reservation.
     */
    public TupleReserver getReservation(int idChambre) throws SQLException
    {
        stmtListe.setInt(1, idChambre);
        ResultSet rset = stmtListe.executeQuery();
        if (rset.next())
        {
            TupleReserver tupleReserver= new TupleReserver();
            tupleReserver.setIdChambre(idChambre);
            tupleReserver.setIdClient(rset.getInt(2));
            tupleReserver.setDateDebut(rset.getDate(3));
            tupleReserver.setDateFin(rset.getDate(4));
            rset.close();
            return tupleReserver;
        }
        else
            return null;
    }
    
    /**
     * Ajout reservation.
     */
    public void ajoutReservation(int idChambre, int idClient, Date dateDebut, Date dateFin) throws SQLException
    {
        stmtInsert.setInt(1, idChambre);  
        stmtInsert.setInt(2, idClient);
        stmtInsert.setDate(3, dateDebut);
        stmtInsert.setDate(4, dateFin);
        stmtInsert.executeUpdate();
    }
   
    /**
     * Retourne la liste de reservation pour une chambre donnée.
     */
    public ArrayList<TupleReserver> getListeReservation(int idChambre) throws SQLException
    {
        stmtListe.setInt(1, idChambre);
        ResultSet rset = stmtListe.executeQuery();
        ArrayList<TupleReserver> listeReservation= new  ArrayList<TupleReserver>();
        while (rset.next())
        {
            TupleReserver chambreReserver = new TupleReserver(rset.getInt("idChambre"),
                                              rset.getInt("idClient"),
                                              rset.getDate("dateDebut"),
                                              rset.getDate("dateFin"));

            listeReservation.add(chambreReserver);
        }
       
        rset.close();
        return listeReservation;
    }
    
    /**
     * Retourne la liste de reservation pour un client donné.
     */
    public ArrayList<TupleReserver> getListeReservationClient(int idClient)throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        ArrayList<TupleReserver> listeReservation= new ArrayList<TupleReserver>();
        while (rset.next())
        {
            TupleReserver clientReserver = new TupleReserver(rset.getInt("idChambre"),
                                              rset.getInt("idClient"),
                                              rset.getDate("dateDebut"),
                                              rset.getDate("dateFin"));

            listeReservation.add(clientReserver);
        }
       
        rset.close();
        return listeReservation;
    }

    /**
     * Supprime un réservation pour un client et une chambre précise
     * Si le client a 2 rérvations commencant à la même date les 2 seront supprimées
     */
    public void annulerReservation(int idClient, int idChambre, Date dateDebut) throws SQLException
    {
        stmtDelete.setInt(1, idClient);
        stmtDelete.setInt(2, idChambre);
        stmtDelete.setDate(3, dateDebut);
        stmtDelete.executeUpdate();
    }
    
    /**
     * Supprime les réservations pour une chambre donnée
     */
    public void supprimerReservation(int idChambre) throws SQLException
    {
        stmtDeleteChambre.setInt(1, idChambre);
        stmtDeleteChambre.executeUpdate();
    }

}
