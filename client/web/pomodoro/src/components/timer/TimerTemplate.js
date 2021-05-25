import './TimerTemplate.css';
import Timer from './Timer.js';
import { useState, useEffect } from 'react';

const TimerTemplate = () => {
    const [ pomo, setPomo ] = useState(0);
    const [ timerSet, setTimerSet ] = useState(25*60);
    const [ fetched, setFetched ] = useState(false);

    const idTokenLocation = document.cookie.split('; ').find(row => row.startsWith('idToken'));
    const idToken = idTokenLocation === undefined ? window.location.replace('http://komputer-task.ml') : idTokenLocation.split('=')[1];

    function handlePomoUpdate() {
        const pomodoroForm = {
            tokenId : idToken,
            timerSet : timerSet,
            pomo : pomo+1
        };    

        // 서버
        fetch(`http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com:8080/api/pomodoro/update`, {
            method : 'PUT',
            headers : {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(pomodoroForm)
        })
        .then((response) => response.json())
        .then((updatedPomodoro) => {
            setPomo(updatedPomodoro.pomo);
            console.log("Updated Pomo: ", updatedPomodoro.pomo);
        });
    }

    // FETCH - POST
    useEffect(() => {
        if(!fetched) {
            fetch('http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com:8080/api/pomodoro', {
                method : 'POST',
                headers : {
                    'content-type' : 'application/json'
                },
                body : JSON.stringify(idToken)
            })
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
        <div className="timer_template component">
            <h1>뽀모도로!</h1>
            <Timer timeSet={timerSet} pomoUpdate={handlePomoUpdate}></Timer> 
            <h2 className="pomodoro_count">오늘은 {pomo} 뽀모를 했어요!</h2>
            <p>1 포모 = 25분 집중, 5분 휴식</p>
            <p>4 포모 후 30분 휴식</p>
        </div>
    );
}

export default TimerTemplate;