package com.github.profesorfalken.payzen.wsexample.create3ds;

import com.lyra.vads.ws.v5.ThreeDSMode;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.github.profesorfalken.payzen.wsexample.util.RequestUtils;
import com.github.profesorfalken.payzen.wsexample.util.ResponseUtils;
import static com.profesorfalken.payzen.webservices.sdk.Payment.create;
import com.profesorfalken.payzen.webservices.sdk.builder.PaymentBuilder;
import com.profesorfalken.payzen.webservices.sdk.builder.request.CardRequestBuilder;
import com.profesorfalken.payzen.webservices.sdk.builder.request.OrderRequestBuilder;
import com.profesorfalken.payzen.webservices.sdk.builder.request.PaymentRequestBuilder;
import com.profesorfalken.payzen.webservices.sdk.builder.request.ThreeDSRequestBuilder;

/**
 *
 * @author Javier Garcia Alonso
 */
@WebServlet(name = "Create3DSPayment", urlPatterns = {"/Create3DSPayment"})
public class Create3DSPayment extends HttpServlet {

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
        
        //Create object builder to launch 3DS payment
        OrderRequestBuilder orderRequestBuilder
                = OrderRequestBuilder
                .create()
                .orderId(orderId);

        PaymentRequestBuilder paymentRequestBuilder
                = PaymentRequestBuilder
                .create()
                .amount(amount)
                .currency(currency);

        CardRequestBuilder cardRequestBuilder
                = CardRequestBuilder
                .create()
                .number(cardNumber)
                .scheme("VISA")
                .expiryMonth(expMonth)
                .expiryYear(expYear)
                .cardSecurityCode(cvvCode);
        
        ThreeDSRequestBuilder threeDSRequestBuilder 
                = ThreeDSRequestBuilder
                .create()
                .mode(ThreeDSMode.ENABLED_CREATE);
                
        //Call WebService
        create(
                PaymentBuilder
                .getBuilder()
                .order(orderRequestBuilder.build())
                .payment(paymentRequestBuilder.build())
                .card(cardRequestBuilder.build())
                .threeDS(threeDSRequestBuilder.build())
                .comment("Test payment 3DS")
                .buildCreate(), 
                (res) -> {
                    if (res.getThreeDSResponse() != null && 
                            res.getThreeDSResponse().getAuthenticationRequestData() != null && 
                            res.getThreeDSResponse().getAuthenticationRequestData().getThreeDSRequestId() != null) {
                        ResponseUtils.sendToACS(request, response, res);
                    } else {
                        ResponseUtils.processResponse(request, response, res);
                    }
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
