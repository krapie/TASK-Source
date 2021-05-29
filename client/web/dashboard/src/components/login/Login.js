import { useEffect, useRef } from 'react';
import './Login.css';

function Login({ history }) {
    const googleLoginBtn = useRef(null);
  
    //SDK 초기 설정 및 내 API초기화
    useEffect(() => {
        //구글 SDK 불러오기
        (function (d, s, id) {
            let js;
            const fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {
                return;
            }
            js = d.createElement(s);
            js.id = id;
            js.src = "https://apis.google.com/js/platform.js?onload=googleSDKLoaded";
            fjs.parentNode.insertBefore(js, fjs);
        })(document, "script", "google-jssdk");

        // SDK 로드 되면, 초기설정 시작
        window.googleSDKLoaded = () => {
            window.gapi.load("auth2", () => {
                const auth2 = window.gapi.auth2.init({
                    client_id:
                        "855394650411-buaopph1kokclaq6l9i8tirma6u2svf0.apps.googleusercontent.com"
                });
                //버튼 클릭시 사용자 정보 불러오기
                auth2.attachClickHandler(
                    googleLoginBtn.current,
                    {},
                    (googleUser) => {
                        onSuccess(googleUser);
                    },
                    (error) => {
                        onFailure(error);
                    }
                );
            });
        };
    }, []);

    function onSuccess(googleUser) {
        const id_token = googleUser.getAuthResponse().id_token;
        const tokenExpireTime = 60*60; //1시간

        localStorage.setItem("idToken", id_token);
        document.cookie = `idToken=${id_token}; max-age=${tokenExpireTime} path=/`;
        /* 
        const profile = googleUser.getBasicProfile();
        console.log(profile);
        console.log(`Token: ${googleUser.getAuthResponse().id_token}`);
        console.log(`ID: ${profile.getId()}`);
        console.log(`Name: ${profile.getName()}`);
        console.log(`Image URL: ${profile.getImageUrl()}`);
        console.log(`Email: ${profile.getEmail()}`);
        */
       
        // 서버로 토큰 전송
        // 전송 완료 후 대시보드로 Re-route
        fetch('http://ec2-3-36-251-188.ap-northeast-2.compute.amazonaws.com/api/google/tokensignin', {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify(id_token)
        })
            .then((response) => response.json())
            .then((userName) => {
                console.log('Signed in as: ' + userName);
                history.push("/dashboard");
            });
    }

    function onFailure(error) {
        alert(JSON.stringify(error, undefined, 2));
    }


    return (
        <div className="login-wrapper component">
            <div className="information">
                <h1 id="logo">TASK</h1>
                <p>일정/시간 관리 서비스</p>
            </div>
            <div ref={googleLoginBtn} id="customBtn" className="customGPlusSignIn">
                <span className="icon"></span>
                <span className="buttonText">구글 로그인</span>
            </div>
        </div>
    );
}

export default Login