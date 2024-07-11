package br.com.vklisman.todolist.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {


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
        System.out.println("Autorizacao");
        System.out.println(username);
        System.out.println(password);

        filterChain.doFilter(request, response);
    }
}
