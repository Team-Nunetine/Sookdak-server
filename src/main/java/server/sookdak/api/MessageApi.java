package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.req.MessageRequestDto;
import server.sookdak.dto.res.message.MessageResponse;
import server.sookdak.dto.res.message.MessageSendResponse;
import server.sookdak.dto.res.message.MessageSendResponseDto;
import server.sookdak.service.MessageService;

import javax.validation.Valid;

import static server.sookdak.constants.SuccessCode.MESSAGE_REPLY_SUCCESS;
import static server.sookdak.constants.SuccessCode.MESSAGE_SEND_SUCCESS;

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
}
