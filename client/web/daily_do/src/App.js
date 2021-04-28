import './App.css';
import DaydoTest from './components/test/DaydoTest';
import TodoListTemplate from './components/Todo/TodoListTemplate';
function App() {

  return (
    <div>
      <h1>오늘 할 일은?</h1>
      <TodoListTemplate></TodoListTemplate>
      <DaydoTest></DaydoTest>
    </div>
  );
}

export default App;