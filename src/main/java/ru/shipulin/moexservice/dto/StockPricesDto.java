package ru.shipulin.moexservice.dto;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class StockPricesDto {
    private List<StockPrice> prices;
}
