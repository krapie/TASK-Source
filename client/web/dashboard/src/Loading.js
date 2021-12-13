import { useEffect } from 'react';
import './Loading.css';
import { serverURL } from './config'

function Loading({ history }) {

    useEffect(() => {
        // userId 토큰이 존재한다면 자동 로그인
        const userIdLocation = document.cookie.split('; ').find(row => row.startsWith('userId'));

        if (userIdLocation !== undefined) {
            userIdAutoLogin(userIdLocation);
        }
        // 토큰이 존재하지 않는다면 로그인 화면으로 Re-route
        else {
            history.push("/login");
        }

    }, []);

    function userIdAutoLogin(userIdLocation) {
        const userId = userIdLocation.split('=')[1];
        
        // POST 방식으로 서버 전송
        fetch(`${serverURL}/api/user`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(userId)
        })
            .then((response) => {
                // 사용자 정보를 정상적으로 가져오면 (status: 200, ok)
                // 대시보드로 Re-route
                if (response.ok) {
                    history.push("/dashboard");
                }
                // 사용자 정보를 정상적으로 가져오지 못했으면 (status: 500)
                // userId 토큰 삭제 및 에러 메세지 출력
                // 로그인 화면으로 Re-route
                else {
                    document.cookie = `userId= ; expires=Thu, 01 Jan 1999 00:00:10 GMT;`;
                    console.log('Failed to login');
                    history.push("/login");
                }
            })
    }


    return (
        <div className="loading-wrapper component">
            <h1 id="loading_text">TASK loading...</h1>
        </div>
    );
}

export default Loading