import "./Login.css";
import { serverURL } from "../../config";
import GoogleLogin from "./GoogleLogin";

function Login({ history }) {
  async function onGoogleSignIn(response) {
    const id_token = response.credential;
    const tokenExpireTime = 30 * 24 * 60 * 60 * 1000; //한 달

    let date = new Date();
    date.setTime(date.getTime() + tokenExpireTime);

    // 서버로 토큰 전송
    // 전송 완료 후 받은 userId 쿠기에 저장
    // 이후 대시보드로 Re-route
    fetch(`${serverURL}/api/google/tokensignin`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(id_token),
    })
      .then((response) => response.json())
      .then((userData) => {
        console.log("Login userId: " + userData.userId);

        document.cookie = `userId=${
          userData.userId
        }; expires=${date.toUTCString()} path=/`;
      })
      .then(() => {
        history.push("/dashboard");
      })
      .catch((error) => {
        alert(JSON.stringify(error, undefined, 2));
      });
  }

  return (
    <div className="login-wrapper component">
      <div className="information">
        <h1 id="logo">TASK</h1>
        <p>일정/시간 관리 서비스</p>
      </div>
      <GoogleLogin onGoogleSignIn={onGoogleSignIn} text="구글 로그인" />
    </div>
  );
}

export default Login;
