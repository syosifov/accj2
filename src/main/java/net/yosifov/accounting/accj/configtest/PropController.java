package net.yosifov.accounting.accj.configtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropController {

    @Value("${limits-service.minimum}")
    private int min;
    @Value("${limits-service.maximum}")
    private int max;

    @GetMapping("/props")
    public Limits props() {
        Limits limits = new Limits(min,max);
        return limits;
    }

}
