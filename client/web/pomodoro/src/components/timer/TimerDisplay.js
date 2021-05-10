import './TimerDisplay.css';

const TimerDisplay = ({ minutes, seconds }) => {
    return (
        <h1 className="timer_display">
            {minutes < 10 ? '0' : ''}{minutes}:{seconds < 10 ? '0' : ''}{seconds} 
        </h1>
    );
}

export default TimerDisplay;