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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;
    private final CodeGroupRepository codeGroupRepository;

    public CommonCodeDto createCode(CommonCodeDto reqCodeDto) {
        CodeGroup codeGroup = getCodeGroup(reqCodeDto.getGroupName());

        checkIfCodeExists(reqCodeDto.getCode(), codeGroup.getName());

        CommonCode commonCode = CommonCode.builder()
                .code(reqCodeDto.getCode())
                .name(reqCodeDto.getName())
                .codeGroup(codeGroup)
                .build();

        commonCodeRepository.save(commonCode);
        return new CommonCodeDto(commonCode);
    }

    public CodeGroupDto createGroup(CodeGroupDto reqCodeDto) {
        checkDuplicateGroupName(reqCodeDto.getName());
        CodeGroup codeGroup = reqCodeDto.toEntity();
        codeGroupRepository.save(codeGroup);
        return new CodeGroupDto(codeGroup);
    }

    @Transactional(readOnly = true)
    public List<CodeGroupDto> getCodeGroups() {
        return codeGroupRepository.findAll().stream()
                .map(CodeGroupDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommonCodeDto getCodeById(long id) {
        CommonCode commonCode = commonCodeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));
        return new CommonCodeDto(commonCode);
    }

    @Transactional(readOnly = true)
    public List<CommonCodeDto> getCodesByGroupName(String groupName) {
        List<CommonCode> commonCodeList = commonCodeRepository.findByCodeGroupName(groupName);

        if (commonCodeList.isEmpty()){
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }

        return commonCodeList.stream()
                .map(CommonCodeDto::new)
                .collect(Collectors.toList());
    }

    public CodeGroupDto updateGroupName(long groupId, String groupName) {
        CodeGroup codeGroup = codeGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        codeGroup.updateName(groupName);
        return new CodeGroupDto(codeGroup);
    }

    public CommonCodeDto updateCode(long id, CommonCodeDto reqCodeDto) {
        validateGroupName(reqCodeDto.getGroupName());

        CommonCode commonCode = commonCodeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));

        CodeGroup codeGroup = getCodeGroup(reqCodeDto.getGroupName());
        commonCode.update(reqCodeDto, codeGroup);
        return new CommonCodeDto(commonCode);
    }

    public void deleteCodesInGroup(long groupId) {
        CodeGroup codeGroup = codeGroupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        commonCodeRepository.deleteAllByCodeGroupId(codeGroup.getId());
    }

    public void deleteCode(long id) {
        commonCodeRepository.deleteById(id);
    }

    private CodeGroup getCodeGroup(String groupName) {
        validateGroupName(groupName);

        return codeGroupRepository.findByName(groupName)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
    }

    private void checkIfCodeExists(String code, String groupName) {
        boolean codeExists = commonCodeRepository.findByCodeGroupName(groupName).stream()
                .anyMatch(commonCode -> commonCode.getCode().equals(code));

        if (codeExists) {
            throw new CustomException(ErrorCode.CODE_ALREADY_EXIST);
        }
    }

    private void checkDuplicateGroupName(String groupName) {
        if (codeGroupRepository.existsByName(groupName)) {
            throw new CustomException(ErrorCode.GROUP_NAME_ALREADY_EXIST);
        }
    }

    private void validateGroupName(String groupName) {
        if (!codeGroupRepository.existsByName(groupName)) {
            throw new CustomException(ErrorCode.GROUP_NOT_FOUND);
        }
    }
}
