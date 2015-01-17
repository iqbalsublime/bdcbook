package com.bdcyclists.bdcbook.domain;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/18/15.
 */
public enum Gender {
    MALE("Male"), FEMALE("Female");

    private String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
