package ru.geekbrains.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByFirstNameAndEmail(String firstName, String email);

    @Query("from Person p where p.firstName = :firstName")
    List<Person> filterPersonByName(@Param("firstName") String firstName);

    Page<Person> findAllByAgeBetween(Integer min, Integer max, Pageable pageable);

    Page<Person> findAllByAgeGreaterThanEqual(Integer min, Pageable pageable);

    Page<Person> findAllByAgeLessThanEqual(Integer max, Pageable pageable);
}
