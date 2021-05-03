import { useEffect, useState } from 'react';
import Footer from './components/main/Footer';
import Navigation from './components/main/Navigation';
import PreferenceWrapper from './components/PreferenceWrapper';
import TodayWrapper from './components/TodayWrapper';
import './App.css';

function App() {
  const today = new Date();
  const dayname = ['일','월','화','수','목','금','토','일'];       
  const todayDateHTML = <p>오늘은 <br></br> {today.getMonth()+1}월 {today.getDate()}일 {dayname[today.getDay()]}요일</p>;

  const [ darkTheme, setDarkTheme ] = useState(false); // 다크 모드
  const [ selectedPageNum, setSelectedPageNum ] = useState(0); // 기본값: 오늘 할 일 (0)

  useEffect(() => { // 랜더링 이후
    const body = document.querySelector('body');
    const navigation = document.querySelector('.navigation');
    const footer = document.querySelector('.footer');

    if(darkTheme) {  
      body.classList.add('dark');
      navigation.classList.add('dark');
      footer.classList.add('dark');
    }
    else {
      body.classList.remove('dark');
      navigation.classList.remove('dark');
      footer.classList.remove('dark');
    }
  }, [ darkTheme ]);

  function handleNavigation(e) {
      const selectedPageName = e.target.className;

      if(selectedPageName === "today") {
        setSelectedPageNum(0);
      }
      else if(selectedPageName === "preference") {
        setSelectedPageNum(1);
      }
  }

  function handleThemeToggle() {
    setDarkTheme(!darkTheme);
  }
  
  // 메인
  return (
    <div className="main">
      <Navigation onNavigation={handleNavigation}></Navigation>
      { (selectedPageNum === 0) ? (<TodayWrapper darkTheme={darkTheme} todayDateHTML={todayDateHTML}></TodayWrapper>) : (<PreferenceWrapper darkTheme={darkTheme} handleThemeToggle={handleThemeToggle}></PreferenceWrapper>)}
      <Footer></Footer>
    </div>
  );
}

export default App;