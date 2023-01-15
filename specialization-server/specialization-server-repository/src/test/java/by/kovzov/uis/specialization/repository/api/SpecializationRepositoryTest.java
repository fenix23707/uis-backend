package by.kovzov.uis.specialization.repository.api;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;

import by.kovzov.uis.specialization.domain.entity.Specialization;
import by.kovzov.uis.specialization.repository.AbstractIntegrationRepositoryTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SpecializationRepositoryTest extends AbstractIntegrationRepositoryTest {
    private static final int SPECIALIZATIONS_COUNT = 5;
    private static final Long PARENT_ID = 1L;

    @Autowired
    SpecializationRepository specializationRepository;

    public SpecializationRepositoryTest() {
        super("data/specialization.sql");
    }

    @Test
    void testCount() {
        assertEquals(SPECIALIZATIONS_COUNT, specializationRepository.count());
    }

    @Test
    void testFindById() {
        Specialization parent = specializationRepository.findById(PARENT_ID).get();

        assertEquals(PARENT_ID, parent.getId());
        verifyParentSpecialization(parent);
        parent.getChildren().forEach(child -> {
            assertEquals(PARENT_ID, child.getParent().getId());
            verifyChildSpecialization(child);
        });
    }

    @Test
    void testFindAllChildrenByParentId() {
        List<Specialization> children = specializationRepository.findAllChildrenByParentId(PARENT_ID);

        children.forEach(child -> {
            assertEquals(PARENT_ID, child.getParent().getId());
            verifyChildSpecialization(child);
        });
    }

    @Test
    void testSaveNewParent() {
        Specialization parent = buildSpecialization();
        assertNull(parent.getId());

        Specialization result = specializationRepository.save(parent);

        assertNotNull(result.getId());
        assertEquals(SPECIALIZATIONS_COUNT + 1, specializationRepository.count());
        verifyParentSpecialization(result);
    }

    @Test
    void testSaveNewChild() {
        Specialization parent = specializationRepository.findById(PARENT_ID).get();
        Specialization child = buildSpecialization();
        child.setParent(parent);
        assertNull(child.getId());

        Specialization result = specializationRepository.save(child);

        assertNotNull(result.getId());
        assertEquals(PARENT_ID, result.getParent().getId());
        assertEquals(SPECIALIZATIONS_COUNT + 1, specializationRepository.count());
        verifyChildSpecialization(result);
    }

    private void verifyParentSpecialization(Specialization parent) {
        assertNull(parent.getParent());
        verifySpecialization(parent);
    }

    private void verifyChildSpecialization(Specialization child) {
        assertNotNull(child.getParent());
        verifySpecialization(child);
    }

    private void verifySpecialization(Specialization specialization) {
        assertNotNull(specialization.getName());
        assertNotNull(specialization.getShortName());
        assertNotNull(specialization.getCipher());
        assertNotNull(specialization.getChildren());
    }

    private Specialization buildSpecialization() {
        Specialization specialization = new Specialization();
        specialization.setName("specialization name");
        specialization.setShortName("short name");
        specialization.setCipher("cipher");
        specialization.setChildren(Collections.emptySet());
        return specialization;
    }
}