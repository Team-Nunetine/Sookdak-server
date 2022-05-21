package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom{
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "이름을 입력해주세요")
    @Column(length = 20)
    private String name;

    private String info;

    private String createdAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "chatter", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "user_Id"))
    private List<User> users = new ArrayList<>();

    public static ChatRoom createChatRoom(User user, String name, String info, String createdAt){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.user = user;
        chatRoom.name = name;
        chatRoom.info = info;
        chatRoom.createdAt = createdAt;
        return chatRoom;
    }

}
