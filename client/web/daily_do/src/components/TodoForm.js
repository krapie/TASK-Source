import './TodoForm.css';

const TodoForm = ({value, onChange, onCreate}) => {
    return (
        <div className="todo-form">
            <input type="text" value={value} onChange={onChange}/>
            <button onClick={onCreate}>추가</button>
        </div>
    );
}

export default TodoForm;