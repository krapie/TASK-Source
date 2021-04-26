import './TodoItem.css';

const TodoItem = ({ id, content, isDone, onToggle, onRemove}) => {
    return (
        <div className="todo-item" onClick={() => onToggle(id)}>
            <span>{ isDone ? (<span className="check-mark">✓</span>) : (<div> </div>)}</span>
            <span className={`todo-content ${isDone ? ('checked') : ('')}`}>{content}</span>
            <span className="remove-item" onClick={(e) => { e.stopPropagation(); onRemove(id); }}>&times;</span>
        </div>
    );
}

export default TodoItem;