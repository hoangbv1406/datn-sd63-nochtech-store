package com.project.shopapp.models;

import java.util.Set;

public class OrderStatus {

    public static final String PENDING = "pending";
    public static final String PROCESSING = "processing";
    public static final String SHIPPED = "shipped";
    public static final String DELIVERED = "delivered";
    public static final String CANCELLED = "cancelled";

    // List of valid statuses
    public static final Set<String> VALID_STATUSES = Set.of(
            PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    );

}
