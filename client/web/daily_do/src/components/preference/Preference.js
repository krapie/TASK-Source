import './Preference.css';

const Preference = ({ darkTheme, onToggle }) => {    
    return (
        <div className="template-wrapper preference-template-wrapper">
            <div className="title">
                <h2>기본 설정</h2>
                <p></p>
            </div>
            <div className="content">
                <ul className="general">
                    <li className="theme">
                        <span className="text">다크 모드</span>
                        <label className="switch">
                            <input type="checkbox" checked={darkTheme} name="theme" onChange={onToggle}></input>
                            <span className="slider round"></span>
                        </label>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default Preference;