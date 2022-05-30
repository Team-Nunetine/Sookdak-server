package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.ChatRoom;
import server.sookdak.domain.User;
import server.sookdak.dto.req.ChatRoomSaveRequestDto;
import server.sookdak.dto.res.chat.ChatRoomListResponseDto;
import server.sookdak.dto.res.chat.ChatRoomListResponseDto.ChatRoomList;
import server.sookdak.dto.res.chat.ChatRoomResponseDto;
import server.sookdak.exception.CustomException;

import server.sookdak.repository.ChatRoomRepository;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static server.sookdak.constants.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ChatRoomListResponseDto findAllDesc(){
        List<ChatRoomList> rooms = chatRoomRepository.findAllDesc().stream()
                .map(ChatRoomList::new)
                .collect(Collectors.toList());
        return ChatRoomListResponseDto.of(rooms);
    }

    public ChatRoomResponseDto saveChatRoom(ChatRoomSaveRequestDto chatRoomSaveRequestDto) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        ChatRoom chatRoom = ChatRoom.createChatRoom(user, chatRoomSaveRequestDto.getName(), chatRoomSaveRequestDto.getInfo(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        chatRoomRepository.save(chatRoom);

        return ChatRoomResponseDto.of(chatRoom);

    }

}
