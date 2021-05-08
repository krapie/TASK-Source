import './App.css';
import TimerTemplate from './components/timer/TimerTemplate';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import PreferenceTemplate from './components/preference/PreferenceTemplate';
import Navigation from './components/fixed/Navigation';
import Footer from './components/fixed/Footer';

function App() {
  return (
    <Router>
      <div className="main">
        <Navigation></Navigation>
        <Switch>
          <Route path="/" exact render={() => <TimerTemplate></TimerTemplate>}/>
          <Route path="/preference" render={() => <PreferenceTemplate></PreferenceTemplate>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </Router>
  );
}

export default App;
