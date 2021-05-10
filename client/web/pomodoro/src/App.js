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

  return (
    <Router>
      <div className="main">
        <Navigation></Navigation>
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
