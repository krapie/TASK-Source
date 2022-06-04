package kom.task.repository.service;

import kom.task.repository.repository.dailydo.daydo.Daydo;
import kom.task.repository.repository.dailydo.daydo.DaydoRepository;
import kom.task.repository.repository.dailydo.todo.Todo;
import kom.task.repository.repository.dailydo.todo.TodoRepository;
import kom.task.repository.repository.pomodoro.Pomodoro;
import kom.task.repository.repository.pomodoro.PomodoroRepository;
import kom.task.repository.repository.user.User;
import kom.task.repository.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodayFetchService {

    private final TodoRepository todoRepository;
    private final DaydoRepository daydoRepository;
    private final UserRepository userRepository;
    private final PomodoroRepository pomodoroRepository;

    public void todayFetch(String userId) {
        LocalTime sixAM = LocalTime.of(6,0,0);

        // User 엔티티 가져오기
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("userId not found"));

        // 호출된 시점의 요일을 구해서
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        // User에 저장된 todoUpdatedTime 와 날짜가 다르고 && 현재 시간이 오전 6시 이후이면
        if(!user.getTodoUpdatedDate().isEqual(nowDate) && nowTime.isAfter(sixAM)) {
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

        // User에 저장된 pomodoroUpdatedDate 와 날짜가 다르고 && 현재 시간이 오전 6시 이후이면
        if(!user.getPomodoroUpdatedDate().isEqual(nowDate) && nowTime.isAfter(sixAM)) {
            // Pomodoro 엔티티 가져오기
            Pomodoro pomodoro = pomodoroRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("userId not found"));

            // pomo 업데이트
            pomodoro.updateMaxPomo();

            // User pomodoroUpdatedTime 업데이트
            user.updatePomodoroUpdatedDate(nowDate);
        }
    }
}