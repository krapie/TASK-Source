import { useState } from 'react';
import TodoForm from './TodoForm';
import TodoItemList from './TodoItemList';
import './TodoListTemplate.css';

const TodoListTemplate = () => {
    const [ todoItemCnt, setTodoItemCnt ] = useState(1);
    const [ todoItemList, setTodoItemList ] = useState([ { id: 0, content: "할일 #1", isDone : true }]);
    const [ formInput, setFormInput ] = useState('');
    
    function handleToggle(id) {
        setFormInput('');

        const index = todoItemList.findIndex(todoItem => todoItem.id === id);
        const selectedItem = todoItemList[index];
        
        const updatedTodoItemList = [...todoItemList];

        updatedTodoItemList[index] = {
            ...selectedItem,
            isDone: !selectedItem.isDone
        };

        setTodoItemList(updatedTodoItemList);
    }
  
    function handleRemove(id) {
        const updatedTodoItemList = todoItemList.filter(todoItem => todoItem.id !== id);
        setTodoItemList(updatedTodoItemList);
    }
  
    function handleChange(e) {
        setFormInput(e.target.value);
    }
  
    function handleCreate() {
        const newTodoItem = {
            id: todoItemCnt,
            content: formInput,
            isDone: false
        };

        setTodoItemCnt(todoItemCnt + 1);
        setTodoItemList([...todoItemList, newTodoItem]);
    }


    return (
        <div className="todo-list-template-wrapper">
            <div className="title">
                <h2>목록</h2>
            </div>
            <hr></hr>
            <div className="todo-list-wrapper">
                <TodoItemList
                    todoList={todoItemList}
                    onToggle={handleToggle}
                    onRemove={handleRemove}
                />
            </div>
            <hr></hr>
            <div className="todo-form-wrapper">
                <TodoForm
                    value={formInput}
                    onChange={handleChange}
                    onCreate={handleCreate}
                />
            </div>
        </div>
    );
}

export default TodoListTemplate;