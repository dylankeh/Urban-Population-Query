/*
 * This class displays city information which has been acquired from database
 * 
 * @author Qing Ge Phoenix
 */
package ExerciseSolution;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Qing Ge Phoenix
 */
public class DisplayInfo extends HttpServlet {
    
    private WorldDBAccess db;
    private boolean connected = false;

    
    @Override
    public void init(ServletConfig config) throws ServletException {
        //db = new WorldDBAccess(config.getInitParameter("DBName"), config.getInitParameter("DBuser"), config.getInitParameter("DBpassword"));
        db = new WorldDBAccess(config.getServletContext().getInitParameter("DBname"), config.getServletContext().getInitParameter("DBuser"), config.getServletContext().getInitParameter("DBpassword"));

        if (db.connect()) {
            connected = true;
        }
    } 
    
    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
      
        City city = null;

        try {
            if (connected) {

                String cityID = request.getParameter("ID");

                if (isNullOrBlank(cityID)) {
                    throw new MyException("Please input City ID");
                }

                try {
                    int cityId = Integer.parseInt(cityID);
                    city = db.getCityInfoById(cityId);
                    if (city == null) {
                        throw new MyException(
                                "Incorrect city ID or no such city!");
                    }
                } catch (NumberFormatException ex) {
                    throw new MyException("Incorrect input!");
                }

                
            } else {
                throw new MyException("Cannot connect to Database!");
            }
        } catch (MyException ex) {

            
            request.setAttribute("message", new String(ex.getMessage()));
            request.getRequestDispatcher("index.jsp").
                    forward(request, response);

        }

        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DisplayInfo</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" "
                    + "href=\"css/login.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"main-page\">");
            out.println("<div class=\"form\">");
            out.println("<h3>Information for City ID " 
                    + request.getParameter("ID") + "</h3>");
            out.println("<p>Name: " + city.getName() + "</p>");
            out.println("<p>Country Code: " + city.getCountryCode() + "</p>");
            out.println("<p>District: " + city.getDistrict() + "</p>");
            out.println("<p>Population: " + city.getPopulation() + "</p>");
            out.println("<a href=\"index.jsp\">Go Home</a>");
            out.println("</div>");
            out.println("</div>");
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
