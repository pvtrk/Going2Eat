package pl.camp.it.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pl.camp.it.model.UserRole;
import pl.camp.it.session.SessionObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {
    @Autowired
    SessionObject sessionObject;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.sessionObject = WebApplicationContextUtils.
                getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean(SessionObject.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if ((sessionObject.getUser() != null && sessionObject.isLogged())
                && sessionObject.getUser().getRole().equals(UserRole.USER)) {

            filterChain.doFilter(httpServletRequest, servletResponse);
            System.out.println("Ten użytkownik ma dostęp do tej strony");

        } else if ((sessionObject.getUser() != null && sessionObject.isLogged())
                && sessionObject.getUser().getRole().equals(UserRole.ADMIN) ) {

            System.out.println("Panie Adminie proszę nie świrować");
            httpServletResponse.sendRedirect("/adminMain");

        } else if ((sessionObject.getUser() != null && sessionObject.isLogged())
                && sessionObject.getUser().getRole().equals(UserRole.RESTORER) ) {

            System.out.println("Ten uzytkownik nie ma dostępu do tych treści");
            httpServletResponse.sendRedirect("/restorerMain");

        } else {
            System.out.println("Ten uzytkownik nie ma dostępu do tych treści");
            httpServletResponse.sendRedirect("/login");
        }

    }

    @Override
    public void destroy() {

    }
}
