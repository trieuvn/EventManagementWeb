/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.interceptor;

import com.uef.annotation.RoleRequired;
import com.uef.model.USER;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author Admin
 */
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RoleRequired roleRequired = method.getMethodAnnotation(RoleRequired.class);
            if (roleRequired != null) {
                HttpSession session = request.getSession();
                USER user = (USER) session.getAttribute("user");
                String role = "";
                if (user == null){
                    role = "";
                }else if (user.getRole() == 0){
                    role = "ADMIN";
                }else if (user.getRole() == 1){
                    role = "USER";
                }
                String[] allowedRoles = roleRequired.value();
                String[] userRoles = role.split(",");
                boolean authorized = Arrays.stream(userRoles)
                        .map(String::trim)
                        .anyMatch(r -> Arrays.asList(allowedRoles).contains(r));
                if (!authorized ) {
                    response.sendRedirect(request.getContextPath() + "/");
                    return false;
                }
            }
        }
        return true;
    }
}
