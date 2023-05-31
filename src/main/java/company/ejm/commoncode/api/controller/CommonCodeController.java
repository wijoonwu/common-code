package company.ejm.commoncode.api.controller;

import company.ejm.commoncode.api.dto.CodeGroupDto;
import company.ejm.commoncode.api.dto.CommonCodeDto;
import company.ejm.commoncode.api.exception.CustomException;
import company.ejm.commoncode.api.model.ErrorCode;
import company.ejm.commoncode.api.model.Message;
import company.ejm.commoncode.api.model.StatusEnum;
import company.ejm.commoncode.api.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/common-codes")
@RequiredArgsConstructor
@RestController
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @PostMapping("/group")
    public ResponseEntity<Message<CodeGroupDto>> createCodeGroup(@RequestBody @Valid CodeGroupDto reqCodeDto,
                                                                 Errors errors) {
        checkEmpty(errors);
        CodeGroupDto resGroupDto = commonCodeService.createGroup(reqCodeDto);
        return createResponseEntity(StatusEnum.CREATED, "Group creation successful.", resGroupDto);
    }

    @PostMapping
    public ResponseEntity<Message<CommonCodeDto>> createCode(@RequestBody CommonCodeDto reqCodeDto) {
        CommonCodeDto resCodeDto = commonCodeService.createCode(reqCodeDto);
        return createResponseEntity(StatusEnum.CREATED, "Code creation successful.", resCodeDto);
    }

    @GetMapping("/{codeId}")
    public ResponseEntity<Message<CommonCodeDto>> getCode(@PathVariable long codeId) {
        CommonCodeDto commonCodeDto = commonCodeService.getCodeById(codeId);
        return createResponseEntity(StatusEnum.OK, "Code retrieval successful.", commonCodeDto);
    }

    @GetMapping("/group/{groupName}")
    public ResponseEntity<Message<List<CommonCodeDto>>> getCodesByGroup(@PathVariable String groupName) {
        List<CommonCodeDto> commonCodeDtoList = commonCodeService.getCodesByGroupName(groupName);
        return createResponseEntity(StatusEnum.OK, "Group codes retrieval successful.", commonCodeDtoList);
    }

    @GetMapping("/groups")
    public ResponseEntity<Message<List<CodeGroupDto>>> getGroups() {
        List<CodeGroupDto> codeGroupDtoList = commonCodeService.getCodeGroups();
        return createResponseEntity(StatusEnum.OK, "Group retrieval successful.", codeGroupDtoList);
    }

    @PutMapping("/group/{groupId}")
    public ResponseEntity<Message<CodeGroupDto>> updateGroupName(@PathVariable long groupId, @RequestBody CodeGroupDto reqCodeDto) {
        CodeGroupDto resCodeGroupDto = commonCodeService.updateGroupName(groupId, reqCodeDto.getName());
        return createResponseEntity(StatusEnum.OK, "Group name update successful.", resCodeGroupDto);
    }

    @PutMapping("/{codeId}")
    public ResponseEntity<Message<CommonCodeDto>> updateCode(@PathVariable long codeId, @RequestBody CommonCodeDto reqCodeDto) {
        CommonCodeDto resCodeDto = commonCodeService.updateCode(codeId, reqCodeDto);
        return createResponseEntity(StatusEnum.OK, "Code update successful.", resCodeDto);
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Message<Void>> deleteCodesInGroup(@PathVariable long groupId) {
        commonCodeService.deleteCodesInGroup(groupId);
        return createResponseEntity(StatusEnum.OK, "Codes deletion in group successful.", null);
    }

    @DeleteMapping("/{codeId}")
    public ResponseEntity<Message<Void>> deleteCode(@PathVariable long codeId) {
        commonCodeService.deleteCode(codeId);
        return createResponseEntity(StatusEnum.OK, "Code deletion successful.", null);
    }

    private void checkEmpty(Errors errors) {
        if (errors.hasErrors()) {
            throw new CustomException(ErrorCode.CANNOT_BE_EMPTY);
        }
    }

    private <T> ResponseEntity<Message<T>> createResponseEntity(StatusEnum status, String messageText, T data) {
        Message<T> message = Message.<T>builder()
                .code(status.getStatusCode())
                .message(messageText)
                .data(data)
                .build();
        return new ResponseEntity<Message<T>>(message, status.getHttpStatus());
    }
}

