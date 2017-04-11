package ua.epam.spring.hometask.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ua.epam.spring.hometask.controller.support.PDFTicketControllerSupport;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;


@Controller
@RequestMapping("/pdf")
public class PDFTicketController extends PDFTicketControllerSupport {

    private static final String FILE_NAME = "tickets.pdf";

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/userTickets", headers = "Accept=application/pdf")
    public void getUserTicketInfo(@RequestParam long userId, HttpServletRequest request, HttpServletResponse response)
            throws IOException, DocumentException {
        final File tempDirectory = (File) request.getSession().getServletContext().getAttribute("javax.servlet.context.tempdir");
        final String tempPath = tempDirectory.getAbsolutePath();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + FILE_NAME);

        final User user = userService.getById(userId);
        if (user == null) {
            return;
        }
        createUserTicketsDocument(tempPath + "\\" + FILE_NAME, user);
        final ByteArrayOutputStream outputStream = convertFileToBytes(tempPath + "\\" + FILE_NAME);
        final OutputStream responseOutputStream = response.getOutputStream();
        outputStream.writeTo(responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
