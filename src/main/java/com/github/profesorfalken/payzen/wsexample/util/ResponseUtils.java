package com.github.profesorfalken.payzen.wsexample.util;

import com.lyra.vads.ws.v5.AuthenticationRequestData;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.profesorfalken.payzen.webservices.sdk.ServiceResult;

/**
 * Class that contains utility methods that process and render the response
 * 
 * @author Javier Garcia Alonso
 */
public class ResponseUtils {

    /**
     * Renders on HTML all the content in the response
     * 
     * @param request
     * @param response
     * @param result
     * @throws ServletException
     * @throws IOException 
     */
    public static void processResponse(HttpServletRequest request, HttpServletResponse response, ServiceResult result)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Web Service Test Response</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Payzen Web Service Response</h1><p>");

            out.println(result.getCommonResponse().getResponseCode() + "<br/>");
            out.println(result.getCommonResponse().getResponseCodeDetail() + "<p/>");

            out.println(ToStringBuilder.reflectionToString(result.getCommonResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getAuthorizationResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getCaptureResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getCardResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getCustomerResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getExtraResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getFraudManagementResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getMarkResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getOrderResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getPaymentResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getThreeDSResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getShoppingCartResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getSubscriptionResponse()) + "<br/>");
            out.println(ToStringBuilder.reflectionToString(result.getTokenResponse()) + "<br/>");

            out.println("<a href=\""+ request.getContextPath() + "/\" />Back To Example</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Generates a redirect page that send all params by POST to ACS page
     * 
     * @param request
     * @param response
     * @param result
     * @throws ServletException
     * @throws IOException 
     */
    public static void sendToACS(HttpServletRequest request, HttpServletResponse response, ServiceResult result)
            throws ServletException, IOException {
        AuthenticationRequestData requestData = result.getThreeDSResponse().getAuthenticationRequestData();
        
        String acsUrl = requestData.getThreeDSAcsUrl();
        if ("TEST".equals(result.getMode())) {
            acsUrl += ";" + result.getWebServiceSession().toLowerCase();
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body onload=\"document.forms['form3DS'].submit()\">");
            out.println("<form name=\"form3DS\" method=\"POST\" action=\"" + acsUrl + "\">");
            out.println(addHiddenData("MD", result.getRedirectAcsMD()));
            out.println(addHiddenData("threeDSAcctId", requestData.getThreeDSAcctId()));
            out.println(addHiddenData("threeDSBrand", requestData.getThreeDSBrand()));
            out.println(addHiddenData("PaReq", requestData.getThreeDSEncodedPareq()));
            out.println(addHiddenData("threeDSEnrolled", requestData.getThreeDSEnrolled()));
            out.println(addHiddenData("threeDSRequestId", requestData.getThreeDSRequestId()));
            String uri = request.getScheme() + "://"
                    + request.getServerName()
                    + ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort())
                    + request.getRequestURI();
            uri = uri.replaceAll("Create3DSPayment", "Finalize3DSPayment");
            out.println(addHiddenData("TermUrl", uri));

            //out.println("<input type=\"submit\" name=\"redirect\" value=\"Redirect\"/>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static String addHiddenData(String name, String value) {
        return "<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\">";
    }
}
