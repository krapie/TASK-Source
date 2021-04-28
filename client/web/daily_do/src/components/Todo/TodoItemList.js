import TodoItem from './TodoItem';
import './TodoItemList.css';

const TodoItemList = ({ todoList, onRemove, onChange, onToggle }) => {
    let todoListHTML;

    if(!todoList) {
        todoListHTML = <p>로딩중...</p>;
    }
    else if(todoList.length === 0) {
        todoListHTML = <p>새 할 일을 적어주세요!</p>
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
        <div className="todo-list-wrapper">
            {todoListHTML}
        </div>
    );
}

export default TodoItemList;