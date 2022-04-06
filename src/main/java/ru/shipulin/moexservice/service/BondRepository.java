package ru.shipulin.moexservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.shipulin.moexservice.dto.BondDto;
import ru.shipulin.moexservice.exception.LimitRequestException;
import ru.shipulin.moexservice.moexclient.CorporateBondsClient;
import ru.shipulin.moexservice.moexclient.GovernmentBondsClient;
import ru.shipulin.moexservice.parser.Parser;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BondRepository {

    private final Parser parser;

    private final CorporateBondsClient corporateBondsClient;

    private final GovernmentBondsClient governmentBondsClient;

    @Cacheable(value = "corps")
    public List<BondDto> getCorporateBonds() {
        log.info("Getting corporate bonds from Moex");
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds.");
            throw new LimitRequestException("Moex isn't answering for getting corporate bonds.");
        }
        return bondDtos;

    }

    @Cacheable(value = "govss")
    public List<BondDto> getGovBonds() {
        log.info("Getting government bonds from Moex");
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting government bonds.");
            throw new LimitRequestException("Moex isn't answering for getting government bonds.");
        }
        return bondDtos;

    }


}
