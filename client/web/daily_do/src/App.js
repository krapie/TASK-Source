import { useState } from 'react';
import './App.css';
import DayDoListTemplate from './components/Daydo/DaydoListTemplate';
import Footer from './components/Footer';
import Navigation from './components/Navigation';
import TodoListTemplate from './components/Todo/TodoListTemplate';

function App() {
  const today = new Date();
  const dayname = ['일','월','화','수','목','금','토','일'];       
  const todayDateHTML = <p>오늘은 <br></br> {today.getMonth()+1}월 {today.getDate()}일 {dayname[today.getDay()]}요일</p>;
  
  const [ selectedPageHTML, setSelectedPageHTML ] = useState(
    <div className="component">
      <h1>오늘 할 일은?</h1>
      {todayDateHTML}
      <TodoListTemplate></TodoListTemplate>
      <p style={{ color : "gray" }}>요일별로 할 일이 자동으로 갱신됩니다!</p>
  </div>
  ); // 기본값: 오늘 할 일

  function handleNavigation(e) {
      const selectedPageName = e.target.className;

      if(selectedPageName === "today") {
        setSelectedPageHTML(
        <div className="component">
          <h1>오늘 할 일은?</h1>
          {todayDateHTML}
          <TodoListTemplate></TodoListTemplate>
          <p style={{ color : "gray" }}>요일별로 할 일이 자동으로 갱신됩니다!</p>
        </div>
        )
      }
      else if(selectedPageName === "preference") {
        setSelectedPageHTML(
        <div className="component">
          <h1>설정</h1>
          <p>요일별 할 일 설정</p>
          <DayDoListTemplate></DayDoListTemplate>
          <p style={{ color : "gray" }}>요일별로 할 일을 설정하면 해당 요일에 자동으로 할 일이 갱신됩니다!</p>
        </div>
        )
      }
  }


  // 메인
  return (
    <div className="main">
      <Navigation onNavigation={handleNavigation}></Navigation>
      {selectedPageHTML}
      <Footer></Footer>
    </div>
  );
}

export default App;