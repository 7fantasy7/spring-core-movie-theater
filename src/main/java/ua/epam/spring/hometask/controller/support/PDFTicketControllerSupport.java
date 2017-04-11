package ua.epam.spring.hometask.controller.support;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

public class PDFTicketControllerSupport {

    protected Document createUserTicketsDocument(final String fileName, final User user) throws FileNotFoundException, DocumentException {
        final Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        createTable(document, user);
        document.close();
        return document;
    }

    private void createTable(final Document document, final User user) throws DocumentException {
        Paragraph header = new Paragraph("Ticket info");
        header.setAlignment("Center");
        header.setSpacingAfter(10);
        document.add(header);

        Paragraph userTitle = new Paragraph("User");
        userTitle.setAlignment("Center");
        userTitle.setSpacingAfter(5);
        document.add(userTitle);
        document.add(createUserInfo(user));

        Paragraph ticketTitle = new Paragraph("Tickets");
        ticketTitle.setAlignment("Center");
        ticketTitle.setSpacingBefore(10);
        ticketTitle.setSpacingAfter(5);
        document.add(ticketTitle);
        document.add(createTicketsInfo(user));
    }

    private PdfPTable createUserInfo(final User user) {
        PdfPTable userTable = new PdfPTable(2);
        userTable.setWidthPercentage(50);
        userTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        userTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        userTable.addCell("Name");
        userTable.addCell(user.getLastName() + " " + user.getLastName());
        userTable.addCell("Email");
        userTable.addCell(user.getEmail() != null ? user.getEmail() : "-");
        return userTable;
    }

    private PdfPTable createTicketsInfo(final User user) {
        PdfPTable ticketsTable = new PdfPTable(4);
        ticketsTable.addCell(centeredCellWithText("Event"));
        ticketsTable.addCell(centeredCellWithText("Date"));
        ticketsTable.addCell(centeredCellWithText("Seat #"));
        ticketsTable.addCell(centeredCellWithText("Price"));
        ticketsTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        ticketsTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        user.getTickets().forEach(ticket -> {
            fillTicket(ticketsTable, ticket);
        });
        return ticketsTable;
    }

    private PdfPCell centeredCellWithText(final String text) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(text));
        cell.setHorizontalAlignment(1);
        return cell;
    }

    private void fillTicket(final PdfPTable table, final Ticket ticket) {
        table.addCell(ticket.getEvent().getName());
        table.addCell(ticket.getDateTime().toString());
        table.addCell(String.valueOf(ticket.getSeat()));
        table.addCell(String.valueOf(ticket.getPrice()));
    }

    protected ByteArrayOutputStream convertFileToBytes(final String fileName) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (final InputStream inputStream = new FileInputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return outputStream;
    }

}
