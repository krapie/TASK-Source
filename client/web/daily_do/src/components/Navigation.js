import './Navigation.css';

const Navigation = ({ onNavigation }) => {
    return (
        <nav className="navigation">
            <ul className="navigation-list">
                <li><span className="today" onClick={onNavigation}>오늘</span></li>
                <li>&pi;</li>
                <li><span className="preference" onClick={onNavigation}>설정</span></li>
            </ul>
        </nav>
    );
}

export default Navigation;