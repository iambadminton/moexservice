package ru.shipulin.moexservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shipulin.moexservice.dto.FigiesDto;
import ru.shipulin.moexservice.dto.StockPricesDto;
import ru.shipulin.moexservice.dto.StocksDto;
import ru.shipulin.moexservice.dto.TickersDto;
import ru.shipulin.moexservice.service.BondService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
public class MoexBondController {

    private final BondService bondService;

    @PostMapping("/getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickersDto) {

        return bondService.getBondsFromMoex(tickersDto);
    }

    @PostMapping("/prices")
    public StockPricesDto getPricesByFigies(@RequestBody FigiesDto figiesDto) {
        return bondService.getPricesByFigies(figiesDto);
    }
}
