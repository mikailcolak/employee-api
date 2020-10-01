package com.employee.api.model;

public class GenericResponse<T> {

    public static <K> GenericResponse<K> from(K value) {
        return new GenericResponse<K>(value);
    }

    private T value;

    public GenericResponse(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
