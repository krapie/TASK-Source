import './TodoForm.css';

const TodoForm = ({value, onChange, onCreate, onKeyPress}) => {
    return (
        <div className="todo-form">
            <span>+</span>           
            <input type="text" value={value} onChange={onChange} onKeyPress={onKeyPress} placeholder="할 일 적기"/>
            <button onClick={onCreate}>추가</button>
        </div>
    );
}

export default TodoForm;