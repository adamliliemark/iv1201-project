package com.iv1201.project.recruitment;

import com.iv1201.project.recruitment.application.CompetenceService;
import com.iv1201.project.recruitment.domain.Competence;
import com.iv1201.project.recruitment.domain.CompetenceProfile;
import com.iv1201.project.recruitment.domain.Language;
import com.iv1201.project.recruitment.repository.CompetenceRepository;
import com.iv1201.project.recruitment.repository.CompetenceTranslationRepository;
import com.iv1201.project.recruitment.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CompetenceTest {

    @Autowired
    private CompetenceService compService;

    @Autowired
    private CompetenceRepository compRepo;

    @Autowired
    private LanguageRepository languageRepo;

    @Autowired
    private CompetenceTranslationRepository transRepo;

    @Test
    public void getting_name_in_different_languages_works() {
        Competence c;
        var cs = compService.getAllWithLocalNames("en_US");
        assertThat(cs.get("Grilling sausage").getName("sv_SE")).isEqualTo("Korvgrillning");
        assertThat(cs.get("Carousel operation").getName("sv_SE")).isEqualTo("Karuselldrift");
        assertThat(cs.get("Grilling sausage").getName("NONEXISTENT_LANGUAGE")).isEqualTo("Grilling sausage");
    }

    @Test
    public void adding_translations_work() {
        Language finnish = languageRepo.save(new Language("fi_FI", "Suomalainen"));
        Competence finGrill = compRepo.save(Competence.create());
        finGrill.addTranslation(finnish, "Grillaus");
        finGrill = compRepo.save(finGrill);
        assertThat(transRepo.findByText("Grillaus")).isNotEmpty();
        assertThat(compRepo.findById(finGrill.getId()).get().getName("fi_FI")).isEqualTo("Grillaus");
    }
}
