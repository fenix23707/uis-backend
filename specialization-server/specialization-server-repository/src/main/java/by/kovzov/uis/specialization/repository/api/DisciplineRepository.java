package by.kovzov.uis.specialization.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;

import by.kovzov.uis.specialization.repository.entity.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

}
