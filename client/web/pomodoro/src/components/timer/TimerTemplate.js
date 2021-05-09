import './TimerTemplate.css';
import Timer from './Timer.js';
import { useState, useEffect } from 'react';

const TimerTemplate = () => {
    const [ pomo, setPomo ] = useState(0);
    const [ timerSet, setTimerSet ] = useState(0);
    const [ fetched, setFetched ] = useState(false);

    function handlePomoUpdate() {
        setPomo(pomo+1);
    }

    // FETCH - GET
    useEffect(() => {
        if(!fetched) {
            fetch('http://localhost:8080/api/pomodoro')
            .then((response) => response.json())
            .then((info) => {
                console.log("서버로부터 Pomodoro 정보 가져옴: ",  info.timerSet, info.pomo);
                setTimerSet(info.timerSet);
                setPomo(info.pomo);
                setFetched(true);
            });
        }
    }, [fetched]);

    
    return (
        <div className="timerTemplate component">
            <h1>포모도로</h1>
            <Timer timeSet={timerSet} pomoUpdate={handlePomoUpdate}></Timer> 
            <h2 className="pomodoro_count">{pomo} 포모 달성!</h2>
            <p>1 포모 = 25분</p>
            <p>4 포모 후 30분 휴식!</p>
        </div>
    );
}

export default TimerTemplate;