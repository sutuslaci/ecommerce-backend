package com.ecommerce.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    CAN_ORDER("can_order");

    private final String permission;
}
