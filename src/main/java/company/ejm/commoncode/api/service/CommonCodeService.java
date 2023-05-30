package company.ejm.commoncode.api.service;

import company.ejm.commoncode.api.dto.CodeGroupDto;
import company.ejm.commoncode.api.dto.CommonCodeDto;
import company.ejm.commoncode.api.entity.CodeGroup;
import company.ejm.commoncode.api.entity.CommonCode;
import company.ejm.commoncode.api.repository.CodeGroupRepository;
import company.ejm.commoncode.api.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;
    private final CodeGroupRepository codeGroupRepository;

    public CommonCodeDto createCode(CommonCodeDto reqCodeDto) {
        validateGroupName(reqCodeDto.getGroupDto().getName());
        validateDuplicateCode(reqCodeDto);
        CommonCode commonCode = reqCodeDto.toEntity();
        commonCodeRepository.save(commonCode);
        return new CommonCodeDto(commonCode);
    }

    public CodeGroupDto createGroup(CodeGroupDto reqGroupDto) {
        validateGroupName(reqGroupDto.getName());
        validateDuplicateGroup(reqGroupDto);
        CodeGroup codeGroup = reqGroupDto.toEntity();
        codeGroupRepository.save(codeGroup);
        return new CodeGroupDto(codeGroup);
    }

    @Transactional(readOnly = true)
    public List<CodeGroupDto> getCodeGroups() {
        List<CodeGroup> codeGroupList = codeGroupRepository.findAll();
        List<CodeGroupDto> codeGroupDtoList = new ArrayList<>();
        for(CodeGroup codeGroup : codeGroupList){
            codeGroupDtoList.add(new CodeGroupDto(codeGroup));
        }
        return codeGroupDtoList;
    }

    @Transactional(readOnly = true)
    public CommonCodeDto getCodeById(long id) {
        Optional<CommonCode> commonCode = commonCodeRepository.findById(id);
        return commonCode.map(CommonCodeDto::new).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<CommonCodeDto> getCodesByGroupName(String groupName) {
        List<CommonCode> commonCodeList = commonCodeRepository.findByCodeGroupName(groupName);
        List<CommonCodeDto> commonCodeDtoList = new ArrayList<>();
        for(CommonCode commonCode : commonCodeList){
            commonCodeDtoList.add(new CommonCodeDto(commonCode));
        }
        return commonCodeDtoList;
    }

    public CodeGroupDto updateGroupName(long groupId, String groupName) {
        Optional<CodeGroup> codeGroup = codeGroupRepository.findById(groupId);
        if(codeGroup.isPresent()){
            codeGroup.get().updateName(groupName);
            //todo 변경된 값으로 return 되는지 테스트
            return new CodeGroupDto(codeGroup.get());
        }
        throw new IllegalArgumentException("Group not exist");
    }

    public CommonCodeDto updateCode(long id, CommonCodeDto reqCodeDto) {
        validateGroupName(reqCodeDto.getGroupDto().getName());
        Optional<CommonCode> commonCode = commonCodeRepository.findById(id);
        if(commonCode.isPresent()){
            commonCode.get().update(reqCodeDto);
            return new CommonCodeDto(commonCode.get());
        }
        throw new IllegalArgumentException("Code not exist");
    }


    public void deleteCodesInGroup(String groupName) {
        Optional<CodeGroup> codeGroup = codeGroupRepository.findByName(groupName);
        codeGroup.ifPresent(group -> commonCodeRepository.deleteAllByCodeGroupId(group.getId()));
    }

    public void deleteCode(long id) {
        commonCodeRepository.deleteById(id);
    }

    private void validateGroupName(String groupName) {
        if (!codeGroupRepository.existsByName(groupName)) {
            throw new IllegalArgumentException("Invalid group name: " + groupName);
        }
    }

    private void validateDuplicateCode(CommonCodeDto reqCodeDto) {
        if (commonCodeRepository.existsById(reqCodeDto.getId()) ||
                commonCodeRepository.existsByCode(reqCodeDto.getCode()) ||
                commonCodeRepository.existsByName(reqCodeDto.getName())) {
            throw new IllegalArgumentException("Code already exists: " + reqCodeDto.getCode() + ", " + reqCodeDto.getName());
        }
    }

    private void validateDuplicateGroup(CodeGroupDto reqGroupDto) {
        if (codeGroupRepository.existsById(reqGroupDto.getId()) ||
                codeGroupRepository.existsByName(reqGroupDto.getName())) {
            throw new IllegalArgumentException("Group already exists: " + reqGroupDto.getName());
        }
    }
}
