import TodoItem from './TodoItem';
import './TodoItemList.css';

const TodoItemList = ({ todoList, onToggle, onRemove }) => {
    const todoListHTML = todoList.map(
        ({id, content, isDone}) => {
            return <TodoItem
                key={id}
                id={id}
                content={content}
                isDone={isDone}
                onToggle={onToggle}
                onRemove={onRemove}
            />
        }
    );

    return (
        <div className="todo-list-wrapper">
            {todoListHTML}
        </div>
    );
}

export default TodoItemList;