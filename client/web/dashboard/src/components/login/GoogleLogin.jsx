import { useRef } from "react";
import useScript from "../../hooks/useScript";
import { clientId } from "../../config";

// https://github.com/anthonyjgrove/react-google-login/issues/502
// https://developers.google.com/identity/gsi/web/reference/js-reference#CredentialResponse
export default function GoogleLogin({
  onGoogleSignIn = () => {},
  text = "signin_with",
}) {
  const googleSignInButton = useRef(null);

  useScript("https://accounts.google.com/gsi/client", () => {
    // https://developers.google.com/identity/gsi/web/reference/js-reference#google.accounts.id.initialize
    window.google.accounts.id.initialize({
      client_id: clientId,
      callback: onGoogleSignIn,
    });
    // https://developers.google.com/identity/gsi/web/reference/js-reference#google.accounts.id.renderButton
    window.google.accounts.id.renderButton(
      googleSignInButton.current,
      {
        theme: "filled_black",
        size: "large",
        text,
        width: "240",
        logo_alignment: "center",
      } // customization attributes
    );
  });

  return <div className="customGPlusSignIn" ref={googleSignInButton}></div>;
}
