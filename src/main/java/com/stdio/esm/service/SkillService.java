package com.stdio.esm.service;

import com.stdio.esm.dto.SkillDto;
import com.stdio.esm.dto.response.SkillResponse;
import com.stdio.esm.exception.SkillNotFound;
import com.stdio.esm.model.Employee;
import com.stdio.esm.model.Project;
import com.stdio.esm.model.Skill;
import com.stdio.esm.model.SkillLevel;
import com.stdio.esm.model.mapper.SkillMapper;
import com.stdio.esm.repository.SkillRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.data.jpa.repository.query.PartTreeJpaQuery;
@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillMapper skillMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillService.class);


    /**
     * Get all list skill
     *
     * @return {@link List<SkillDto>>}
     */

    @Transactional(readOnly = true)
    public Map<String, List<SkillResponse>> getAllListSkill() {
        LOGGER.info("Started get all list skill in service layer");
        List<SkillResponse> responseData = new ArrayList<>();
        Map<String, List<SkillResponse>> responseDataSkill = new HashMap<>();

        skillRepository.findAll().stream()
                .forEach(s -> {
                    SkillResponse response = skillMapper.
                            skillToSkillResponse(s, s.getSkillLevels().stream().map(SkillLevel::getName).collect(Collectors.toList()),

                                    s.getProjects_skills().stream().filter(tmp -> !tmp.getDeleteFlag() && tmp.getProjectStatus().getId() != 5).map(Project::getName).collect(Collectors.toList()),

                                    s.getEmployees_skills().stream().filter(tmp -> !tmp.getDeleteFlag()).map(Employee::getName).collect(Collectors.toList()));
                    responseData.add(response);
                });
        LOGGER.info("Finished get all list skill in service layer");
        responseDataSkill.put("skills", responseData);
        return responseDataSkill;
    }

    /**
     * Get skill from id
     *
     * @param skillId
     * @return {@link SkillDto}
     */
    @Transactional(readOnly = true)
    public SkillResponse getSkillById(@NotNull  Long skillId) throws SkillNotFound{
        LOGGER.info("Started get skill by id in service layer");
        Skill skill = skillRepository.findById(skillId).orElseThrow(()-> new SkillNotFound("Skill is not exits"));
        LOGGER.info("Finished get skill by id in service layer");
        return skillMapper.skillToSkillResponse(skill,
                skill.getSkillLevels().stream().map(SkillLevel::getName).collect(Collectors.toList()),
                skill.getProjects_skills().stream().filter(tmp -> !tmp.getDeleteFlag() && tmp.getProjectStatus().getId()!=5).map(Project::getName).collect(Collectors.toList()),
                skill.getEmployees_skills().stream().filter(tmp->!tmp.getDeleteFlag()).map(Employee::getName).collect(Collectors.toList()));

    }

    /**
     * Get skill from name
     *
     * @param name
     * @return {@link SkillDto}
     */
    @Transactional(readOnly = true)
    public SkillResponse getSkillByName(@NotBlank String name) throws SkillNotFound{
        LOGGER.info("Started get skill by id in service layer");
        Skill skill = skillRepository.findByName(name).orElseThrow(()-> new SkillNotFound("Skill is not exits"));
        LOGGER.info("Finished get skill by id in service layer");
        return skillMapper.skillToSkillResponse(skill,
                skill.getSkillLevels().stream().map(SkillLevel::getName).collect(Collectors.toList()),
                skill.getProjects_skills().stream().filter(tmp -> !tmp.getDeleteFlag() && tmp.getProjectStatus().getId()!=5).map(Project::getName).collect(Collectors.toList()),
                skill.getEmployees_skills().stream().filter(tmp->!tmp.getDeleteFlag()).map(Employee::getName).collect(Collectors.toList()));

    }

    /**
     * create new skill
     *
     * @param skillDto
     * @return {@link SkillDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public SkillDto addNewSkill(@NotNull SkillDto skillDto){
        LOGGER.info("Finished create a skill in service layer");

        Skill skill = skillMapper.skillDtoToSkill(skillDto);

        Skill skillSaved = skillRepository.save(skill);

        LOGGER.info("Finished create a skill in service layer");

        return skillMapper.skillToSkillDto(skillSaved);
    }

    /**
     * Update exist skill
     *
     * @param skillDto
     * @return {@link SkillDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public SkillDto updateSkill(@NotNull SkillDto skillDto) throws SkillNotFound{
        LOGGER.info("Finished update a skill in service layer");

        if(skillDto.getId()==0||!skillRepository.existsSkillById(skillDto.getId()))
        {
            throw new SkillNotFound("skill is not existed");
        }

        Skill skill = skillMapper.skillDtoToSkill(skillDto);

        Skill skillSaved = skillRepository.save(skill);

        LOGGER.info("Finished update a skill in service layer");

        return skillMapper.skillToSkillDto(skillSaved);
    }

    /**
     * Delete skill by id
     *
     * @param skillId
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteSkillById(@NotNull Long skillId) throws SkillNotFound {
        LOGGER.info("Started delete a skill in service layer");
        skillRepository.findById(skillId).ifPresentOrElse((skill) -> {
            skill.setDeleteFlag(true);
        }, () -> {
            throw new SkillNotFound("skill is not existed");
        });
        LOGGER.info("Finished delete a skill in service layer");
    }

    /**
     * Delete skill by username
     *
     * @param name
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteSkillByName(@NotBlank String name) throws SkillNotFound {
        LOGGER.info("Started delete a skill in service layer");
        skillRepository.findByName(name).ifPresentOrElse((skill) -> {
            skill.setDeleteFlag(true);
        }, () -> {
            throw new SkillNotFound("skill is not existed");
        });
        LOGGER.info("Finished delete a skill in service layer");
    }
}
