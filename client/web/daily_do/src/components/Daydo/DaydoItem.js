import './DaydoItem.css'

const DaydoItem = ({ id, content, onRemove, onChange}) => {
    return (
        <li className="daydo-item">
            <input type="text" className={`todo-content`} value={content} onChange={(e) => { onChange(e, id) }}/>
            <span className="remove-item" onClick={(e) => { e.stopPropagation(); onRemove(id); }}>&times;</span>
        </li>
    );
}

export default DaydoItem;