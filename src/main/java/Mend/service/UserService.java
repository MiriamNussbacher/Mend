package Mend.service;

import Mend.converter.UserMapper;
import Mend.dto.UserActivityDTO;
import Mend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserActivityDTO> getTopActiveUsers(int limit) {
        Pageable topTen = PageRequest.of(0, limit);

        // Fetch the top 10 users from the repository
        List<Object[]> result = userRepository.findTopActiveUsers(topTen);

        // Convert the result to UserActivityDTO
        List<UserActivityDTO> userActivityList = new ArrayList<>();
        for (Object[] row : result) {
            int userId= (int) row[0];
            String fullName = (String) row[1];
            Long scanCount = (Long) row[2];

            UserActivityDTO dto = userMapper.toUserActivityDTO(userId,fullName, scanCount);
            userActivityList.add(dto);
        }

        return userActivityList;
    }
}

