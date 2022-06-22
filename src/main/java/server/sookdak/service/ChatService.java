package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.Chat;
import server.sookdak.domain.ChatRoom;
import server.sookdak.domain.User;
import server.sookdak.dto.req.ChatRoomSaveRequestDto;
import server.sookdak.dto.res.chat.ChatRoomListResponseDto;
import server.sookdak.dto.res.chat.ChatRoomListResponseDto.ChatRoomList;
import server.sookdak.dto.res.chat.ChatRoomResponseDto;
import server.sookdak.exception.CustomException;

import server.sookdak.repository.ChatRepository;
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
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ChatRoomListResponseDto findAllDesc(){
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        List<ChatRoomList> rooms = chatRoomRepository.findAllDesc().stream()
                .map(chatRoom -> ChatRoomList.of(chatRoom,
                        chatRoom.getUsers().contains(user)))
                .collect(Collectors.toList());
        return ChatRoomListResponseDto.of(rooms);
    }

    public ChatRoomResponseDto saveChatRoom(ChatRoomSaveRequestDto chatRoomSaveRequestDto) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        ChatRoom chatRoom = ChatRoom.createChatRoom(user, chatRoomSaveRequestDto.getName(), chatRoomSaveRequestDto.getInfo(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        chatRoomRepository.save(chatRoom);
        chatRoom.getUsers().add(user);

        return ChatRoomResponseDto.of(chatRoom);

    }

    public Chat sendChat(Long roomId, String msg) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
        return chatRepository.save(Chat.createChat(user, chatRoom, msg, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))));
    }

    public List<Chat> showChat(Long roomId){
        return chatRepository.findAllByChatRoom(roomId);
    }

    public ChatRoomResponseDto joinChatRoom(Long roomId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()-> new CustomException(ROOM_NOT_FOUND));
        boolean  existUser = chatRoom.getUsers().contains(user);
        if(existUser) {
            throw new CustomException(ALREADY_JOIN);
        }
        chatRoom.getUsers().add(user);
        return ChatRoomResponseDto.of(chatRoom);
    }

    public void quitChatRoom(Long roomId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(()-> new CustomException(ROOM_NOT_FOUND));
        if (chatRoom.getUsers().contains(user)) {
            chatRoom.getUsers().remove(user);
        }else {
            throw new CustomException(ROOM_NOT_FOUND);
        }
    }


}
