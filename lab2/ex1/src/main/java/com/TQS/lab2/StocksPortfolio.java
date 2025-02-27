package com.TQS.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0;
        for (Stock stock : stocks) {
            total += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return total;
    }

    /**
    * @param topN the number of most valuable stocks to return
    * @return a list with the topN most valuable stocks in the portfolio
    */
    public List<Stock> mostValuableStocks(int topN) {
        return stocks.stream()
            .sorted((s1, s2) -> Double.compare(
                stockmarket.lookUpPrice(s2.getLabel()) * s2.getQuantity(),
                stockmarket.lookUpPrice(s1.getLabel()) * s1.getQuantity()))
            .limit(topN)
            .collect(Collectors.toList());
    }

}
