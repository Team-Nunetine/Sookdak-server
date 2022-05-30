package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
