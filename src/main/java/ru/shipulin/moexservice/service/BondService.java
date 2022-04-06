package ru.shipulin.moexservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.shipulin.moexservice.dto.*;
import ru.shipulin.moexservice.exception.BondNotFoundException;
import ru.shipulin.moexservice.exception.LimitRequestException;
import ru.shipulin.moexservice.model.Currency;
import ru.shipulin.moexservice.model.Stock;
import ru.shipulin.moexservice.moexclient.CorporateBondsClient;
import ru.shipulin.moexservice.moexclient.GovernmentBondsClient;
import ru.shipulin.moexservice.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BondService {

    private final CacheManager cacheManager;

    private final BondRepository bondRepository;

    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getCorporateBonds());
        allBonds.addAll(bondRepository.getGovBonds());
        List<BondDto> resultList = allBonds.stream().filter(b -> tickersDto.getTickers().contains(b.getTicker()))
                .collect(Collectors.toList());
        List<Stock> stocks = resultList.stream().map(b -> {
                    return Stock.builder()
                            .ticker(b.getTicker())
                            .name(b.getName())
                            .figi(b.getName())
                            .type("Bond")
                            .currency(Currency.RUB)
                            .source("MOEX")
                            .build();
                }
        ).collect(Collectors.toList());
        return new StocksDto(stocks);


    }


    public StockPricesDto getPricesByFigies(FigiesDto figiesDto) {
        log.info("Request for figies {}", figiesDto.getFigies());
        List<String> figies = new ArrayList<>(figiesDto.getFigies());
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getGovBonds());
        allBonds.addAll(bondRepository.getCorporateBonds());
        figies.removeAll(allBonds.stream().map(b -> b.getTicker()).collect(Collectors.toList()));
        if(!figies.isEmpty()) {
            throw new BondNotFoundException(String.format("Bonds %s not found.", figies));
        }
        List<StockPrice> prices = allBonds.stream()
                .filter(b -> figiesDto.getFigies().contains(b.getTicker()))
                .map(b -> new StockPrice(b.getTicker(), b.getPrice() * 10))
                .collect(Collectors.toList());
        return new StockPricesDto(prices);
    }
}
