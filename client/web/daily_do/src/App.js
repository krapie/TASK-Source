import './App.css';
import DayDoListTemplate from './components/Daydo/DaydoListTemplate';
import TodoListTemplate from './components/Todo/TodoListTemplate';
function App() {

  return (
    <div>
      <h1>오늘 할 일은?</h1>
      <TodoListTemplate></TodoListTemplate>
      <h1>설정</h1>
      <h2>요일별 할 일 설정</h2>
      <DayDoListTemplate></DayDoListTemplate>
    </div>
  );
}

export default App;