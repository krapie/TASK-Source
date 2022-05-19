import './TodoItem.css';

const TodoItem = ({ id, content, isDone, onRemove, onChange, onToggle}) => {
    return (
        <li className="todo-item">
            <input id={id} type="checkbox" checked={isDone} onChange={() => { onToggle(id) }}></input>
            <label htmlFor={id}></label>
            <input type="text" className={`todo-content ${isDone ? ('checked') : ('')}`} value={content} onChange={(e) => { onChange(e, id) }}/>
            <span className="remove-item" onClick={(e) => { e.stopPropagation(); onRemove(id); }}>&times;</span>
        </li>
    );
}

export default TodoItem;