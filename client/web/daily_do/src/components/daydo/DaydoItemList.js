import DaydoItem from './DaydoItem';
import './DaydoItemList.css';

const DaydoItemList = ({ day, daydoList, onRemove, onChange }) => {
    let daydoListHTML;
    const dayname = ['일','월','화','수','목','금','토','일'];       
    const dayHTML = dayname[day] + '요일';

    if(!daydoList) {
        daydoListHTML = <p>로딩중...</p>;
    }
    else if(daydoList.length === 0) {
        daydoListHTML = <div className="daydo-empty-list">{dayHTML}마다 할 일을 설정해주세요!</div>
    }
    else {
        daydoListHTML = daydoList.map(
            ({id, content}) => {
                return <DaydoItem
                    key={id}
                    id={id}
                    content={content}
                    onRemove={onRemove}
                    onChange={onChange}
                />
            }
        );
    }

    return (
        <ul className="todo-list-wrapper">
            {daydoListHTML}
        </ul>
    );
}

export default DaydoItemList;