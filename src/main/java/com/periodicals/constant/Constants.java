package com.periodicals.constant;

public class Constants {
    public static final String CATEGORY_LIST = "CATEGORY_LIST";
    public static final String PUBLISHER_LIST = "PUBLISHER_LIST";
    public static final String MAGAZINE_LIST = "magazineList";
    public static final int MAX_PRODUCTS_PER_HTML_PAGE = 12;
    public static final int MAX_SUBSCRIPTIONS_PER_HTML_PAGE = 6;
    public static final int MAX_USERS_PER_HTML_PAGE = 6;
    public static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
    public static final String ATT_NAME_SORTING_NAME = "SORTING";
    public static final String ATT_NAME_NO_OF_SUB_PAGES = "noOfSubPages";
    public static final String ATT_NAME_NO_OF_MAGAZINE_PAGES = "noOfPages";
    public static final String ATT_NAME_NO_OF_USER_PAGES = "noOfUsrPages";
    public static final String PASSWORD_VALIDATION_MESSAGE = "The password must be at least " +
            "5 characters long, contain at least one digit and the special character @#$%^&+=";

    public enum SortingParamName {
        NAME_ASC("magazine_name-asc"),
        NAME_DESC("magazine_name-desc"),
        PRICE_ASC("price-asc"),
        PRICE_DESC("price-desc"),
        NOT_FOUND("not-found");

        private final String value;

        SortingParamName(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static SortingParamName fromValue(String value) {
            for (SortingParamName name : values()) {
                if (name.value.equalsIgnoreCase(value))
                    return name;
            }
            return NOT_FOUND;
        }
    }
}
