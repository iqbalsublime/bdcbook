package com.bdcyclists.bdcbook.domain;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/18/15.
 */
public enum BloodGroup {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("A+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String label;

    BloodGroup(String value) {
        this.label = value;
    }

    public String getLabel() {
        return label;
    }
}
