package company.ejm.commoncode.api.service;

import company.ejm.commoncode.api.dto.CodeGroupDto;
import company.ejm.commoncode.api.dto.CommonCodeDto;
import company.ejm.commoncode.api.entity.CodeGroup;
import company.ejm.commoncode.api.entity.CommonCode;
import company.ejm.commoncode.api.exception.CustomException;
import company.ejm.commoncode.api.properties.ErrorCode;
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
        validateGroupName(reqCodeDto.getGroupName());

        Optional<CodeGroup> codeGroupOptional = codeGroupRepository.findByName(reqCodeDto.getGroupName());
        if (codeGroupOptional.isPresent()) {
            CodeGroup codeGroup = codeGroupOptional.get();

            List<CommonCode> commonCodeList = commonCodeRepository.findByCodeGroupName(codeGroup.getName());
            for (CommonCode commonCode : commonCodeList) {
                if (commonCode.getCode().equals(reqCodeDto.getCode())) {
                    throw new CustomException(ErrorCode.CODE_ALREADY_EXIST);
                }
            }

            CommonCode commonCode = CommonCode.builder()
                    .code(reqCodeDto.getCode())
                    .name(reqCodeDto.getName())
                    .codeGroup(codeGroup)
                    .build();

            commonCodeRepository.save(commonCode);
            return new CommonCodeDto(commonCode);
        } else {
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }
    }


    public CodeGroupDto createGroup(CodeGroupDto reqCodeDto) {
        validateDuplicateGroupName(reqCodeDto.getName());
        CodeGroup codeGroup = reqCodeDto.toEntity();
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
        throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
    }

    public CommonCodeDto updateCode(long id, CommonCodeDto reqCodeDto) {
        validateGroupName(reqCodeDto.getGroupName());
        Optional<CommonCode> commonCode = commonCodeRepository.findById(id);
        if(commonCode.isPresent()){
            Optional<CodeGroup> codeGroup = codeGroupRepository.findByName(reqCodeDto.getGroupName());
            if(codeGroup.isPresent()){
                commonCode.get().update(reqCodeDto, codeGroup.get());
                return new CommonCodeDto(commonCode.get());
            } else {
                throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
            }
        } else {
            throw new CustomException(ErrorCode.CODE_NOT_FOUND);
        }
    }



    public void deleteCodesInGroup(long groupId) {
        Optional<CodeGroup> codeGroup = codeGroupRepository.findById(groupId);
        codeGroup.ifPresent(group -> commonCodeRepository.deleteAllByCodeGroupId(group.getId()));
    }

    public void deleteCode(long id) {
        commonCodeRepository.deleteById(id);
    }

    private void validateGroupName(String groupName) {
        if (!codeGroupRepository.existsByName(groupName)) {
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }
    }

    private void validateDuplicateCode(CommonCodeDto reqCodeDto) {
        if (commonCodeRepository.existsByCode(reqCodeDto.getCode())) {
            throw new CustomException(ErrorCode.CODE_ALREADY_EXIST);
        }
        if (commonCodeRepository.existsByName(reqCodeDto.getName())) {
            throw new CustomException(ErrorCode.CODE_ALREADY_EXIST);
        }
    }

    private void validateDuplicateGroupName(String groupName) {
        if (codeGroupRepository.existsByName(groupName)) {
            throw new CustomException(ErrorCode.GROUP_NAME_ALREADY_EXIST);
        }
    }

}
