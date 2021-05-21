import './PreferenceTemplate.css'; 
import DarkMode from "./DarkMode";
import TimerChange from "./TimerChange";

const PreferenceTemplate = ({ idToken, darkTheme, onToggle }) => {

    return (
        <div className="preference_template">
            <h1 className="preference_title">설정</h1>
            <ul>
                <li>
                    <TimerChange idToken={idToken}></TimerChange>
                </li>
                <li>
                    <DarkMode darkTheme={darkTheme} onToggle={onToggle}></DarkMode>
                </li>
            </ul>
        </div>
    );
}

export default PreferenceTemplate;