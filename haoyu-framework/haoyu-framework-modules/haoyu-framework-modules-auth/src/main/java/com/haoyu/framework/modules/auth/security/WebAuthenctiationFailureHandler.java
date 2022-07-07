package com.haoyu.framework.modules.auth.security;


import cn.hutool.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if((String)request.getParameter("type")!=null&&((String)request.getParameter("type")).equals("ajax")) {
            JSONObject res = new JSONObject();
            res.put("success",false);
            res.put("msg",exception.getMessage());
            response.setStatus(500);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().append(res.toString());
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
