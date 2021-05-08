import { Link } from 'react-router-dom';
import './Navigation.css';

const Navigation = () => {
    return (
        <nav className="navigation">
            <ul className="navigation-list">
                <li><Link to="/">타이머</Link></li>
                <li id="logo">&pi;</li>
                <li><Link to="/preference">설정</Link></li>
            </ul>
        </nav>
    );
}

export default Navigation;