package ua.epam.spring.hometask.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/upload")
@Transactional
public class FileUploadController {

    private static final String UPLOADED_FOLDER = "D:/temp/";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @PostConstruct
    public void init() {
        mapper.registerModule(new JavaTimeModule());
    }

    @RequestMapping
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public String form() {
        return "authorized/upload";
    }

    @RequestMapping(method = RequestMethod.POST, value = "users")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")

    public String usersFileUpload(@RequestParam("file") MultipartFile file,
                                  RedirectAttributes redirectAttributes) throws IOException {
        List<User> users = mapper.readValue(file.getInputStream(), new TypeReference<List<User>>() {
        });
        users.forEach(userService::save);
        return "redirect:/users";
    }

    @RequestMapping(method = RequestMethod.POST, value = "events")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public String eventsFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
        List<Event> events = mapper.readValue(file.getInputStream(), new TypeReference<List<Event>>() {
        });
        events.forEach(eventService::save);
        return "redirect:/events";
    }

    /*
        Simple file upload
     */
    @RequestMapping(method = RequestMethod.POST, value = "file")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public String fileUpload(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "File should not be empty");
            return "redirect:upload";
        }
        byte[] fileBytes = file.getBytes();
        final String originalFilename = file.getOriginalFilename();
        Path path = Paths.get(UPLOADED_FOLDER +
                StringUtils.getFilename(originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                        + System.currentTimeMillis()
                        + originalFilename.substring(originalFilename.lastIndexOf('.'), originalFilename.length() - 1)));
        Files.createFile(path);
        Files.write(path, fileBytes);

        redirectAttributes.addFlashAttribute("message",
                "File \"" + file.getOriginalFilename() + "\" successfully uploaded");

        return "redirect:/upload";
    }

}
