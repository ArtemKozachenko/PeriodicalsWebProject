package com.periodicals.servlet;

import com.periodicals.constant.Constants;
import com.periodicals.bean.Category;
import com.periodicals.bean.Magazine;
import com.periodicals.bean.Subscription;
import com.periodicals.bean.User;
import com.periodicals.form.SearchForm;
import com.periodicals.manager.MagazineManager;
import com.periodicals.manager.UserManager;
import com.periodicals.util.SortingUtils;
import com.periodicals.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;


public class AbstractServlet extends HttpServlet {
    private static final long serialVersionUID = 1284344275572402718L;

    private MagazineManager magazineManager;
    private UserManager userManager;

    @Override
    public void init() throws ServletException {
        magazineManager = MagazineManager.getInstance();
        userManager = UserManager.getInstance();
    }

    public final MagazineManager getMagazineManager() {
        return magazineManager;
    }

    public final UserManager getUserManager() {
        return userManager;
    }

    public final String[] getSortingParams(HttpServletRequest request) {
        String sorting = SortingUtils.getSortingFromRequest(request);
        String[] params = null;
        if (sorting != null) {
            params = sorting.split("-");
        }
        return params;
    }

    public final int getRecordsLimit(HttpServletRequest request) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("p_control_panel", "Y");
        }
        request.setAttribute("page", page);
        return (page - 1) * Constants.MAX_PRODUCTS_PER_HTML_PAGE;
    }

    public final int getUsrRecordsLimit(HttpServletRequest request) {
        int page = 1;
        if (request.getParameter("pageUsr") != null) {
            page = Integer.parseInt(request.getParameter("pageUsr"));
            request.setAttribute("p_users_panel", "Y");
        }
        request.setAttribute("pageUsr", page);
        return (page - 1) * Constants.MAX_USERS_PER_HTML_PAGE;
    }

    public final int getOffset() {
        return Constants.MAX_PRODUCTS_PER_HTML_PAGE;
    }

    public final int getUsrOffset() {
        return Constants.MAX_USERS_PER_HTML_PAGE;
    }

    public final int setBeginValue(HttpServletRequest request) {
        int page = 1;
        if (request.getParameter("pageSub") != null) {
            page = Integer.parseInt(request.getParameter("pageSub"));
            request.setAttribute("subscribe", "Y");
        }
        request.setAttribute("pageSub", page);
        int begin = (page - 1) * Constants.MAX_SUBSCRIPTIONS_PER_HTML_PAGE;
        request.setAttribute("begin", begin);
        return begin;
    }

    public final void setEndValue(HttpServletRequest request, int beginValue) {
        int end = (beginValue + Constants.MAX_SUBSCRIPTIONS_PER_HTML_PAGE) - 1;
        request.setAttribute("end", end);
    }

    public final int getCountOfMagazinesByCategory(String url) {
        int count = 0;
        List<Category> categories = (List<Category>) getServletContext().getAttribute(Constants.CATEGORY_LIST);
        for (Category category : categories) {
            if (category.getCategoryUrl().equals(url)) {
                count = category.getProductCount();
            }
        }
        return count;
    }

    public final void setNoOfPages(HttpServletRequest request, int noOfRecords, int maxValuesPerPage, String attrName) {
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / maxValuesPerPage);
        request.setAttribute(attrName, noOfPages);
    }

    public final SearchForm createSearchForm(HttpServletRequest request) {
        return new SearchForm(
                request.getParameter("query"),
                request.getParameterValues("category"),
                request.getParameterValues("publisher"));
    }

    public final boolean canAfford(User user, BigDecimal price) {
        BigDecimal balance = user.getWallet().getAmountOfMoney();
        if ((balance.subtract(price)).compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        user.getWallet().setAmountOfMoney(balance.subtract(price));
        return true;
    }

    public final void setMagazinesInSession(HttpServletRequest request, List<Magazine> magazines) {
        request.getSession().setAttribute(Constants.MAGAZINE_LIST, magazines);
    }

    public final Magazine findMagazineById(HttpSession session, int magazineId) {
        for (Magazine magazine : (List<Magazine>) session.getAttribute(Constants.MAGAZINE_LIST)) {
            if (magazineId == magazine.getId()) {
                return magazine;
            }
        }
        return null;
    }

    public final void updateMagazineInSubscription(HttpSession session, Magazine magazine) {
        User loginedUser = UserUtils.getLoginedUser(session);
        List<Subscription> subscriptions = loginedUser.getSubscriptions();
        for (Subscription subscription : subscriptions) {
            Magazine magazineFromSub = subscription.getMagazine();
            if (magazineFromSub.getId().equals(magazine.getId())) {
                String imageLink = magazineFromSub.getImageLink();
                magazine.setImageLink(imageLink);
                subscription.setMagazine(magazine);
            }
        }
    }

    public final String uploadImage(Part filePart) throws IOException {
        String fileName = filePart.getSubmittedFileName();
        String imageLink = null;
        if (fileName != null && !fileName.isEmpty()) {
            File uploads = new File(getServletContext().getRealPath("media"));
            String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf('.'));
            File file = File.createTempFile(fileNameWithoutSuffix, ".jpg", uploads);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            imageLink = "/media/" + file.getName();
        }
        return imageLink;
    }

    public final boolean isImageLoaded(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        return fileName != null && !fileName.isEmpty();
    }

    public final void deleteOldImageIfNewUploaded(HttpServletRequest request, Part filePart) {
        if (!isImageLoaded(filePart)) {
            return;
        }
        String imageLink = request.getParameter("imageLink");
        File fileToDelete = new File(getServletContext().getRealPath(imageLink));
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    public final void deleteOldImage(HttpServletRequest request) {
        String imageLink = request.getParameter("imageLink");
        File fileToDelete = new File(getServletContext().getRealPath(imageLink));
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

}
