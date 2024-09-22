package Mend.converter;

import Mend.dto.UserActivityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserActivityDTO toUserActivityDTO(int userId,String fullName, Long scanCount) {
        return new UserActivityDTO(userId,fullName, scanCount);
    }
}
