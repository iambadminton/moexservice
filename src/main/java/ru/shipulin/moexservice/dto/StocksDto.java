package ru.shipulin.moexservice.dto;

import lombok.Value;
import ru.shipulin.moexservice.model.Stock;

import java.util.List;

@Value
public class StocksDto {
    List<Stock> stocks;

}
