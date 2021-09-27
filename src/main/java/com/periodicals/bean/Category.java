package com.periodicals.bean;

public class Category extends AbstractBean<Integer> {
    private static final long serialVersionUID = 8712587695293507581L;

    private String categoryName;
    private String categoryUrl;
    private int productCount;

    public Category() {
    }

    public Category(Integer id, String categoryName, String categoryUrl, int productCount) {
        super(id);
        this.categoryName = categoryName;
        this.categoryUrl = categoryUrl;
        this.productCount = productCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryUrl='" + categoryUrl + '\'' +
                ", productCount=" + productCount +
                ", id=" + getId() +
                '}';
    }
}
