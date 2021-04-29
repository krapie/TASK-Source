import DaydoItem from './DaydoItem';
import './DaydoItemList.css';

const DaydoItemList = ({ daydoList, onRemove, onChange }) => {
    let daydoListHTML;

    if(!daydoList) {
        daydoListHTML = <p>로딩중...</p>;
    }
    else if(daydoList.length === 0) {
        daydoListHTML = <p>**일에 할 일을 적어주세요!</p>
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