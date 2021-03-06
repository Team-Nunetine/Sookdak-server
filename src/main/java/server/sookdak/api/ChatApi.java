package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.sookdak.domain.Chat;
import server.sookdak.dto.req.ChatRequsetDto;
import server.sookdak.dto.req.ChatRoomSaveRequestDto;
import server.sookdak.dto.res.chat.*;
import server.sookdak.service.ChatService;

import javax.validation.Valid;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatApi {
    private final ChatService chatService;

    @GetMapping("/chatroom")
    public ResponseEntity<ChatRoomListResponse> findAll() {
        ChatRoomListResponseDto responseDto = chatService.findAllDesc();
        return ChatRoomListResponse.newResponse(CHATROOM_READ_SUCCESS, responseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<ChatRoomResponse> save(@Valid @RequestBody ChatRoomSaveRequestDto chatRoomSaveRequestDto) {
        ChatRoomResponseDto responseDto = chatService.saveChatRoom(chatRoomSaveRequestDto);
        return ChatRoomResponse.newResponse(CHATROOM_CREATE_SUCCESS, responseDto);
    }


    @MessageMapping("/{roomId}")
    @SendTo("/room/{roodId}")
    public ChatResponseDto send(@DestinationVariable Long roomId, ChatRequsetDto chatRequsetDto) {
        Chat chat = chatService.sendChat(roomId,chatRequsetDto.getContent());
        return ChatResponseDto.of(chat);
    }

    @PostMapping("/join/{roomId}")
    public ResponseEntity<ChatRoomResponse> roomJoin(@PathVariable Long roomId) {
        ChatRoomResponseDto responseDto = chatService.joinChatRoom(roomId);
        return ChatRoomResponse.newResponse(CHATROOM_JOIN_SUCCESS, responseDto);

    }

    @DeleteMapping("/quit/{roomId}")
    public ResponseEntity<ChatStatusResponse> quitChatRoom(@PathVariable Long roomId) {
        chatService.quitChatRoom(roomId);
        return ChatStatusResponse.newResponse(CHATROOM_QUIT_SUCCESS);
    }


}
