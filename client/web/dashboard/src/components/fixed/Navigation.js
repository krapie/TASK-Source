import { Link } from 'react-router-dom';
import './Navigation.css';
import { homepageURL } from '../../config'

const Navigation = ({ userInfo }) => {
    function logout() {
        document.cookie = `userId= ; expires=Thu, 01 Jan 1999 00:00:10 GMT;`;
        window.location.replace(homepageURL);
    }

    function handleToggle() {
        let dropdown = document.querySelector('.user-info-dropdown');
        dropdown.classList.toggle('show');
    }

    return (
        <nav className="navigation">
            <ul className="navigation-list">
                <li id="logo"><a href="https://github.com/Krapi0314" target="_blank" rel="noreferrer">&pi;</a></li>
                <li><Link to="/dashboard">대시보드</Link></li>
                <li><Link to="/preference">설정</Link></li>
                <li>
                    <div className="dropdown">
                        <div className="navigation-user-info" onClick={handleToggle}>
                            <img className="navigation-user_picture" src={userInfo.pictureUrl} alt={userInfo.name}></img>
                            <span className="navigation-user-name">{userInfo.name}</span>
                        </div>
                        <div className="user-info-dropdown">
                            <p className="logout" onClick={logout}>로그아웃</p>
                        </div>
                    </div>
                </li>
            </ul>
        </nav>
    );
}

export default Navigation;