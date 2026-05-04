package com.solvd.market.parser;

import com.solvd.market.market.Market;

public interface Parser {
    Market parse(String filePath) throws Exception;
}