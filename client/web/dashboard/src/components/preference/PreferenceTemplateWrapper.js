import { useEffect } from 'react';
import Preference from './Preference';
import Navigation from '../fixed/Navigation';

function PreferenceTemplateWrapper({ darkTheme, handleThemeToggle }) {
    
    useEffect(() => { // 랜더링 이후 다크 모드 설정 (추후 리팩토링)
        const navigation = document.querySelector('.navigation');
    
        if(darkTheme) {  
          navigation.classList.add('dark');
        }
        else {
          navigation.classList.remove('dark');
        }
      }, [ darkTheme ]);

    return (
        <div className="navigation-preference-wrapper wrapper">
            <Navigation></Navigation>
            <h1 style={{ fontSize : '3rem', marginBottom : '10%'}}>설정</h1>
            <div className="preferences">
                <Preference darkTheme={darkTheme} onToggle={handleThemeToggle}></Preference>
            </div>
        </div>
    );
}

export default PreferenceTemplateWrapper;