/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servelets;

import beans.Controller;
import beans.Singleton;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;



/**
 *
 * @author Asif Towheed
 */
@WebServlet(name = "PhotographerCount", urlPatterns = {"/PhotographerCount"})
public class PhotographerCount extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
//            DBSingleton sing = DBSingleton.getInstace();
//            Class.forName(sing.getDriver());          

            Controller controller = (Controller) request.getSession().getAttribute("controller");
            
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
            crs.setCommand("select count(photographer_id) as cnt, photographer.FNAME as fname from reservation inner join photographer on photographer.ID = reservation.PHOTOGRAPHER_ID and reservation.CUSTOMER_ID = ? group by photographer.FNAME");
            crs.setInt(1,controller.current_c.getID());
            crs.execute();
            
            out.print("[ [\"Photographer\",\"Count\"]");
            while(crs.next())
            {
                out.print(",");
                out.print("[\"" + crs.getString("fname") + "\", " + crs.getInt("cnt")+"]");
            }
            out.print("]");
//            System.out.println("XXX:"+request.getParameter("authorid"));
        } catch (SQLException ex) {
            Logger.getLogger(PhotographerCount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhotographerCount.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
            
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
