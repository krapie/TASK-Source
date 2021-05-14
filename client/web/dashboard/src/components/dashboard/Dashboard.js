import './Dashboard.css';
import { useEffect, useState } from "react";

function Dashboard() {
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

    useEffect(() => { // 임시
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
        <div className="dashboard">
            <div className="dashboard_user_info component">
                <div className="user_picture"></div>
                <h2>박지환</h2>
                <hr></hr>
            </div>
            <div className="dashboard_content component">
                <div className="dashboard_daily_do">
                    <a href="/"><div className="daily_do_app_picture">오늘 할 일</div></a>
                    <ul>
                        <li>오늘 할 일 달성률<h1>{Math.round((todoItemsDoneCount / todoItemsCount) * 100)}%</h1></li>
                    </ul>
                </div>
                <div className="dashboard_pomodoro">
                    <a href="/"><div className="pomodoro_app_picture">뽀모도로</div></a>
                    <ul>
                        <li>오늘 한 뽀모<h1>{pomoCount} 뽀모</h1></li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Dashboard;