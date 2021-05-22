import { useEffect, useState } from 'react';
import Footer from './components/fixed/Footer';
import Navigation from './components/fixed/Navigation';
import PreferenceWrapper from './components/PreferenceWrapper';
import TodayWrapper from './components/TodayWrapper';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

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
  const idToken = document.cookie.split('; ').find(row => row.startsWith('idToken')).split('=')[1];
  
  // Read
  useEffect(() => {
      if (!isPatched) {
          // GET 방식으로 서버 전송
          fetch('http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com:8080/api/user', {
              method: 'POST',
              headers: {
                  'content-type': 'application/json'
              },
              body: JSON.stringify(idToken)
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
    <Router basename="/dailydo">
      <div className="main">
        <Navigation userInfo={userInfo}></Navigation>
        <Switch>
          <Route path={process.env.PUBLIC_URL + '/'} exact render={() => <TodayWrapper idToken={idToken} darkTheme={darkTheme} todayDateHTML={todayDateHTML}/>}/>
          <Route path={process.env.PUBLIC_URL + '/preference'} render={() => <PreferenceWrapper idToken={idToken} darkTheme={darkTheme} handleThemeToggle={handleThemeToggle}/>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </Router>
  );
}

export default App;