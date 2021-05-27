import { Link } from 'react-router-dom';
import './Navigation.css';

const Navigation = ({ userInfo }) => {
    return (
        <nav className="navigation">
            <ul className="navigation-list">
                <li id="logo"><a href="http://komputer-task.ml/#/dashboard" target="_blank" rel="noreferrer">&pi;</a></li>
                <li><Link to="/">뽀모</Link></li>
                <li><Link to="/preference">설정</Link></li>
                <li>
                    <div className="navigation-user-info">
                        <img className="navigation-user_picture" src={userInfo.pictureUrl} alt={userInfo.name}></img>
                        <span className="navigation-user-name">{userInfo.name}</span>
                    </div>
                </li>
            </ul>
        </nav>
    );
}

export default Navigation;