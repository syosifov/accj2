package net.yosifov.accounting.accj;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class T1 {

    @Test
    public void t1() {
        BigDecimal vm = Optional.ofNullable((BigDecimal)null).orElse(BigDecimal.ZERO);
    }

    @Test
    public void whenOrElseWorks_thenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("john");
        assertEquals("john", name);
    }
}
