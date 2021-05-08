const TimerChange = () => {

    return (
        <div className="timerChange component">
            <h3>타이머 설정</h3>
            <div className="timer_set_form">
                <input type="text"  placeholder="분:초"></input>
                <button >변경</button>
            </div>
            <p>자신만의 포모도로 타이머를 세팅해보세요!</p>
            <p>현재 타이머 세팅: <b className="timer_set_display"></b></p>
        </div>
    );
}

export default TimerChange;