package com.stdio.esm.repository;

import com.stdio.esm.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Page<Skill> findAllByDeleteFlagIsFalse( Pageable pageable);

    @Query(value = "select skill from Skill skill where skill.deleteFlag = false and skill.skillTypes.id = (" +
            "select skillTypes.id from SkillTypes skillTypes where skillTypes.name = 'Technical Skills')")
    List<Skill> getListTechnicalSkills();

    @Query(value = "select skill from Skill skill where skill.deleteFlag = false and skill.skillTypes.id not in (" +
            "select skillTypes.id from SkillTypes skillTypes where skillTypes.name = 'Technical Skills')")
    List<Skill> getListNonTechnicalSkills();

    List<Skill> findAll();


    Optional<Skill> findByName(String name);

    boolean existsSkillById(Long skillId);
}
