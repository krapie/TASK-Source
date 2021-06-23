import { useEffect, useState } from 'react';
import TodoForm from './TodoForm';
import TodoItemList from './TodoItemList';
import './TodoListTemplate.css';
import { homepageURL, serverURL } from '../../config'

const TodoListTemplate = ({ darkTheme }) => {
    const [ todoItemList, setTodoItemList ] = useState([]);
    const [ formInput, setFormInput ] = useState('');
    const [ fetched, setFetched ] = useState(false);

    const userIdLocation = document.cookie.split('; ').find(row => row.startsWith('userId'));
    const userId = userIdLocation === undefined ? window.location.replace(homepageURL) : userIdLocation.split('=')[1];
    
    useEffect(() => { // 다크 모드 
        console.log()
        const inputs = document.querySelectorAll('input');
    
        if(darkTheme) {  
          inputs.forEach(input => input.classList.add('dark'));
        }
        else {
          inputs.forEach(input => input.classList.remove('dark'));
        }
    });

    // FETCH - POST
    useEffect(() => {
        //첫 로딩시에만 서버로부터 Todo 목록 가져오기
        if(!fetched) {
            fetch(`${serverURL}/api/todos`, {
                method : 'POST',
                headers : {
                    'content-type' : 'application/json'
                },
                body : JSON.stringify(userId)
            })
            .then((response) => response.json())
            .then((items) => {
                console.log("서버로부터 Todo 목록 가져옴: ", items);
                setTodoItemList(items);
                setFetched(true);
            });
        }
    }, [fetched]);

    // CREATE - POST 
    function handleCreate() {
        // Form 안의 내용을 초기화
        setFormInput('');

        // 서버로 보낼 객체 아이템 생성
        const newTodoItem = {
            userId: userId,
            content: formInput,
            isDone: false
        };
        
        // POST 방식으로 서버 전송
        fetch(`${serverURL}/api/todo`, {
            method : 'POST',
            headers: {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(newTodoItem)
        }) // 서버에서 받은 응답을 객체화 후, TodoItemList에 추가 (컴포넌트 re-render됨)
        .then((response) => response.json())
        .then((newTodoItem) => {
            console.log("새 Todo 아이템 생성됨: ", newTodoItem);
             setTodoItemList([...todoItemList, newTodoItem]);
         })
    }
    
    // REMOVE - DELETE
    function handleRemove(id) {
        // 서버와 클라이언트 따로따로 처리됨
        const updatedTodoItemList = todoItemList.filter(todoItem => todoItem.id !== id);

        //서버
        fetch(`${serverURL}/api/todo/${id}`, {
            method: 'DELETE',
            headers : {
                'content-type' : 'application/json'
            }           
        })
        .then((response) => response.json())
        .then((deletedId) => {
            console.log("ID:", deletedId, " 삭제됨");
        });

        //클라이언트
        setTodoItemList(updatedTodoItemList);
    }
  
    // UPDATE (content) - PUT
    function handleTodoInputChange(e, id) {
        // 서버와 클라이언트 따로따로 처리됨
        const index = todoItemList.findIndex(todoItem => todoItem.id === id);
        const selectedItem = todoItemList[index];
        selectedItem.content = e.target.value;

        // 서버
        fetch(`${serverURL}/api/todo/${id}`, {
            method : 'PUT',
            headers : {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(selectedItem)
        })
        .then((response) => response.json())
        .then((updatedId) => {
            console.log("ID:", updatedId, " 업데이트됨");
        });

        // 클라이언트
        const updatedTodoItemList = [...todoItemList];

        updatedTodoItemList[index] = {
            ...selectedItem,
        };

        setTodoItemList(updatedTodoItemList);
    }
    
    // UPDATE (isDone) - PUT
    function handleToggle(id) {
        // 서버와 클라이언트 따로따로 처리됨
        const index = todoItemList.findIndex(todoItem => todoItem.id === id);
        const selectedItem = todoItemList[index];
        selectedItem.isDone = !selectedItem.isDone;

        // 서버
        fetch(`${serverURL}/api/todo/${id}`, {
            method : 'PUT',
            headers : {
                'content-type' : 'application/json'
            },
            body : JSON.stringify(selectedItem)
        })
        .then((response) => response.json())
        .then((updatedId) => {
            console.log("ID:", updatedId, " 업데이트됨");
        });

        // 클라이언트
        const updatedTodoItemList = [...todoItemList];

        updatedTodoItemList[index] = {
            ...selectedItem
        };

        setTodoItemList(updatedTodoItemList);       
    }

    function handleFormInputChange(e) {
        setFormInput(e.target.value);
    }

    function handleKeyPress(e) {
        if(e.key === 'Enter') {
            handleCreate();
        }
    }
  

    return (
        <div className="template-wrapper todo-list-template-wrapper">
            <div className="todo-title">
                <h2>목록</h2>
                <p></p>
            </div>
            <div className="todo-lists-wrapper">
                <TodoItemList
                    todoList={todoItemList}
                    onRemove={handleRemove}
                    onChange={handleTodoInputChange}
                    onToggle={handleToggle}
                />
            </div>
            <div className="todo-form-wrapper">
                <TodoForm
                    value={formInput}
                    onChange={handleFormInputChange}
                    onCreate={handleCreate}
                    onKeyPress={handleKeyPress}
                />
            </div>
        </div>
    );
}

export default TodoListTemplate;