package com.periodicals.bean;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractBean<T extends Number> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T id;

    public AbstractBean() {
    }

    public AbstractBean(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBean<?> that = (AbstractBean<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "AbstractBean{" +
                "id=" + id +
                '}';
    }
}
