package ua.epam.spring.hometask.client;

import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class WebServiceClient {

    private static final String MESSAGE = "<message xmlns=\"http://tempuri.org\">Hello Web Service World</message>";

    private final WebServiceTemplate template = new WebServiceTemplate();

    private void simple() {
        StreamSource source = new StreamSource(new StringReader(MESSAGE));
        StreamResult result = new StreamResult(System.out);
        template.sendSourceAndReceiveToResult("localhost:9010/ws/",source, result);
    }

    public static void main(String[] args) {
        new WebServiceClient().simple();
    }
}
