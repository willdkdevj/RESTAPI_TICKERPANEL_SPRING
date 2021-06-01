package br.com.supernova.tickerpanel.builder;

import br.com.supernova.tickerpanel.model.dto.StockDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class StockDTOBuilder {

    @Builder.Default
    private static final Long ID = 1L;

    @Builder.Default
    private static final String NAME = "SANB11";

    @Builder.Default
    private static final String COMPANY = "Banco Santander Brasil";

    @Builder.Default
    private static final Double PRICE = 41.22D;

    @Builder.Default
    private static final LocalDate DATE = LocalDate.of(2021, 5, 31);

    @Builder.Default
    private static final Double VARIATION = 1.03D;

    public StockDTO toStockDTO(){
        return new StockDTO(ID, NAME, COMPANY, PRICE, DATE, VARIATION);
    }
}
