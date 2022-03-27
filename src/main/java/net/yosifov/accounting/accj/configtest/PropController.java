package net.yosifov.accounting.accj.configtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropController {

    @Value("${limits-service.minimum}")
    private String min;
    @Value("${limits-service.maximum}")
    private String max;

    @GetMapping("/props")
    public Limits props() {
        Limits limits = new Limits(Integer.valueOf(min),
                                   Integer.valueOf(max));
        return limits;
    }

}
