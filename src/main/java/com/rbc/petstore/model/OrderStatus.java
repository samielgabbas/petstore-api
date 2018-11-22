package com.rbc.petstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Inventory related status. This enumerator represents the status of an
 * {@code Order}
 */
public enum OrderStatus {
    PLACED, APPROVED, DELIVERED;

    /**
     * @param status string representing the enum value
     * @return return the enum value associated with the status parameter. This method perform a none case-sensitive comparison
     */
    @JsonCreator
    public static OrderStatus fromValue(String status) {
        if (status != null) {
            for (OrderStatus enumStatus : OrderStatus.values()) {
                if (enumStatus.toString().equalsIgnoreCase(status)) {
                    return enumStatus;
                }
            }

            throw new IllegalArgumentException(status + " is an invalid value.");
        }

        throw new IllegalArgumentException("A value was not provided.");
    }
}
