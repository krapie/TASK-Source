import Preference from './Preference';
import './PreferenceTemplateWrapper.css';

function PreferenceTemplateWrapper({ darkTheme, handleThemeToggle }) {
    return (
        <div className="preference-wrapper">
            <h1 style={{ fontSize : '3rem', marginBottom : '10%'}}>설정</h1>
            <div className="preferences">
                <Preference darkTheme={darkTheme} onToggle={handleThemeToggle}></Preference>
            </div>
        </div>
    );
}

export default PreferenceTemplateWrapper;