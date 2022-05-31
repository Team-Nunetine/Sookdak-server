package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.req.MessageRequestDto;
import server.sookdak.dto.res.message.*;
import server.sookdak.service.MessageService;

import javax.validation.Valid;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageApi {

    private final MessageService messageService;

    @PostMapping("/{postId}/save")
    public ResponseEntity<MessageSendResponse> sendMessage(@Valid @RequestBody MessageRequestDto messageRequestDto,
                                                           @PathVariable Long postId) {
        MessageSendResponseDto response = messageService.sendMessage(messageRequestDto, postId);

        return MessageSendResponse.newResponse(MESSAGE_SEND_SUCCESS, response);
    }

    @PostMapping("/{roomId}")
    public ResponseEntity<MessageResponse> replyMessage(@Valid @RequestBody MessageRequestDto messageRequestDto,
                                                        @PathVariable Long roomId) {
        messageService.replyMessage(messageRequestDto, roomId);

        return MessageResponse.newResponse(MESSAGE_REPLY_SUCCESS);
    }

    @GetMapping("")
    public ResponseEntity<MessageRoomResponse> getMessageRoom() {
        MessageRoomResponseDto response = messageService.getMessageRoom();

        return MessageRoomResponse.newResponse(MESSAGE_ROOM_READ_SUCCESS, response);
    }

    @GetMapping("/{roomId}/{page}")
    public ResponseEntity<MessageDetailResponse> getMessageDetail(@PathVariable Long roomId, @PathVariable int page) {
        MessageDetailResponseDto response = messageService.getMessageDetail(roomId, page);

        return MessageDetailResponse.newResponse(MESSAGE_READ_SUCCESS, response);
    }
}
