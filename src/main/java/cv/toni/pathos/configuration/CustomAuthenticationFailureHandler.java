package cv.toni.pathos.configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            response.sendRedirect(response.encodeRedirectURL("/login?errorLog" +
                    "=true"));
        }
        else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
            response.sendRedirect(response.encodeRedirectURL("/login?errorAct=true"));
        }
        else{
            response.sendRedirect(response.encodeRedirectURL("/login?error=true"));
        }

    }
}