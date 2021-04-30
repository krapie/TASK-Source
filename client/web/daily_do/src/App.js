import { useState } from 'react';
import './App.css';
import DayDoListTemplate from './components/Daydo/DaydoListTemplate';
import Navigation from './components/Navigation';
import TodoListTemplate from './components/Todo/TodoListTemplate';
function App() {
  const [ selectedPageHTML, setSelectedPageHTML ] = useState(
  <div>
    <h1>오늘 할 일은?</h1>
    <TodoListTemplate></TodoListTemplate>
  </div>
  ); // 기본값: 오늘 할 일
  
  function handleNavigation(e) {
      const selectedPageName = e.target.className;

      if(selectedPageName === "today") {
        setSelectedPageHTML(
        <div>
          <h1>오늘 할 일은?</h1>
          <TodoListTemplate></TodoListTemplate>
        </div>
        )
      }
      else if(selectedPageName === "preference") {
        setSelectedPageHTML(
        <div>
          <h1>설정</h1>
          <h2>요일별 할 일 설정</h2>
          <DayDoListTemplate></DayDoListTemplate>
        </div>
        )
      }
  }


  return (
    <div>
      <Navigation onNavigation={handleNavigation}></Navigation>
      {selectedPageHTML}
    </div>
  );
}

export default App;