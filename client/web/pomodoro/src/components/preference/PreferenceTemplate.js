import TimerChange from "./TimerChange"

const PreferenceTemplate = () => {

    return (
        <div className="preferenceTemplate component">
            <h1>설정</h1>
            <ul>
                <li>
                    <TimerChange></TimerChange>
                </li>
                {/*
                <li>
                    <div>
                        <h3>다크 모드</h3>
                        <div className="theme_toggle">
                            <label className="switch">
                                <input type="checkbox"></input>
                                <span className="slider round"></span>
                            </label>
                        </div>
                    </div>
                </li>
                */}
            </ul>
        </div>
    );
}

export default PreferenceTemplate;