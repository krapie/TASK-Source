import { useEffect, useState } from 'react';
import './Timer.css';
import TimerDisplay from './TimerDisplay';

const Timer = ({ timeSet, pomoUpdate }) => {
    
    let countdown;
    let isBreakTime = false;

    const [ remainderMinutes, setRemainderMinutes ] = useState(0);
    const [ remainderSeconds, setRemainderSeconds ] = useState(0);
    const [ timerSet, setTimerSet ] = useState(timeSet);

    // timeSet props 가져오기
    useEffect(() => {
        setTimerSet(timeSet);    
    }, [ timeSet ]);

    // 설정되어 있는 시간만큼 초기 화면 표시
    useEffect(() => {
        setRemainderMinutes(Math.floor(timerSet / 60));
        setRemainderSeconds(timerSet % 60);
    }, [ timerSet ]);

    // Router 이동시 states cleanUp 해주기
    useEffect(() => {
        return function cleanUp() {
            clearInterval(countdown);
        }
    }, []); 

    function start_timer() {
        timer(timerSet);
    }

    // 타이머
    function timer(seconds) {
        clearInterval(countdown);
    
        const start_time = Date.now();
        const end_time = start_time + seconds * 1000;
            
        countdown = setInterval(() => {
            const seconds_left = Math.round((end_time-Date.now()) / 1000); // accurate version
    
            if(seconds_left < 0) {
                clearInterval(countdown);
                
                const break_time_display = document.querySelector('.break_time_display');

                if(!isBreakTime) {
                    isBreakTime = true;
                    setTimerSet(5);
                    pomoUpdate();
                    break_time_display.innerHTML = "휴식 시간!";

                    timer(5);
                } else {
                    isBreakTime = false;
                    setTimerSet(timeSet);
                    break_time_display.innerHTML = "";
                }

                return;
            }
            
            setRemainderMinutes(Math.floor(seconds_left / 60));
            setRemainderSeconds(seconds_left % 60);
        }, 1000);
    }

    return (
        <div className="timer">
            <TimerDisplay minutes={remainderMinutes} seconds={remainderSeconds}></TimerDisplay>
            <p className="break_time_display"></p>
            <input className="start_timer_button" type="button" value="시작!" onClick={start_timer}/>
        </div>
    );
}

export default Timer;