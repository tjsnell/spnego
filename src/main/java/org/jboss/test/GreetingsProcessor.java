package org.jboss.test;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GreetingsProcessor implements Processor {
    /**
     * Simple processor to return a response from the web service client invocation.
     *
     * The original @WebParam values are contained within the exchange body, and these are used to build
     * a 'greetings' response.
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Object[] args = exchange.getIn().getBody(Object[].class);
        exchange.getOut().setBody(args[0] + " " + args[1]);
    }
}
