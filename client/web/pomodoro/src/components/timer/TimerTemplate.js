import './TimerTemplate.css';
import Timer from './Timer.js';
import { useState } from 'react';

const TimerTemplate = () => {
    const [ pomo, setPomo ] = useState(0);

    function handlePomoUpdate() {
        setPomo(pomo+1);
    }

    return (
        <div className="timerTemplate component">
            <h1>포모도로</h1>
            <Timer timeSet={25*60} pomoUpdate={handlePomoUpdate}></Timer> 
            <h2 className="pomodoro_count">{pomo} 포모 달성!</h2>
            <p>1 포모 = 25분</p>
            <p>4 포모 후 30분 휴식!</p>
        </div>
    );
}

export default TimerTemplate;