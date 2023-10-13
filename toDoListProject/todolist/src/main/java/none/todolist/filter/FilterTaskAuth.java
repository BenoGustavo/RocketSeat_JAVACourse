package none.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import none.todolist.user.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // getting the path of the request
        var servLetPath = request.getServletPath();

        if (servLetPath.startsWith("/tasks/")) {
            //
            // getting user credentials
            //

            // gets user credentials in base64
            var authorization = request.getHeader("Authorization");

            // removes the "Basic" part of the string
            var authEncoded = authorization.substring("Basic".length()).trim();

            // decodes the base64 to byte array
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

            // converts the byte array to string
            var authString = new String(authDecoded);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Verify user credentials based on database data
            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401, "User not found");
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Password incorrect");
                }
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
