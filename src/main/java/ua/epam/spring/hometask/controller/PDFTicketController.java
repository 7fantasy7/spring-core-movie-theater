package ua.epam.spring.hometask.controller;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.epam.spring.hometask.controller.support.PDFTicketControllerSupport;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


@Controller
@RequestMapping("/pdf")
@Transactional
public class PDFTicketController extends PDFTicketControllerSupport {

    private static final String FILE_NAME = "tickets.pdf";

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('BOOKING_MANAGER')")
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
