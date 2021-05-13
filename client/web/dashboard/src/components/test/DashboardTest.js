import { useEffect, useState } from "react";

function DashboardTest() {
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
            <h1>오늘 할 일: {todoItemsDoneCount}/{todoItemsCount}</h1>
            <h1>오늘 할 일 달성률: {Math.round((todoItemsDoneCount / todoItemsCount) * 100)}%</h1>
            <h1>오늘 한 뽀모: {pomoCount}</h1>
        </div>
    );
}

export default DashboardTest;