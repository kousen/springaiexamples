package com.kousenit.springaiexamples.output;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService service;

    @Test
    void extractPerson() {
        String text = """
                Captain Picard was born 281 years from now,
                in La Barre, France, on Earth, on the 13th
                of juillet. His given name, Jean-Luc, was in
                honor of his grandfather.
                """;
        PersonService.Person person = service.retrievePerson(text);
        System.out.println(person);
        assertEquals("Jean-Luc", person.firstName());
        assertEquals("Picard", person.lastName());
        assertThat(person.dob().getYear()).isBetween(2580, 2320);
    }

}