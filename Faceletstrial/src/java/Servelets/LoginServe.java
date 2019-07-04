/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servelets;

import beans.Singleton;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "LoginServe", urlPatterns = {"/LoginServe"})
public class LoginServe extends HttpServlet {

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
            throws ServletException, IOException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String user = request.getParameter("Username");
            String pass = request.getParameter("Password");
            int which = 0;
            int whicherr = 0;
            ArrayList<String> returned = new ArrayList<>();
            try {
//                out.println("a");
//                out.println("b");

                CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
//                out.println("c");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            crs=RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl(Singleton.getInstance().getDB());
            crs.setUsername(Singleton.getInstance().getUser());
            crs.setPassword(Singleton.getInstance().getPasswd());            
                crs.setCommand("select * from photographer where username like ?");
 //               out.println("e");

                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] sign = md.digest(pass.getBytes());

 //               out.println("f");
                crs.setString(1, user);
                crs.execute();
 //               out.println("g");
                whicherr += 1;
 //               out.println("h");

                while(crs.next()){
//                out.println("i");
                    returned.add(crs.getString("password"));
//                out.println("j");
                }

                if (returned.size() < 1){
                    which = 0;                    
                }
                else{
//                    out.println("k");
//                    out.println(returned.get(0).equals(new String(sign, "UTF-8")));
                    which = 1;
                    if (returned.get(0).equals(pass)) which = 2;
//                    out.println("l");
//                    out.println(new String(sign, "UTF-8"));
//                    out.println("m");
                }

                //out.println("<h1>Registration is complete</h1>");

            } catch (SQLException ex) {
                switch(whicherr){
                    case 0:
                        out.println("No such username in the database");
                        break;
                    case 1:
                        out.println("Incorrect password entered");
                        break;
                }
            } catch (Exception e){
                out.println("Some error");
            }
            //out.println("<h1>Servlet LoginServe at " + request.getContextPath() + "</h1>");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            if (which == 2){
                
                
                
                out.println("<meta http-equiv=\"refresh\" content=\"1; url=http://localhost:8080/Faceletstrial/faces/portfolio.xhtml\">");
            }
            out.println("<title>Servlet LoginServe</title>");            
            out.println("</head>");
            out.println("<body>");
            switch(which){
                case 0:
                    out.println("No such username in the database");
                    break;
                case 1:
                    out.println(returned.get(0).equals(pass));
                    break;
            }
            out.println("</body>");
            out.println("</html>");
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginServe.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginServe.class.getName()).log(Level.SEVERE, null, ex);
        }
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
