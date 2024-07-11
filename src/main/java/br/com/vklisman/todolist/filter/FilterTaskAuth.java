package br.com.vklisman.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.vklisman.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authorization = request.getHeader("Authorization");

        var authEncode = authorization.substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authEncode);

        var authString = new String(authDecode);

        String[] credential = authString.split(":");
        String username = credential[0];
        String password = credential[1];

        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(401, "Unauthorized");
        } else {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if(passwordVerify.verified){
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401, "Unauthorized");
            }
        }

    }
}
