import { useState, useEffect } from "react";
import "./TimerChange.css";

const TimerChange = () => {
    const [ formInput, setFormInput ] = useState(''); // 입력 필드값
    const [ timerSet, setTimerSet ] = useState(0);
    const [ pomo, setPomo ] = useState(0);
    const [ fetched, setFetched ] = useState(false);

    const userIdLocation = document.cookie.split('; ').find(row => row.startsWith('userId'));
    const userId = userIdLocation === undefined ? window.location.replace('http://komputer-task.ml') : userIdLocation.split('=')[1];

    // FETCH - POST
    useEffect(() => {
        if(!fetched) {
            fetch('http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com/api/pomodoro', {
                method : 'POST',
                headers : {
                    'content-type' : 'application/json'
                },
                body : JSON.stringify(userId)
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
    
    function handleUpdate() {
        setFormInput('');
        // 입력값 유효성 검사 (분:초)
        if(!RegExp("[0-9][0-9]:[0-9][0-9]").test(formInput)) { //if not in correct format
            alert("형식에 맞게 값을 입력해주세요!");
            return;
        }

        const time_parsed = formInput.split(':');
        let minutes = parseInt(time_parsed[0]);
        let seconds = parseInt(time_parsed[1]);
        seconds += minutes * 60;
        
        const pomodoroForm = {
            userId : userId,
            timerSet : seconds,
            pomo : pomo
        };    

        // 서버
        fetch(`http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com/api/pomodoro/update`, {
            method : 'PUT',
            headers : {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(pomodoroForm)
        })
        .then((response) => response.json())
        .then((updatedPomodoro) => {
            setTimerSet(updatedPomodoro.timerSet);
            console.log("Updated TimerSet: ", updatedPomodoro.timerSet );
        });
    }

    function handleChange(e) {
        setFormInput(e.target.value);
    }

    function handleKeyPress(e) {
        if(e.key === 'Enter') {
            handleUpdate();
        }
    }

    return (
        <div className="preference_component wrapper">
            <h3>타이머 설정</h3>
            <div className="timer_set_form">
                <input className="timer_set_text" type="text" value={formInput} onChange={handleChange} onKeyPress={handleKeyPress} placeholder="분 : 초 "></input>
                <button className="timer_set_submit" onClick={handleUpdate}>변경</button>
            </div>
            <p>자신만의 뽀모도로 타이머를 세팅해보세요.</p>
            <p>현재 타이머 세팅: <b className="timer_set_display">{Math.floor(timerSet / 60) < 10 ? ('0' + Math.floor(timerSet / 60)) : (Math.floor(timerSet / 60))}:{(timerSet % 60) < 10 ? ('0' + timerSet % 60) : (timerSet % 60)}</b></p>
        </div>
    );
}

export default TimerChange;