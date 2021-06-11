import './App.css';
import TimerTemplate from './components/timer/TimerTemplate';
import { HashRouter, Route, Switch } from 'react-router-dom';
import PreferenceTemplate from './components/preference/PreferenceTemplate';
import Navigation from './components/fixed/Navigation';
import Footer from './components/fixed/Footer';
import { useState, useEffect } from 'react';

function App() {
  const [ darkTheme, setDarkTheme ] = useState(false);

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

  return (
    <HashRouter>
      <div className="main">
        <Navigation userInfo={userInfo}></Navigation>
        <Switch>
          <Route path={'/'} exact render={() => <TimerTemplate></TimerTemplate>}/>
          <Route path={'/preference'} render={() => <PreferenceTemplate darkTheme={darkTheme} onToggle={handleThemeToggle}></PreferenceTemplate>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </HashRouter>
  );
}

export default App;
