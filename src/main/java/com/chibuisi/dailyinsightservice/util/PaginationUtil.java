package com.chibuisi.dailyinsightservice.util;

import org.springframework.data.domain.Sort;

public class PaginationUtil {
    public static Sort getSort(String orderBy) {
        String[] orderByFields = orderBy.split(",");
        Sort.Order[] orders = new Sort.Order[orderByFields.length];

        for (int i = 0; i < orderByFields.length; i++) {
            String[] fieldAndDirection = orderByFields[i].split(":");
            if (fieldAndDirection.length != 2) {
                throw new IllegalArgumentException("Invalid orderBy format");
            }

            String field = fieldAndDirection[0];
            String direction = fieldAndDirection[1].toUpperCase();

            if (!direction.equals("ASC") && !direction.equals("DESC")) {
                throw new IllegalArgumentException("Invalid sorting direction: " + direction);
            }

            orders[i] = new Sort.Order(Sort.Direction.fromString(direction), field);
        }
        return Sort.by(orders);
    }
}
