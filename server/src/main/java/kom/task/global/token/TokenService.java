package kom.task.global.token;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Service
public class TokenService {
    private static final String CLIENT_ID = "855394650411-buaopph1kokclaq6l9i8tirma6u2svf0.apps.googleusercontent.com";

    /*** TOKEN VERIFY ***/
    public GoogleIdToken.Payload tokenVerify(String tokenDtoString) {
        GoogleIdToken.Payload payload = null;

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)
        try {
            GoogleIdToken idToken = verifier.verify(tokenDtoString);
            if (idToken != null) {
                payload = idToken.getPayload();
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return payload;
    }
}
