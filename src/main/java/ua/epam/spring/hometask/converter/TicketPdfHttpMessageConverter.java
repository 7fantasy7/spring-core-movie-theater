package ua.epam.spring.hometask.converter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class TicketPdfHttpMessageConverter implements HttpMessageConverter {
    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return mediaType.toString().equals(MediaType.APPLICATION_PDF_VALUE)
                && (User.class.isAssignableFrom(clazz)
                || Event.class.isAssignableFrom(clazz));
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return newArrayList(MediaType.APPLICATION_PDF);
    }

    @Override
    public Object read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            if (User.class.isAssignableFrom(o.getClass())) {
                User user = (User) o;
                Document document = new Document();
                document.open();
                document.add(new Paragraph("User : " + user.getLastName() + " " + user.getFirstName()));
                document.add(new Paragraph(user.getTickets().stream()
                        .map(Ticket::getDateTime)
                        .map(LocalDateTime::toString)
                        .collect(Collectors.joining())));
                document.close();
                return;
            }
            if (Event.class.isAssignableFrom(o.getClass())) {
                Event event = (Event) o;
                Document document = new Document();
                document.open();
                document.add(new Paragraph("Event : " + event.getName() +
                        "\n, rating: " + event.getRating() +
                        "\n, price: " + event.getBasePrice()));
                document.close();
                return;
            }
        } catch (DocumentException ex) {
        }
        throw new HttpMessageNotWritableException(o.getClass().getCanonicalName());
    }

}
