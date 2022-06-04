package kom.task.domain.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import kom.task.domain.repository.pomodoro.Pomodoro;
import kom.task.domain.repository.pomodoro.PomodoroRepository;
import kom.task.domain.repository.user.User;
import kom.task.domain.repository.user.UserRepository;
import kom.task.domain.web.dto.user.UserLoginResponseDto;
import kom.task.domain.web.dto.user.UserResponseDto;
import kom.task.global.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TodayFetchService todayFetchService;
    private final TokenService tokenService;
    private final PomodoroRepository pomodoroRepository;

    @Transactional
    public UserLoginResponseDto googleTokenLogin(String tokenDtoString) {
        String returnString;
        GoogleIdToken.Payload payload = tokenService.tokenVerify(tokenDtoString);

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Google Token Login");

            // Get profile information from payload
            String email = payload.getEmail();
            //boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            //String locale = (String) payload.get("locale");
            //String familyName = (String) payload.get("family_name");
            //String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...
            userRepository.findByUserId(userId)
                    .map(entity -> entity.update(name, pictureUrl))
                    .orElseGet(() -> userRepository.save(User.builder()
                            .userId(userId)
                            .name(name)
                            .email(email)
                            .pictureUrl(pictureUrl)
                            .build()));

            pomodoroRepository.findByUserId(userId)
                    .orElseGet(() -> pomodoroRepository.save(new Pomodoro(userId)));

            returnString = userId;
        }
        else {
            returnString = "invalid token";
        }

        return new UserLoginResponseDto(returnString);
    }

    @Transactional
    public UserResponseDto fetchUserInfo(String userId) {
        // 오늘의 패치 진행
        todayFetchService.todayFetch(userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("id not found"));

        return new UserResponseDto(user);
    }
}
