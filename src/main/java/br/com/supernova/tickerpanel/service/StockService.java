package br.com.supernova.tickerpanel.service;

import br.com.supernova.tickerpanel.mapper.StockMapper;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper mapper = StockMapper.INSTANCE;

    private final StockRepository repository;

    public StockDTO createStock(StockDTO stockDTO)
}
