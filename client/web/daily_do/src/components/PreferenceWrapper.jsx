import DayDoListTemplate from './daydo/DaydoListTemplate';
import Preference from './preference/Preference';

function PreferenceWrapper({ idToken, darkTheme, handleThemeToggle }) {
  return (
    <div className="preference-wrapper">
    <div className="component">
        <h1 style={{ fontSize : '3rem'}}>설정</h1>
        <p>요일별 할 일 설정</p>
        <DayDoListTemplate idToken={idToken} darkTheme={darkTheme}></DayDoListTemplate>
        <p style={{ color : "gray" }}>요일별로 할 일을 설정하면<br></br> 해당 요일에 자동으로 할 일이 갱신됩니다!</p>
    </div>
    <div className="component"> 
      <Preference darkTheme={darkTheme} onToggle={handleThemeToggle}></Preference>
    </div>
  </div>
  );
}

export default PreferenceWrapper;