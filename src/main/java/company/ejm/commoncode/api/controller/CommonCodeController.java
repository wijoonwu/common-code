package company.ejm.commoncode.api.controller;

import company.ejm.commoncode.api.dto.CodeGroupDto;
import company.ejm.commoncode.api.dto.CommonCodeDto;
import company.ejm.commoncode.api.entity.CodeGroup;
import company.ejm.commoncode.api.properties.Message;
import company.ejm.commoncode.api.properties.StatusEnum;
import company.ejm.commoncode.api.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/common-codes")
@RequiredArgsConstructor
@RestController
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @PostMapping
    public ResponseEntity<Message> createCode(@RequestBody CommonCodeDto reqCodeDto) {
        CommonCodeDto resCodeDto = commonCodeService.createCode(reqCodeDto);
        Message message = Message.builder()
                .status(StatusEnum.CREATED)
                .message("Code creation successful.")
                .data(resCodeDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }

    @PostMapping("/group")
    public ResponseEntity<Message> createCodeGroup(@RequestBody CodeGroupDto reqGroupDto) {
        CodeGroupDto resGroupDto = commonCodeService.createGroup(reqGroupDto);
        Message message = Message.builder()
                .status(StatusEnum.CREATED)
                .message("CodeGroup creation successful.")
                .data(resGroupDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getCode(@PathVariable long id){
        CommonCodeDto commonCodeDto = commonCodeService.getCodeById(id);
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("Code retrieval successful.")
                .data(commonCodeDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/group/{groupName}")
    public ResponseEntity<Message> getCodesByGroup(@PathVariable String groupName){
        List<CommonCodeDto> commonCodeDtoList = commonCodeService.getCodesByGroupName(groupName);
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("Group codes retrieval successful.")
                .data(commonCodeDtoList)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping("/groups")
    public ResponseEntity<Message> getGroups(){
        List<CodeGroupDto> codeGroupDtoList = commonCodeService.getCodeGroups();
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("Group retrieval successful.")
                .data(codeGroupDtoList)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PutMapping("/group/{groupId}")
    public ResponseEntity<Message> updateGroupName(@PathVariable long groupId, @RequestBody String groupName) {
        CodeGroupDto resCodeGroupDto = commonCodeService.updateGroupName(groupId, groupName);
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("Group name update successful.")
                .data(resCodeGroupDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateCode(@PathVariable long id, @RequestBody CommonCodeDto reqCodeDto) {
        CommonCodeDto resCodeDto = commonCodeService.updateCode(id, reqCodeDto);
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("Code update successful.")
                .data(resCodeDto)
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @DeleteMapping("/group")
    public ResponseEntity<Message> deleteCodesInGroup(@RequestBody CodeGroupDto reqGroupDto) {
        commonCodeService.deleteCodesInGroup(reqGroupDto.getName());
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("Codes deletion in group successful.")
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteCode(@PathVariable long id) {
        commonCodeService.deleteCode(id);
        Message message = Message.builder()
                .status(StatusEnum.OK)
                .message("Code deletion successful.")
                .build();
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
