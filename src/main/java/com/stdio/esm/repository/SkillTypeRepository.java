package com.stdio.esm.repository;

import com.stdio.esm.model.SkillTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillTypeRepository extends JpaRepository<SkillTypes, Long> {

    Optional<SkillTypes> findByName(String name);

    boolean existsSkillTypesById(Long skillTypesId);
}
