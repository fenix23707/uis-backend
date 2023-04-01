package by.kovzov.uis.academic.repository.api;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import by.kovzov.uis.academic.repository.AbstractIntegrationRepositoryTest;
import by.kovzov.uis.academic.repository.entity.Specialization;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SpecializationRepositoryTest extends AbstractIntegrationRepositoryTest {
    private static final int SPECIALIZATIONS_COUNT = 11;
    private static final int SPECIALIZATIONS_PARENTS_COUNT = 3;
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
        Optional<Specialization> optional = specializationRepository.findWithChildrenById(PARENT_ID);

        assertTrue(optional.isPresent());
        Specialization parent = specializationRepository.findWithChildrenById(PARENT_ID).get();

        assertEquals(PARENT_ID, parent.getId());
        verifyParentSpecialization(parent);
        parent.getChildren().forEach(child -> {
            assertEquals(PARENT_ID, child.getParent().getId());
            verifyChildSpecialization(child);
        });
    }

    @Test
    void testFindPageableAllParentIds() {
        int size = 2;
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, size);

        Page<Long> page = specializationRepository.findAllParentIds(pageable);

        assertEquals(size ,page.getContent().size());
        assertEquals(SPECIALIZATIONS_PARENTS_COUNT ,page.getTotalElements());
        assertEquals(SPECIALIZATIONS_PARENTS_COUNT / size + 1, page.getTotalPages());
        assertEquals(pageNumber , page.getNumber());
        assertTrue(page.isFirst());
        assertFalse(page.isEmpty());
    }

    @Test
    void testFindAllParentsWithChildren() {
        Pageable pageable = Pageable.ofSize(SPECIALIZATIONS_PARENTS_COUNT);

        Set<Long> ids = specializationRepository.findAllParentIds(pageable).toSet();
        List<Specialization> parentsWithChildren = specializationRepository.findAllByIds(ids, Sort.unsorted());

        assertEquals(ids.size(), parentsWithChildren.size());
        assertEquals(SPECIALIZATIONS_PARENTS_COUNT, parentsWithChildren.size());
        parentsWithChildren.forEach(this::verifySpecialization);
        Set<Specialization> children = parentsWithChildren.stream()
            .map(Specialization::getChildren)
            .filter(specializationChildren -> !specializationChildren.isEmpty())
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        children.stream()
            .peek(this::verifySpecialization)
            .map(Specialization::getId)
            .forEach(Assertions::assertNotNull);
    }

    @Test
    void testFindAllChildrenByParentId() {
        Set<Specialization> children = specializationRepository.findAllChildrenByParentId(PARENT_ID, Sort.unsorted());

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
        Optional<Specialization> optional = specializationRepository.findWithChildrenById(PARENT_ID);

        assertTrue(optional.isPresent());
        Specialization parent = specializationRepository.findWithChildrenById(PARENT_ID).get();
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