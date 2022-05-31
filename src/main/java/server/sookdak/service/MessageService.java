package server.sookdak.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.Message;
import server.sookdak.domain.MessageRoom;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;
import server.sookdak.dto.req.MessageRequestDto;
import server.sookdak.dto.res.message.MessageDetailResponseDto;
import server.sookdak.dto.res.message.MessageRoomResponseDto;
import server.sookdak.dto.res.message.MessageSendResponseDto;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.MessageRepository;
import server.sookdak.repository.MessageRoomRepository;
import server.sookdak.repository.PostRepository;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static server.sookdak.constants.ExceptionCode.*;
import static server.sookdak.dto.res.message.MessageDetailResponseDto.*;
import static server.sookdak.dto.res.message.MessageRoomResponseDto.*;
import static server.sookdak.dto.res.message.MessageRoomResponseDto.RoomList.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public MessageSendResponseDto sendMessage(MessageRequestDto messageRequestDto, Long postId) {
        User user = getUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (post.getUser() == user) {
            throw new CustomException(MESSAGE_DENIED);
        }

        Optional<MessageRoom> messageRoom = messageRoomRepository.findByUserAndPost(user, post);
        MessageRoom room;
        if (messageRoom.isEmpty()) {
            MessageRoom createdMessageRoom = MessageRoom.createMessageRoom(post, user);
            messageRoomRepository.save(createdMessageRoom);
            room = createdMessageRoom;
        } else {
            room = messageRoom.get();
        }

        Message message = Message.createMessage(room, user, messageRequestDto.getContent());
        messageRepository.save(message);

        return MessageSendResponseDto.of(room.getRoomId());
    }

    public void replyMessage(MessageRequestDto messageRequestDto, Long roomId) {
        User user = getUser();

        MessageRoom messageRoom = messageRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(MESSAGE_ROOM_NOT_FOUND));

        Message message = Message.createMessage(messageRoom, user, messageRequestDto.getContent());
        messageRepository.save(message);
    }

    public MessageRoomResponseDto getMessageRoom() {
        User user = getUser();

        List<MessageRoom> roomList = Stream.concat(messageRoomRepository.findByUser(user).stream(), messageRoomRepository.findByUserPost(user).stream())
                .collect(Collectors.toList());

        List<RoomList> rooms = roomList.stream().
                map(room -> of(room, messageRepository.findFirstByMessageRoomOrderByCreatedAtDesc(room)))
                .collect(Collectors.toList());

        Collections.sort(rooms);

        return MessageRoomResponseDto.of(rooms);
    }

    public MessageDetailResponseDto getMessageDetail(Long roomId, int page) {
        User user = getUser();

        MessageRoom messageRoom = messageRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(MESSAGE_ROOM_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 50);
        List<MessageDto> messages = messageRepository.findByMessageRoomOrderByCreatedAtDesc(messageRoom, pageRequest).stream()
                .map(message -> MessageDto.of(message, message.getSender().equals(user)))
                .collect(Collectors.toList());

        return MessageDetailResponseDto.of(messages);
    }

    private User getUser() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
