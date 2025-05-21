package com.paymentchain.transaction.util;

public enum Status {
    PENDING("01", "Pending"),
    SETTLED("02", "Settled"),
    REJECTED("03", "Rejected"),
    CANCELED("04", "Canceled");

    private final String code;
    private final String description;

    // Constructor to initialize code and description
    Status(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // Getter for code
    public String getCode() {
        return code;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Optional: A method to find Status by code
    public static Status fromCode(String code) {
        for (Status status : Status.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
