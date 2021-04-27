import './TodoItem.css';

const TodoItem = ({ id, content, isDone, onRemove, onChange, onToggle}) => {
    return (
        <div className="todo-item">
            <input type="checkbox" checked={isDone} onChange={() => { onToggle(id) }}></input>
            <input type="text" className={`todo-content ${isDone ? ('checked') : ('')}`} value={content} onChange={(e) => { onChange(e, id) }}/>
            <span className="remove-item" onClick={(e) => { e.stopPropagation(); onRemove(id); }}>&times;</span>
        </div>
    );
}

export default TodoItem;