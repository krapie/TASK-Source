import Dashboard from './Dashboard';
import './DashboardTemplateWrapper.css';

function DashboardTemplateWrapper({ darkTheme,todayDateHTML }) {
    return (
    <div className="dashboard_wrapper">
        <Dashboard></Dashboard>
    </div>
    );
  }
  
  export default DashboardTemplateWrapper;