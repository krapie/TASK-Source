import { useEffect, useState } from 'react';
import Footer from './components/fixed/Footer';
import Navigation from './components/fixed/Navigation';
import PreferenceWrapper from './components/PreferenceWrapper';
import TodayWrapper from './components/TodayWrapper';
import './App.css';
import { HashRouter, Route, Switch } from 'react-router-dom';

function App() {
  const today = new Date();
  const dayname = ['일','월','화','수','목','금','토','일'];       
  const todayDateHTML = <p>오늘은 <br></br> {today.getMonth()+1}월 {today.getDate()}일 {dayname[today.getDay()]}요일</p>;

  const [ darkTheme, setDarkTheme ] = useState(false); // 다크 모드
  //const [ selectedPageNum, setSelectedPageNum ] = useState(0); // 기본값: 오늘 할 일 (0)

  useEffect(() => { // 랜더링 이후 다크 모드 설정 (추후 리팩토링)
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

  function handleThemeToggle() {
    setDarkTheme(!darkTheme);
  }

  const [isPatched, setIsPatched] = useState(false);
  const [userInfo, setUserInfo] = useState("");

  const userIdLocation = document.cookie.split('; ').find(row => row.startsWith('userId'));
  const userId = userIdLocation === undefined ? window.location.replace('http://tasko.today') : userIdLocation.split('=')[1];
  
  // Read
  useEffect(() => {
      if (!isPatched) {
          // GET 방식으로 서버 전송
          fetch('http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com/api/user', {
              method: 'POST',
              headers: {
                  'content-type': 'application/json'
              },
              body: JSON.stringify(userId)
          })
              .then((response) => response.json())
              .then((newUserInfo) => {
                  console.log("유저 정보 가져옴: ", { newUserInfo });
                  setUserInfo(newUserInfo);
              });

          setIsPatched(true);
      }
  }, [isPatched]);
  
  // 메인
  return (
    <HashRouter>
      <div className="main">
        <Navigation userInfo={userInfo}></Navigation>
        <Switch>
          <Route path={'/'} exact render={() => <TodayWrapper darkTheme={darkTheme} todayDateHTML={todayDateHTML}/>}/>
          <Route path={'/preference'} render={() => <PreferenceWrapper darkTheme={darkTheme} handleThemeToggle={handleThemeToggle}/>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </HashRouter>
  );
}

export default App;