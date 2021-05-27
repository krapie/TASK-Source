package kom.task.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import kom.task.domain.dailydo.daydo.Daydo;
import kom.task.domain.dailydo.daydo.DaydoRepository;
import kom.task.domain.dailydo.todo.Todo;
import kom.task.domain.dailydo.todo.TodoRepository;
import kom.task.domain.pomodoro.Pomodoro;
import kom.task.domain.pomodoro.PomodoroRepository;
import kom.task.domain.user.User;
import kom.task.domain.user.UserRepository;
import kom.task.web.dto.daydo.DaydoResponseDto;
import kom.task.web.dto.daydo.DaydoSaveRequestDto;
import kom.task.web.dto.daydo.DaydoUpdateRequestDto;
import kom.task.web.dto.pomodoro.PomodoroResponseDto;
import kom.task.web.dto.pomodoro.PomodoroUpdateRequestDto;
import kom.task.web.dto.todo.TodoResponseDto;
import kom.task.web.dto.todo.TodoSaveRequestDto;
import kom.task.web.dto.todo.TodoUpdateRequestDto;
import kom.task.web.dto.user.UserLoginResponseDto;
import kom.task.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

@RequiredArgsConstructor
@Service
public class TaskService {

    private static final String CLIENT_ID = "855394650411-buaopph1kokclaq6l9i8tirma6u2svf0.apps.googleusercontent.com";
    private final TodoRepository todoRepository;
    private final DaydoRepository daydoRepository;
    private final UserRepository userRepository;
    private final PomodoroRepository pomodoroRepository;

    /*** FETCH LOGICS ***/
    public void todayFetch(String userId) {
        LocalTime sixAM = LocalTime.of(6,0,0);

        // User 엔티티 가져오기
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("userId not found"));

        // 호출된 시점의 요일을 구해서
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        /*** TodoItem 갱신 ***/
        // User에 저장된 todoUpdatedTime 와 날짜가 다르고 && 현재 시간이 오전 6시 이후이면
        if(!user.getTodoUpdatedDate().isEqual(nowDate) && nowTime.isAfter(sixAM)) {
            System.out.println("todayTodoItemFetch");

            // 해당 userId를 가진 모든 todoItem 삭제
            todoRepository.deleteAllByUserId(userId);

            // 해당 요일에 해당하는 튜플들을 DaydoRepository에서 추출
            int todayDay = nowDate.getDayOfWeek().getValue(); // Monday - Sunday : 1 ~ 7

            List<Daydo> daydoList = daydoRepository.findAllByUserId(userId);
            List<Daydo> todayDaydoList = daydoList.stream().filter(daydo -> daydo.getDay() == todayDay).collect(Collectors.toList());

            // To-do 아이템으로 변환 후 TodoRepository에 저장
            for (Daydo daydo : todayDaydoList) {
                Todo todoEntity = Todo.builder()
                        .userId(userId)
                        .content(daydo.getContent())
                        .isDone(false)
                        .build();

                todoRepository.save(todoEntity);
            }

            // User todoUpdatedTime 업데이트
            user.updateTodoUpdatedDate(nowDate);
        }

        /*** Pomodoro 갱신 ***/
        // User에 저장된 pomodoroUpdatedDate 와 날짜가 다르고 && 현재 시간이 오전 6시 이후이면
        if(!user.getPomodoroUpdatedDate().isEqual(nowDate) && nowTime.isAfter(sixAM)) {
            System.out.println("todayPomodoroFetch");

            // Pomodoro 엔티티 가져오기
            Pomodoro pomodoro = pomodoroRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("userId not found"));

            // pomo 업데이트
            pomodoro.updatePomo();

            // User pomodoroUpdatedTime 업데이트
            user.updatePomodoroUpdatedDate(nowDate);
        }
    }

    /*** TOKEN VERIFY ***/
    public Payload TokenVerify(String tokenDtoString) {
        Payload payload = null;

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)
        try {
            GoogleIdToken idToken = verifier.verify(tokenDtoString);
            if (idToken != null) {
                payload = idToken.getPayload();
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException e) {
            System.out.println(e.getStackTrace());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return payload;
    }


    /*** LOGIN REST SERVICE ***/
    public UserLoginResponseDto googleTokenLogin(String tokenDtoString) {
        String returnString = "";
        Payload payload = TokenVerify(tokenDtoString);

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Google Token Login");

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

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

            returnString = name;
        }
        else {
            returnString = "invalid token";
        }

        return new UserLoginResponseDto(returnString);
    }

    @Transactional
    public UserResponseDto fetchUserInfo(String tokenDtoString) {
        Payload payload = TokenVerify(tokenDtoString);
        UserResponseDto userResponseDto = null;

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> User Info Fetch");

            // 오늘의 패치 진행
            todayFetch(userId);

            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("id not found"));
            userResponseDto = new UserResponseDto(user);
        }
        else {

        }

        return userResponseDto;
    }


    /*** TO DO REST SERVICE ***/
    @Transactional
    public TodoResponseDto saveTodoItem(TodoSaveRequestDto requestDto) {
        Payload payload = TokenVerify(requestDto.getToken());
        Todo createdTodo = new Todo();

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Todo Item Save");

            createdTodo = todoRepository.save(requestDto.toEntity(userId));
        }
        else {
            System.out.println("invalid token");
        }

        return new TodoResponseDto(createdTodo);
    }

    @Transactional
    public List<TodoResponseDto> fetchAllTodoItems(String tokenDtoString) {
        Payload payload = TokenVerify(tokenDtoString);
        List<TodoResponseDto> responseDtoList = new ArrayList<>();

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Todo Item List Fetch");

            List<Todo> todoEntities = todoRepository.findAllByUserId(userId);

            for(Todo todoEntity : todoEntities) {
                responseDtoList.add(new TodoResponseDto(todoEntity));
            }
        }
        else {
            System.out.println("invalid token");
        }

        return responseDtoList;
    }

    @Transactional
    public Long updateTodoItem(Long id, TodoUpdateRequestDto requestDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        todo.update(requestDto.getContent(),requestDto.getIsDone());

        return id;
    }

    @Transactional
    public Long deleteTodoItem(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        todoRepository.delete(todo);

        return id;
    }


    /*** DAY DO REST SERVICE ***/
    @Transactional
    public DaydoResponseDto saveDaydoItem(DaydoSaveRequestDto requestDto) {
        Payload payload = TokenVerify(requestDto.getToken());
        Daydo createdDaydo = new Daydo();

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Daydo Item Save");

            createdDaydo = daydoRepository.save(requestDto.toEntity(userId));
        }
        else {
            System.out.println("invalid token");
        }

        return new DaydoResponseDto(createdDaydo);
    }

    @Transactional
    public List<DaydoResponseDto> fetchAllDaydoItems(String tokenDtoString) {
        Payload payload = TokenVerify(tokenDtoString);
        List<DaydoResponseDto> responseDtoList = new ArrayList<>();

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Daydo Item List Fetch");

            List<Daydo> daydoEntities = daydoRepository.findAllByUserId(userId);

            for(Daydo daydoEntity : daydoEntities) {
                responseDtoList.add(new DaydoResponseDto(daydoEntity));
            }
        }
        else {
            System.out.println("invalid token");
        }

        return responseDtoList;
    }

    @Transactional
    public Long updateDaydoItem(Long id, DaydoUpdateRequestDto requestDto) {
        Daydo daydo = daydoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        daydo.update(requestDto.getContent());

        return id;
    }

    @Transactional
    public Long deleteDaydoItem(Long id) {
        Daydo daydo = daydoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        daydoRepository.delete(daydo);

        return id;
    }


    /*** Pomodoro Service ***/
    @Transactional
    public PomodoroResponseDto fetchPomodoroItem(String tokenDtoString) {
        Payload payload = TokenVerify(tokenDtoString);
        PomodoroResponseDto pomodoroResponseDto = null;

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Pomodoro Info Fetch");

            Pomodoro pomodoro = pomodoroRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("id not found"));
            pomodoroResponseDto = new PomodoroResponseDto(pomodoro);
        }
        else {

        }

        return pomodoroResponseDto;
    }

    @Transactional
    public PomodoroResponseDto updatePomodoroItem(PomodoroUpdateRequestDto requestDto) {
        Payload payload = TokenVerify(requestDto.getTokenId());
        PomodoroResponseDto pomodoroResponseDto = null;

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId + "=> Pomodoro Info Update");

            Pomodoro pomodoro = pomodoroRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("id not found"));
            pomodoro.update(requestDto.getTimerSet(),requestDto.getPomo());

            pomodoroResponseDto = new PomodoroResponseDto(pomodoro);
        }
        else {

        }

        return pomodoroResponseDto;
    }
}