import './DaydoSlider.css'

const DaydoSlider = ({ day, onSlide }) => {
    const dayname = ['일','월','화','수','목','금','토','일'];       
    const dayHTML = dayname[day] + '요일';

    
    return (
        <div className="daydo-title">
            <h2 className="left" onClick={onSlide}>&#60;</h2>
            <h2>{dayHTML}</h2>
            <h2 className="right" onClick={onSlide}>&#62;</h2>
    </div>
    );
}

export default DaydoSlider;