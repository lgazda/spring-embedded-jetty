package pl.net.gazda.embeddedjetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.net.gazda.embeddedjetty.service.SimpleService;

@RestController
public class SimpleController {
    private final SimpleService service;

    @Autowired
    public SimpleController(SimpleService service) {
        this.service = service;
    }

    @RequestMapping("/dummy")
    public ResponseEntity<?> dummy() {
        return ResponseEntity.ok(service.toString());
    }
}
