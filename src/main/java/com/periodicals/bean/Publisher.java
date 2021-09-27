package com.periodicals.bean;

public class Publisher extends AbstractBean<Integer> {
    private static final long serialVersionUID = -4845265445447500952L;

    private String publisherName;

    public Publisher() {
    }

    public Publisher(Integer id, String publisherName) {
        super(id);
        this.publisherName = publisherName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publisherName='" + publisherName + '\'' +
                ", id=" + getId() +
                '}';
    }
}
