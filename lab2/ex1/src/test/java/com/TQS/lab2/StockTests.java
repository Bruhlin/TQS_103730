package com.TQS.lab2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StockTests {

    @Mock
    IStockmarketService stockmarket;

    @InjectMocks
    StocksPortfolio portfolio;
    
    @Test
    public void testGetTotalValue() {
        when(stockmarket.lookUpPrice("microsoft")).thenReturn(100.0);
        when(stockmarket.lookUpPrice("apple")).thenReturn(200.0);
        when(stockmarket.lookUpPrice("amazon")).thenReturn(250.0);

        portfolio.addStock(new Stock("microsoft", 2));
        portfolio.addStock(new Stock("apple", 3));

        assertThat(portfolio.getTotalValue(), equalTo(800.0));

        portfolio.addStock(new Stock("amazon", 2));

        assertThat(portfolio.getTotalValue(), equalTo(1300.0));
    }

    @Test
    public void testMostValuableStocks() {
        when(stockmarket.lookUpPrice("microsoft")).thenReturn(100.0);
        when(stockmarket.lookUpPrice("apple")).thenReturn(200.0);
        when(stockmarket.lookUpPrice("amazon")).thenReturn(250.0);
        when(stockmarket.lookUpPrice("google")).thenReturn(150.0);
        when(stockmarket.lookUpPrice("facebook")).thenReturn(300.0);

        portfolio.addStock(new Stock("microsoft", 2)); // 200
        portfolio.addStock(new Stock("apple", 3)); // 600
        portfolio.addStock(new Stock("amazon", 2)); // 500
        portfolio.addStock(new Stock("google", 1)); // 150
        portfolio.addStock(new Stock("facebook", 1)); // 300

        List<Stock> top3Stocks = portfolio.mostValuableStocks(3);

        assertThat(top3Stocks.get(0).getLabel(), equalTo("apple"));
        assertThat(top3Stocks.get(1).getLabel(), equalTo("amazon"));
        assertThat(top3Stocks.get(2).getLabel(), equalTo("facebook"));
    }

    @Test
    public void testMostValuableStocksWithLessThanNStocks() {
        when(stockmarket.lookUpPrice("microsoft")).thenReturn(100.0);
        when(stockmarket.lookUpPrice("apple")).thenReturn(200.0);

        portfolio.addStock(new Stock("microsoft", 2));
        portfolio.addStock(new Stock("apple", 3));

        List<Stock> top3Stocks = portfolio.mostValuableStocks(3);

        assertThat(top3Stocks.get(0).getLabel(), equalTo("apple"));
        assertThat(top3Stocks.get(1).getLabel(), equalTo("microsoft"));
    }

    @Test
    public void testMostValuableStocksWithNoStocks() {
        List<Stock> top3Stocks = portfolio.mostValuableStocks(3);

        assertThat(top3Stocks.size(), equalTo(0));
    }
}
