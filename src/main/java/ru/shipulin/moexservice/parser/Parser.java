package ru.shipulin.moexservice.parser;

import ru.shipulin.moexservice.dto.BondDto;

import java.util.List;

public interface Parser {
    List<BondDto> parse(String ratesAsString);
}
