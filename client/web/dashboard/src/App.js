import './App.css';
import { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Footer from './components/fixed/Footer'
import DashboardTemplateWrapper from './components/dashboard/DashboardTemplateWrapper';
import PreferenceTemplateWrapper from './components/preference/PreferenceTemplateWrapper';
import Login from './components/login/Login';

function App() {
  const [ darkTheme, setDarkTheme ] = useState(false); // 다크 모드

  useEffect(() => { // 랜더링 이후 다크 모드 설정 (추후 리팩토링)
    const body = document.querySelector('body');
    const footer = document.querySelector('.footer');

    if(darkTheme) {  
      body.classList.add('dark');
      footer.classList.add('dark');
    }
    else {
      body.classList.remove('dark');
      footer.classList.remove('dark');
    }
  }, [ darkTheme ]);

  function handleThemeToggle() {
    setDarkTheme(!darkTheme);
  }

  const [isPatched, setIsPatched] = useState(false);
  const [userInfo, setUserInfo] = useState("");

  const idToken = document.cookie === "" ? "" : document.cookie.split('; ').find(row => row.startsWith('idToken')).split('=')[1];
  
  // Read
  useEffect(() => {
      if (!isPatched) {
          // GET 방식으로 서버 전송
          fetch('http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com/api/user', {
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
        <Switch>
          <Route path="/" exact component={Login}/>
          <Route path="/dashboard" render={() => <DashboardTemplateWrapper darkTheme={darkTheme} userInfo={userInfo}></DashboardTemplateWrapper>}/>
          <Route path="/preference" render={() => <PreferenceTemplateWrapper darkTheme={darkTheme} userInfo={userInfo} handleThemeToggle={handleThemeToggle}></PreferenceTemplateWrapper>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </Router>
  );
}

export default App;
