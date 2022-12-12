package Servlet;

import javax.servlet.http.*;

import AubergeInn.GestionAuberge;

import java.sql.*;

/**
 * Classe pour gestion des sessions
 * <P>
 * Syst�me de gestion de biblioth�que &copy; 2004 Marc Frappier, Universit� de
 * Sherbrooke
 */

public class AubergInnSessionListener implements HttpSessionListener
{
    public void sessionCreated(HttpSessionEvent se)
    {
    }

    public void sessionDestroyed(HttpSessionEvent se)
    {
        System.out.println("AubergeSessionListener " + se.getSession().getId());
        
        GestionAuberge aubergeInterrogation = (GestionAuberge)se.getSession().getAttribute("aubergeInterrogation");
        if (aubergeInterrogation != null)
        {
            System.out.println("connexion =" + aubergeInterrogation.getConnexion());
            try
            {
                aubergeInterrogation.fermer();
            }
            catch (SQLException e)
            {
                System.out.println("Impossible de fermer la connexion");
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("AubergInn inaccessible.");
        }
        
        GestionAuberge aubergeUpdate = (GestionAuberge)se.getSession().getAttribute("aubergeUpdate");
        if (aubergeUpdate != null)
        {
            System.out.println("connexion = " + aubergeUpdate.getConnexion());
            try
            {
                aubergeUpdate.fermer();
            }
            catch (SQLException e)
            {
                System.out.println("Impossible de fermer la connexion");
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("AubergInn inaccessible.");
        }
    }
}
