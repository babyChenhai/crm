package zstu.crm.filter;


import zstu.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入到登陆拦截");
        //将请求、相应对象转为Http
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        if (path.contains("login")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            //判断session域中是否有 user 对象，如果有，则说明登陆过，放行。无则拦截
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            //拦截，重定向至登陆页
            if (user == null){
                //request.getContextPath() 取得站点的根目录 /crm
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }else { //放行
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }

    }
}
