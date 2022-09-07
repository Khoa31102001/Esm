package com.stdio.esm.service;

import com.stdio.esm.dto.SkillDto;
import com.stdio.esm.dto.SkillTypesDto;
import com.stdio.esm.dto.response.SkillTypesResponse;
import com.stdio.esm.exception.SkillTypesNotFound;
import com.stdio.esm.model.SkillTypes;
import com.stdio.esm.model.mapper.SkillTypesMapper;
import com.stdio.esm.repository.SkillTypeRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkillTypesService {
    @Autowired
    private SkillTypeRepository skillTypeRepository;

    @Autowired
    private SkillTypesMapper skillTypesMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillTypesService.class);


    /**
     * Get all list skillTypes
     *
     * @return {@link List<SkillTypesDto>>}
     */

    @Transactional(readOnly = true)
    public Map<String, List<SkillTypesResponse>> getAllListSkillTypes() {
        LOGGER.info("Started get all list skilltypes in service layer");
        Map<String, List<SkillTypesResponse>> responseData = new HashMap<>();
        responseData.put("skillTypes", skillTypesMapper.skillTypesListToSkillTypesResponseList(skillTypeRepository.findAll()));
        LOGGER.info("Finished get all list skilltypes in service layer");
        return responseData;
    }

    /**
     * Get skillTypes from id
     *
     * @param skillTypesId
     * @return {@link SkillTypesDto}
     */
    @Transactional(readOnly = true)
    public SkillTypesDto getSkillTypesById(@NotNull  Long skillTypesId) throws SkillTypesNotFound{
        LOGGER.info("Started get skillTypes by id in service layer");
        SkillTypes skillTypes = skillTypeRepository.findById(skillTypesId).orElseThrow(()-> new SkillTypesNotFound("SkillTypes is not exits"));
        LOGGER.info("Finished get skillTypes by id in service layer");
        return skillTypesMapper.skillTypesToSkillTypesDto(skillTypes);
    }

    /**
     * Get skillTypes from name
     *
     * @param name
     * @return {@link SkillTypesDto}
     */
    @Transactional(readOnly = true)
    public SkillTypesDto getSkillTypesByName(@NotBlank String name) throws SkillTypesNotFound{
        LOGGER.info("Started get skillTypes by name in service layer");
        SkillTypes skillTypes = skillTypeRepository.findByName(name).orElseThrow(()-> new SkillTypesNotFound("SkillTypes is not exits"));
        LOGGER.info("Finished get skillTypes by name in service layer");
        return skillTypesMapper.skillTypesToSkillTypesDto(skillTypes);
    }

    /**
     * create new skillTypes
     *
     * @param skillTypesDto
     * @return {@link SkillDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public SkillTypesDto addNewSkillTypes(@NotNull SkillTypesDto skillTypesDto){
        LOGGER.info("Finished create a skillTypes in service layer");

        SkillTypes skillTypes = skillTypesMapper.skillTypesDtoToSkillTypes(skillTypesDto);

        SkillTypes skillTypesSaved = skillTypeRepository.save(skillTypes);

        LOGGER.info("Finished create a skillTypes in service layer");

        return skillTypesMapper.skillTypesToSkillTypesDto(skillTypesSaved);
    }

    /**
     * Update exist skillTypes
     *
     * @param skillTypesDto
     * @return {@link SkillTypesDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public SkillTypesDto updateskillTypes(@NotNull SkillTypesDto skillTypesDto) throws SkillTypesNotFound{
        LOGGER.info("Finished update a skillTypes in service layer");

        if(skillTypesDto.getId()==0||!skillTypeRepository.existsSkillTypesById(skillTypesDto.getId()))
        {
            throw new SkillTypesNotFound("skillTypes is not existed");
        }


        SkillTypes skillTypes = skillTypesMapper.skillTypesDtoToSkillTypes(skillTypesDto);

        SkillTypes skillTypesSaved = skillTypeRepository.save(skillTypes);

        LOGGER.info("Finished create a skillTypes in service layer");

        return skillTypesMapper.skillTypesToSkillTypesDto(skillTypesSaved);
    }

    /**
     * Delete skillTypes by id
     *
     * @param skillTypesId
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteSkillTypesById(@NotNull Long skillTypesId) throws SkillTypesNotFound {
        LOGGER.info("Started delete a skillTypes in service layer");
        skillTypeRepository.findById(skillTypesId).ifPresentOrElse((skillTypes) -> {
            skillTypeRepository.delete(skillTypes);
        }, () -> {
            throw new SkillTypesNotFound("skillTypes is not existed");
        });
        LOGGER.info("Finished delete a skillTypes in service layer");
    }

    /**
     * Delete skillTypes by username
     *
     * @param name
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteSkillTypesByName(@NotBlank String name) throws SkillTypesNotFound {
        LOGGER.info("Started delete a skillTypes in service layer");
        skillTypeRepository.findByName(name).ifPresentOrElse((skillTypes) -> {
            skillTypeRepository.delete(skillTypes);
        }, () -> {
            throw new SkillTypesNotFound("skillTypes is not existed");
        });
        LOGGER.info("Finished delete a skillTypes in service layer");
    }
}
