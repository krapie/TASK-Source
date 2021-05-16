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
import kom.task.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
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

    /*** LOGIN REST SERVICE ***/
    public String googleTokenLogin(String tokenDtoString) {
        String returnString = "";
        Payload payload = TokenVerify(tokenDtoString);

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

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
                    .orElse(userRepository.save(User.builder()
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

        return returnString;
    }

    public UserResponseDto fetchUserInfo(String tokenDtoString) {
        Payload payload = TokenVerify(tokenDtoString);
        UserResponseDto userResponseDto = null;

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("id not found"));
            userResponseDto = new UserResponseDto(user);
        }
        else {

        }

        return userResponseDto;
    }



    /*** TO DO REST SERVICE ***/
    @Transactional
    public Todo saveTodoItem(TodoSaveRequestDto requestDto) {
        return todoRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<TodoResponseDto> fetchAllTodoItems() {
        todayFetch();

        List<Todo> todoEntities = todoRepository.findAll();
        List<TodoResponseDto> responseDtoList = new ArrayList<>();

        for(Todo todoEntity : todoEntities) {
            responseDtoList.add(new TodoResponseDto(todoEntity));
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
    public Daydo saveDaydoItem(DaydoSaveRequestDto requestDto) {
        return daydoRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<DaydoResponseDto> fetchAllDaydoItems() {
        List<Daydo> daydoEntities = daydoRepository.findAll();
        List<DaydoResponseDto> responseDtoList = new ArrayList<>();

        for(Daydo daydoEntity : daydoEntities) {
            responseDtoList.add(new DaydoResponseDto(daydoEntity));
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


    /*** FETCH LOGICS ***/
    public void todayFetch() {
        // 이미 To-do 아이템이 존재하면 (이미 요일별 할 일이 저장되어 있으면)
        if(todoRepository.count() != 0) {
            return;
        }

        // 호출된 시점의 요일을 구해서
        LocalDate localDate = LocalDate.now();
        int todayDay =  localDate.getDayOfWeek().getValue(); // Monday - Sunday : 1 ~ 7

        // 해당 요일에 해당하는 튜플들을 DaydoRepository에서 추출 후
        List<Daydo> daydoList = daydoRepository.findAll();
        List<Daydo> todayDaydoList = daydoList.stream().filter(daydo -> daydo.getDay() == todayDay).collect(Collectors.toList());

        // To-do 아이템으로 변환 후 TodoRepository에 저장
        for(Daydo daydo : todayDaydoList ) {
            Todo todoEntity = Todo.builder()
                    .content(daydo.getContent())
                    .isDone(false)
                    .build();

            todoRepository.save(todoEntity);
        }
    }


    /*** Pomodoro Service ***/
    @Transactional
    public PomodoroResponseDto fetchPomodoroItem(String tokenDtoString) {
        Payload payload = TokenVerify(tokenDtoString);
        PomodoroResponseDto pomodoroResponseDto = null;

        if(payload != null) {
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

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
            System.out.println("User ID: " + userId);

            Pomodoro pomodoro = pomodoroRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("id not found"));
            pomodoro.update(requestDto.getTimerSet(),requestDto.getPomo());

            pomodoroResponseDto = new PomodoroResponseDto(pomodoro);
        }
        else {

        }

        return pomodoroResponseDto;
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
}