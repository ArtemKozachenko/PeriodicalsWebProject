<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 27.09.2021
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<li role="presentation" class="${p_control_panel != null ? 'active' : ''}">
    <a href="#user-profile__periodicals" aria-controls="user-profile__periodicals" role="tab" data-toggle="tab"
       aria-expanded="${p_control_panel != null ? 'true' : 'false'}">Periodicals control panel</a>
</li>
<li role="presentation" class="${p_users_panel != null ? 'active' : ''}">
    <a href="#user-profile__users-list" aria-controls="user-profile__users-list" role="tab" data-toggle="tab"
       aria-expanded="${p_users_panel != null ? 'true' : 'false'}">Users control panel</a>
</li>
