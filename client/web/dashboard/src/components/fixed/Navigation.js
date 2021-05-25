import { Link } from 'react-router-dom';
import './Navigation.css';

const Navigation = ({ userInfo }) => {

    function signOut() {
        var auth2 = window.gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('User signed out.');
            window.location.replace('http://komputer-task.ml');
        });
    }

    function handleToggle() {
        let dropdown = document.querySelector('.user-info-dropdown');
        dropdown.classList.toggle('show');
    }

    return (
        <nav className="navigation">
            <ul className="navigation-list">
                <li id="logo">&pi;</li>
                <li><Link to="/dashboard">대시보드</Link></li>
                <li><Link to="/preference">설정</Link></li>
                <li>
                    <div className="dropdown">
                        <div className="navigation-user-info" onClick={handleToggle}>
                            <img className="navigation-user_picture" src={userInfo.pictureUrl} alt={userInfo.name}></img>
                            <span className="navigation-user-name">{userInfo.name}</span>
                        </div>
                        <div className="user-info-dropdown">
                            <p className="logout" onClick={signOut}>로그아웃</p>
                        </div>
                    </div>
                </li>
            </ul>
        </nav>
    );
}

export default Navigation;