package com.stdio.esm.repository;

import com.stdio.esm.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProjectRepository extends JpaRepository<Project, Long> {

//    @Query(value = "SELECT t1 FROM ProjectSkills t1  " +
//            "WHERE t1.skill in (?1) AND t1.project.projectStatus.id NOT IN ( " +
//            "SELECT t2.id FROM ProjectStatus t2 WHERE t2.name = 'Not started') AND t1.project.deleteFlag = false  " +
//            "ORDER BY t1.project.startTime DESC")

//    @Query(value = "select p from Project p where p.skills in (skill) ")
//    List<ProjectSkills> getProjectsBySkills(@Param("skills") List<Skill> skills);

    List<Project> findAllByDeleteFlagIsFalse();


    List<Project> findAllByDeleteFlagIsFalseOrderByStartTimeDesc(Pageable pageable);

}
