import { useState } from 'react';
import TodoForm from './TodoForm';
import TodoItemList from './TodoItemList';
import './TodoListTemplate.css';

const TodoListTemplate = () => {

    let todoItemId = 1;

    const [ todoItemList, setTodoItemList ] = useState([ { id: 0, content: "Todo #1", isDone : true }]);
    const [ formInput, setFormInput ] = useState('');
    
    function handleToggle(id) {
        const index = todoItemList.findIndex(todoItem => todoItem.id === id);
        const selectedItem = todoItemList[index];
        selectedItem.isDone = !selectedItem.isDone;

        setTodoItemList({...todoItemList, selectedItem});
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
            id: todoItemId++,
            content: formInput,
            isDone: false
        };

        setTodoItemList({...todoItemList, newTodoItem});
        console.log(todoItemList);
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