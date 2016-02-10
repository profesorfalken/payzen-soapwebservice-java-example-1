package com.github.profesorfalken.payzen.wsexample.create3ds;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.github.profesorfalken.payzen.wsexample.util.ResponseUtils;
import static com.profesorfalken.payzen.webservices.sdk.Payment.create;
import com.profesorfalken.payzen.webservices.sdk.ResponseHandler;
import com.profesorfalken.payzen.webservices.sdk.ServiceResult;

/**
 *
 * @author Javier Garcia Alonso
 */
@WebServlet(name = "Finalize3DSPayment", urlPatterns = {"/Finalize3DSPayment"})
public class Finalize3DSPayment extends HttpServlet {

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
        //Get form parameters
        String paRes = request.getParameter("PaRes");
        String MD = request.getParameter("MD");
        
        create(paRes, 
            MD, new ResponseHandler() {
            @Override
            public void handle(ServiceResult result) throws Exception {
                ResponseUtils.processResponse(request, response, result);
            }
        });
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet that shows how to make a Payment by WS Payzen";
    }

}
