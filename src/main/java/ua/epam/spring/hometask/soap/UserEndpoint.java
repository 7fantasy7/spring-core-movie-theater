package ua.epam.spring.hometask.soap;

import https.epam_com.movie_theater_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ua.epam.spring.hometask.config.WsConfig;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;

@Endpoint
public class UserEndpoint {

    private final UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = WsConfig.NAMESPACE_URI, localPart = "getUser")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest userRequest) {
        GetUserResponse userResponse = new GetUserResponse();
        userResponse.setUser(convert(userService.getById(userRequest.getUserId())));
        return userResponse;
    }

    @PayloadRoot(namespace = WsConfig.NAMESPACE_URI, localPart = "putUser")
    @ResponsePayload
    public PutUserResponse putUser(@RequestPayload PutUserRequest userRequest) {
        PutUserResponse userResponse = new PutUserResponse();
        userResponse.setUser(convert(userService.save(convert(userRequest.getUser()))));
        return userResponse;
    }

    private ua.epam.spring.hometask.domain.User convert(User user) {
        ua.epam.spring.hometask.domain.User dbUser = new ua.epam.spring.hometask.domain.User();
        dbUser.setId(user.getId());
        dbUser.setEmail(user.getEmail());
        dbUser.setBirthDay(LocalDateTime.parse(user.getBirthDay()));
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        return dbUser;
    }

    private User convert(ua.epam.spring.hometask.domain.User user) {
        User apiUser = new User();
        apiUser.setId(user.getId());
        apiUser.setEmail(user.getEmail());
        apiUser.setBirthDay(user.getBirthDay().toString());
        apiUser.setFirstName(user.getFirstName());
        apiUser.setLastName(user.getLastName());
        return apiUser;
    }

}
