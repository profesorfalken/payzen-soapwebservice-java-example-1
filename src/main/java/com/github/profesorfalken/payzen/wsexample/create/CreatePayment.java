package com.github.profesorfalken.payzen.wsexample.create;

import com.github.profesorfalken.payzen.wsexample.util.RequestUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.profesorfalken.payzen.webservices.sdk.Payment.create;
import com.github.profesorfalken.payzen.wsexample.util.ResponseUtils;

/**
 *
 * @author Javier Garcia Alonso
 */
@WebServlet(name = "CreatePayment", urlPatterns = {"/CreatePayment"})
public class CreatePayment extends HttpServlet {

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
        String orderId = request.getParameter("orderId");
        long amount = RequestUtils.getLongFromRequest(request.getParameter("amount"));
        int currency = RequestUtils.getIntegerFromRequest(request.getParameter("currency"));
        String cardNumber = request.getParameter("cardNumber");
        int expMonth = RequestUtils.getIntegerFromRequest(request.getParameter("expMonth"));
        int expYear = RequestUtils.getIntegerFromRequest(request.getParameter("expYear"));
        String cvvCode = request.getParameter("cvvCode");

        //Call WebService
        create(
                orderId,
                amount,
                currency,
                cardNumber,
                expMonth,
                expYear,
                cvvCode,
                (result) -> {
                    ResponseUtils.processResponse(request, response, result);
                }
        );
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
