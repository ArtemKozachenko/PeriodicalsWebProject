package com.periodicals.bean;

import java.math.BigDecimal;

public class Magazine extends AbstractBean<Integer> {
    private static final long serialVersionUID = 6180546438572487699L;

    private String magazineName;
    private BigDecimal price;
    private String description;
    private String imageLink;
    private String categoryName;
    private String publisherName;
    private int categoryId;
    private int publisherId;

    public Magazine() {
    }

    public Magazine(Integer id, String magazineName, BigDecimal price, String description,
                    int categoryId, int publisherId) {
        super(id);
        this.magazineName = magazineName;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
    }

    public Magazine(String magazineName, BigDecimal price, String description, String imageLink,
                    int categoryId, int publisherId) {
        this.magazineName = magazineName;
        this.price = price;
        this.description = description;
        this.imageLink = imageLink;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "magazineName='" + magazineName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", id=" + getId() +
                '}';
    }
}
