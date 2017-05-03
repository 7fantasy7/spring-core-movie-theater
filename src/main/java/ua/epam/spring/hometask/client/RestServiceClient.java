package ua.epam.spring.hometask.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ua.epam.spring.hometask.domain.Event;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestServiceClient {

    public static void main(String[] args) throws IOException {
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        httpMessageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        RestTemplate template = new RestTemplate();
        template.setMessageConverters(httpMessageConverters);

        Event event = template.getForObject("http://localhost:9010/api/event/1", Event.class);
        System.out.println(event);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        HttpEntity<byte[]> byteResponse = template.exchange("http://localhost:8080/api/booking/tickets/1", HttpMethod.GET, entity, byte[].class);
        FileOutputStream fos = new FileOutputStream("tickets.pdf");
        fos.write(byteResponse.getBody());
        fos.close();
    }

}
