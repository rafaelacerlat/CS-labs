package web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTickets(@PathVariable String username){
        userService.deleteByUsername(username);
    }

}
