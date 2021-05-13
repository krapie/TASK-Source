import './DashboardWrapper.css';
import { useEffect, useState } from "react";

function DashboardWrapper() {
    const today = new Date();
    const dayname = ['일','월','화','수','목','금','토','일'];       
    const todayDateHTML = <p>오늘은 <br></br> {today.getMonth()+1}월 {today.getDate()}일 {dayname[today.getDay()]}요일</p>;

    const [ todoItems, setTodoItems ] = useState([]);
    const [ pomodoroItem, setPomodoroItem ] = useState([]);
    const [ isPatched, setIsPatched ] = useState(false);
    const [ todoItemsCount, setTodoItemsCount ] = useState(0);
    const [ todoItemsDoneCount, setTodoItemsDoneCount ] = useState(0);
    const [ pomoCount, setPomoCount ] = useState(0);
    
    // Read
    useEffect(() => {
        if(!isPatched) {
            fetch('http://localhost:8080/api/todo')
                .then((response) => response.json())
                .then((data) => {
                    console.log("Todo Items: ", data);
                    setTodoItems(data);
                });

            fetch('http://localhost:8080/api/pomodoro')
                .then((response) => response.json())
                .then((data) => {
                    console.log("Pomodoro Item: ", data.timerSet, data.pomo);
                    setPomodoroItem(data);
                });
            setIsPatched(true);
        }
    }, [ isPatched ]);

    useEffect(() => {
        calTodoItemsCount();  
        calTodoItemsDoneCount();
        calPomoCount();
    }, [ todoItems, pomodoroItem ]);

    function calTodoItemsCount() {
        setTodoItemsCount(todoItems.length);
    }

    function calTodoItemsDoneCount() {
        setTodoItemsDoneCount(todoItems.filter(todoItem => todoItem.isDone).length);
    }

    function calPomoCount() {
        setPomoCount(pomodoroItem.pomo);
    }

    return (
        <div>
            <div className="dashboard_background">
                
            </div>
            <div className="dashboard_content">
                <div className="dashboard_date">
                    {todayDateHTML}
                </div>
                <div className="dashboard_daily_do">
                    <h1>오늘 할 일</h1>
                    <ul>
                        <li>오늘 할 일 달성률: {Math.round((todoItemsDoneCount / todoItemsCount) * 100)}%</li>
                        <li>오늘 할 일: {todoItemsDoneCount}/{todoItemsCount}</li>
                    </ul>
                </div>
                <div className="dashboard_pomodoro">
                    <h1>뽀모도로</h1>
                    <ul>
                        <li>오늘 한 뽀모: {pomoCount}</li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default DashboardWrapper;