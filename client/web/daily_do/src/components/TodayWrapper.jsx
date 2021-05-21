import TodoListTemplate from './todo/TodoListTemplate';

function TodayWrapper({ idToken, darkTheme, todayDateHTML }) {
    return (
    <div className="component">
        <h1 style={{ fontSize : '3rem'}}>오늘 할 일은?</h1>
        {todayDateHTML}
        <TodoListTemplate idToken={idToken} darkTheme={darkTheme}></TodoListTemplate>
        <p style={{ color : "gray" }}>요일별로 할 일이 자동으로 갱신됩니다!</p>
    </div>
    );
  }
  
  export default TodayWrapper;