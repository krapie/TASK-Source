import './App.css';
import TimerTemplate from './components/timer/TimerTemplate';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
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
  const idToken = localStorage.getItem("idToken");
  
  // Read
  useEffect(() => {
      if (!isPatched) {
          // GET 방식으로 서버 전송
          fetch('http://localhost:8080/api/user', {
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

  return (
    <Router>
      <div className="main">
        <Navigation userInfo={userInfo}></Navigation>
        <Switch>
          <Route path="/" exact render={() => <TimerTemplate></TimerTemplate>}/>
          <Route path="/preference" render={() => <PreferenceTemplate darkTheme={darkTheme} onToggle={handleThemeToggle}></PreferenceTemplate>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </Router>
  );
}

export default App;
