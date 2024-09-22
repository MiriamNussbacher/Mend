package Mend.api;

import Mend.dto.UserActivityDTO;
import Mend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Value("${app.max-user-limit:50}")
    private int maxUserLimit;

    @GetMapping("/top-active")
    public ResponseEntity<List<UserActivityDTO>> getTopActiveUsers(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        if (limit > maxUserLimit) {
            limit = maxUserLimit;
        }

        List<UserActivityDTO> topUsers = userService.getTopActiveUsers(limit);
        return ResponseEntity.ok(topUsers);
    }
}

