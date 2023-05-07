package edu.msudenver.chat.groupchat;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/chat/groups/")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;


    @GetMapping(path = "/{groupId}", produces = "application/json")
    public ResponseEntity<List<GroupChat>> getGroupChatFromDate(@PathVariable Long groupId, @RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end)
    {
        try {
            List<GroupChat> groupChatList = groupChatService.getGroupChats(groupId, start, end);
            return new ResponseEntity<>(groupChatList, groupChatList == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/{groupId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GroupChat> createGroupChat(@RequestBody GroupChat groupChat) {
        try {
            return new ResponseEntity<>(groupChatService.saveGroupChat(groupChat), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{groupId}/users/{user}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GroupChat> updateGroupChat(@PathVariable Long groupId, @RequestBody GroupChat updatedGroupChat) {
        List<GroupChat> retrievedGroupChat = groupChatService.getAllMessages(groupId);
        if (retrievedGroupChat != null) {
            try {
                return ResponseEntity.ok(groupChatService.saveGroupChat(updatedGroupChat));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{groupId}")
    public ResponseEntity<Void> deleteGroupChat(@PathVariable Long groupId, @RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            return new ResponseEntity<>(groupChatService.deleteGroupChat(groupId, start, end) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }
}