import './App.css';
import { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Navigation from './components/fixed/Navigation';
import Footer from './components/fixed/Footer'
import DashboardTemplateWrapper from './components/dashboard/DashboardTemplateWrapper';
import PreferenceTemplateWrapper from './components/preference/PreferenceTemplateWrapper';

function App() {
  const [ darkTheme, setDarkTheme ] = useState(false); // 다크 모드

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
          <Route path="/" exact render={() => <DashboardTemplateWrapper/>}/>
          <Route path="/preference" render={() => <PreferenceTemplateWrapper darkTheme={darkTheme} handleThemeToggle={handleThemeToggle}></PreferenceTemplateWrapper>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </Router>
  );
}

export default App;
