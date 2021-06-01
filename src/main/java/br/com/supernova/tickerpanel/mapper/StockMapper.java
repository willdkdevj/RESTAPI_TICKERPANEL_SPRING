package br.com.supernova.tickerpanel.mapper;

import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.model.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    @Mapping(target = "date", source = "date", dateFormat = "dd/MM/yyyy")
    Stock toEntity(StockDTO stockDTO);

    StockDTO toDTO(Stock stock);
}
