package server.sookdak.dto.res.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Authority;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private String email;
    private Authority authority;

    public static UserResponseDto toUserResponseDto(String email, Authority authority) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.email = email;
        userDto.authority = authority;
        return userDto;
    }
}
