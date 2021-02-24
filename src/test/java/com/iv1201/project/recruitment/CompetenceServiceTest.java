package com.iv1201.project.recruitment;

import com.iv1201.project.recruitment.application.CompetenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CompetenceServiceTest {

    @Autowired
    private CompetenceService compService;

    @Test
    public void test_getAllWithLocalNames() {
        var compMapEn_US = compService.getAllWithLocalNames("en_US");
        assertThat(compMapEn_US.containsKey("Grilling sausage")).isTrue();
        assertThat(compMapEn_US.containsKey("Carousel operation")).isTrue();
        assertThat(compMapEn_US.containsKey("NONEXISTENT COMP")).isFalse();
        assertThat(compMapEn_US.size()).isEqualTo(2);

        var comp1 = compMapEn_US.get("Grilling sausage");
        var comp2 = compService.getAllWithLocalNames("sdadssad").get("Grilling sausage");
        assertThat(comp1).isEqualTo(comp2);
        var compMapNONE = compService.getAllWithLocalNames("NONEXISTENT_LANG");
        compMapEn_US.forEach((k,v) -> assertThat(compMapNONE.get(k)).isEqualTo(v));
    }
}
