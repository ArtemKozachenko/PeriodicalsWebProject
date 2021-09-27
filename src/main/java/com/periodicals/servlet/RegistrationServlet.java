package com.periodicals.servlet;

import com.periodicals.constant.Constants;
import com.periodicals.bean.User;
import com.periodicals.util.PasswordUtils;
import com.periodicals.util.RoutingUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/register")
public class RegistrationServlet extends AbstractServlet {
    private static final long serialVersionUID = -4413874686223286015L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String gender = request.getParameter("gender");

        boolean hasError = false;
        String errorString = null;
        User user;
        if (!PasswordUtils.isValidatedPassword(password)) {
            hasError = true;
            errorString = Constants.PASSWORD_VALIDATION_MESSAGE;
        } else if (!PasswordUtils.isConfirmedPassword(password, confirmPassword)) {
            hasError = true;
            errorString = "Password confirmation doesn't match password.";
        } else {
            try {
                user = getUserManager().findUserByLogin(login);
                if (user != null) {
                    hasError = true;
                    errorString = "Login '" + login + "' already in use.";
                }
                if (!hasError) {
                    user = getUserManager().findUserByEmail(email);
                    if (user != null) {
                        hasError = true;
                        errorString = "Email address already in use.\n" +
                                "You indicated you're a new customer, but an account " +
                                "already exists with the email address " + email;
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGender(gender);

        if (hasError) {
            request.setAttribute("user", user);
            request.setAttribute("registrationErrorString", errorString);
            doGet(request, response);
            return;
        }

        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashThePlainTextPassword(user.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setCreationDate(LocalDate.now());
        try {
            getUserManager().insertNewUser(user);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loginedUser = UserUtils.getLoginedUser(request.getSession());
        if (loginedUser != null) {
            response.sendRedirect(request.getContextPath() + "/magazines");
            return;
        }
        request.setAttribute("registrationPage", "Y");
        RoutingUtils.forwardToPage("registration-form.jsp", request, response);
    }
}
