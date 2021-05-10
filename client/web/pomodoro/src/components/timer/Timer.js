import { useEffect, useRef, useState } from 'react';
import './Timer.css';
import TimerDisplay from './TimerDisplay';

const Timer = ({ timeSet, pomoUpdate }) => {
    let countdown = useRef(null);
    let isBreakTime = false;

    const [ remainderMinutes, setRemainderMinutes ] = useState(0);
    const [ remainderSeconds, setRemainderSeconds ] = useState(0);
    const [ timerSet, setTimerSet ] = useState(timeSet);
    const [ timerActive, setTimerActive ] = useState(false);

    const breakTimerSet = 5*60;

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
        return () => clearInterval(countdown);
    }, []); 

    function start_timer() {
        timer(timerSet);
        setTimerActive(true);
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
                    setTimerSet(breakTimerSet);
                    // 설정된 휴식시간 타이머 디스플레이: 5분
                    setRemainderMinutes(5);
                    setRemainderSeconds(0);
                    pomoUpdate();
                    break_time_display.innerHTML = "휴식 시간!";

                    timer(breakTimerSet);
                } else {
                    isBreakTime = false;
                    setTimerSet(timeSet);
                    // 설정된 타이머 디스플레이하기
                    setRemainderMinutes(Math.floor(timerSet / 60));
                    setRemainderSeconds(timerSet);
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
            <h1 className="break_time_display">{}</h1>
            <input className="start_timer_button" type="button" value="타이머 시작!" onClick={start_timer} disabled={timerActive ? ('disabled') : ('')}/>
        </div>
    );
}

export default Timer;