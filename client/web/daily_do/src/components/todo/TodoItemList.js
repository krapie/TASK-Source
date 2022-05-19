import TodoItem from './TodoItem';
import './TodoItemList.css';

const TodoItemList = ({ todoList, onRemove, onChange, onToggle }) => {
    let todoListHTML;

    if(todoList.length === 0) {
        todoListHTML = <div className="todo-empty-list">새 할 일을 적어주세요!</div>
    }
    else {
        todoListHTML = todoList.map(
            ({id, content, isDone}) => {
                return <TodoItem
                    key={id}
                    id={id}
                    content={content}
                    isDone={isDone}
                    onRemove={onRemove}
                    onChange={onChange}
                    onToggle={onToggle}
                />
            }
        );
    }

    return (
        <ul className="todo-list-wrapper">
            {todoListHTML}
        </ul>
    );
}

export default TodoItemList;