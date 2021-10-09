package az.hrportal.hrportalapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("keep-alive")
@Slf4j
public class KeepAliveController {
    @GetMapping
    public void keepAlive() {
        log.info("***** keepAlive *****");
    }
}
