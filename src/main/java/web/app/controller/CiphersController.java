package web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.app.service.CiphersService;

@RestController
@RequestMapping("/ciphers")
public class CiphersController {
    @Autowired
    CiphersService ciphersService;

    @GetMapping("/caesar")
    @PreAuthorize("hasAuthority('SIMPLE_USER')")
    public String useCaesarCipher(@RequestParam String message, @RequestParam int key, @RequestParam boolean encryption) {
        return ciphersService.caesar(message, key, encryption);
    }

    @GetMapping("/playfair")
    @PreAuthorize("hasAuthority('SIMPLE_USER')")
    public String usePlayfairCipher(@RequestParam String message, @RequestParam String key, @RequestParam boolean encryption) {
        return ciphersService.playfair(message, key, encryption);
    }
}
