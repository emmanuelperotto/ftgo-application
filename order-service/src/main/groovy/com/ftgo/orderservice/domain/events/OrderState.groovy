package com.ftgo.orderservice.domain.events

enum OrderState {
    APPROVAL_PENDING,
    REVISION_PENDING,
    APPROVED,
    REJECTED
}