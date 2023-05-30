package company.ejm.commoncode.api.controller;

import company.ejm.commoncode.api.dto.CodeGroupDto;
import company.ejm.commoncode.api.dto.CommonCodeDto;
import company.ejm.commoncode.api.exception.CustomException;
import company.ejm.commoncode.api.properties.ErrorCode;
import company.ejm.commoncode.api.properties.Message;
import company.ejm.commoncode.api.properties.StatusEnum;
import company.ejm.commoncode.api.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Message> createCodeGroup(@RequestBody @Valid CodeGroupDto reqCodeDto,
                                                   Errors errors) {
        checkEmpty(errors);
        CodeGroupDto resGroupDto = commonCodeService.createGroup(reqCodeDto);
        Message message = Message.builder()
                .code(StatusEnum.CREATED.getStatusCode())
                .message("Group creation successful.")
                .data(resGroupDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Message> createCode(@RequestBody CommonCodeDto reqCodeDto) {
        CommonCodeDto resCodeDto = commonCodeService.createCode(reqCodeDto);
        Message message = Message.builder()
                .code(StatusEnum.CREATED.getStatusCode())
                .message("Code creation successful.")
                .data(resCodeDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{codeId}")
    public ResponseEntity<Message> getCode(@PathVariable long codeId){
        CommonCodeDto commonCodeDto = commonCodeService.getCodeById(codeId);
        Message message = Message.builder()
                .code(StatusEnum.OK.getStatusCode())
                .message("Code retrieval successful.")
                .data(commonCodeDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/group/{groupName}")
    public ResponseEntity<Message> getCodesByGroup(@PathVariable String groupName){
        List<CommonCodeDto> commonCodeDtoList = commonCodeService.getCodesByGroupName(groupName);
        Message message = Message.builder()
                .code(StatusEnum.OK.getStatusCode())
                .message("Group codes retrieval successful.")
                .data(commonCodeDtoList)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/groups")
    public ResponseEntity<Message> getGroups(){
        List<CodeGroupDto> codeGroupDtoList = commonCodeService.getCodeGroups();
        Message message = Message.builder()
                .code(StatusEnum.OK.getStatusCode())
                .message("Group retrieval successful.")
                .data(codeGroupDtoList)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PutMapping("/group/{groupId}")
    public ResponseEntity<Message> updateGroupName(@PathVariable long groupId, @RequestBody CodeGroupDto reqCodeDto) {
        CodeGroupDto resCodeGroupDto = commonCodeService.updateGroupName(groupId, reqCodeDto.getName());
        Message message = Message.builder()
                .code(StatusEnum.OK.getStatusCode())
                .message("Group name update successful.")
                .data(resCodeGroupDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PutMapping("/{codeId}")
    public ResponseEntity<Message> updateCode(@PathVariable long codeId, @RequestBody CommonCodeDto reqCodeDto) {
        CommonCodeDto resCodeDto = commonCodeService.updateCode(codeId, reqCodeDto);
        Message message = Message.builder()
                .code(StatusEnum.OK.getStatusCode())
                .message("Code update successful.")
                .data(resCodeDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Message> deleteCodesInGroup(@PathVariable long groupId) {
        commonCodeService.deleteCodesInGroup(groupId);
        Message message = Message.builder()
                .code(StatusEnum.OK.getStatusCode())
                .message("Codes deletion in group successful.")
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{codeId}")
    public ResponseEntity<Message> deleteCode(@PathVariable long codeId) {
        commonCodeService.deleteCode(codeId);
        Message message = Message.builder()
                .code(StatusEnum.OK.getStatusCode())
                .message("Code deletion successful.")
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    private void checkEmpty(Errors errors) {
        if(errors.hasErrors()){
            throw new CustomException(ErrorCode.CANNOT_BE_EMPTY);
        }
    }
}
