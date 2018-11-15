package org.jboss.test;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet(name = "HttpServiceServlet", urlPatterns = {"/cxf/*"}, loadOnStartup = 1)
public class CamelCxfWsServlet extends HttpServlet {

    @Resource(lookup = "java:jboss/camel/context/cxfws-secure-camel-context")
    private CamelContext camelContext;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/test.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        /**
         * Get message and name parameters sent on the POST request
         */
        String message = request.getParameter("message");
        String name = request.getParameter("name");

        /**
         * Create a ProducerTemplate to invoke the direct:start endpoint, which will
         * result in the greeting web service 'greet' method being invoked.
         *
         * The web service parameters are sent to camel as an object array which is
         * set as the request message body.
         *
         * The web service result string is returned back for display on the UI.
         */
        ProducerTemplate producer = camelContext.createProducerTemplate();

        Object[] serviceParams = new Object[]{message, name};
        String result = producer.requestBody("direct:start", serviceParams, String.class);

        request.setAttribute("greeting", result);
        request.getRequestDispatcher("/greeting.jsp").forward(request, response);
    }
}
