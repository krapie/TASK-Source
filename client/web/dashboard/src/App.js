import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Navigation from './components/fixed/Navigation';
import Footer from './components/fixed/Footer'
import DashboardWrapper from './components/dashboard/DashboardWrapper';

function App() {
  return (
    <Router>
      <div className="main">
        <Navigation></Navigation>
        <Switch>
          <Route path="/" exact render={() => <DashboardWrapper/>}/>
          <Route path="/preference" render={() => <div>설정</div>}/>
        </Switch>
        <Footer></Footer>
      </div>
    </Router>
  );
}

export default App;
