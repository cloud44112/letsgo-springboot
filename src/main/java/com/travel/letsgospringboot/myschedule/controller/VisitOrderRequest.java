package com.travel.letsgospringboot.myschedule.controller;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class VisitOrderRequest {
    private String[] visitItemIds;
    private int[] visitOrders;
    private String[] distances;
}
