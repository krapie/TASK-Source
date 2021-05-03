import DayDoListTemplate from './daydo/DaydoListTemplate';
import Preference from './preference/Preference';

function PreferenceWrapper({ darkTheme, handleThemeToggle }) {
  return (
    <div className="preference-wrapper">
    <div className="component">
        <h1>설정</h1>
        <p>요일별 할 일 설정</p>
        <DayDoListTemplate darkTheme={darkTheme}></DayDoListTemplate>
        <p style={{ color : "gray" }}>요일별로 할 일을 설정하면<br></br> 해당 요일에 자동으로 할 일이 갱신됩니다!</p>
    </div>
    <div className="component"> 
      <Preference darkTheme={darkTheme} onToggle={handleThemeToggle}></Preference>
      <p style={{ color : "gray" }}>설정 추가중...</p>
    </div>
  </div>
  );
}

export default PreferenceWrapper;