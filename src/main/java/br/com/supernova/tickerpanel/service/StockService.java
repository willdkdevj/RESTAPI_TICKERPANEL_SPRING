package br.com.supernova.tickerpanel.service;

import br.com.supernova.tickerpanel.exception.ResourceAlreadyRegisteredException;
import br.com.supernova.tickerpanel.exception.ResourceNotFoundException;
import br.com.supernova.tickerpanel.mapper.StockMapper;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.model.entity.Stock;
import br.com.supernova.tickerpanel.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper mapper = StockMapper.INSTANCE;

    private final StockRepository repository;

    public StockDTO createStock(StockDTO stockDTO) throws ResourceAlreadyRegisteredException {
        checkIfThereIsARecordByName(stockDTO.getName());
        Stock stockEntity = mapper.toEntity(stockDTO);
        Stock saveStock = repository.save(stockEntity);
        return mapper.toDTO(saveStock);
    }

    public StockDTO checkStockName(String name) {
        Stock returnedStock = repository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException(name)
        );
        return mapper.toDTO(returnedStock);
    }

    private void checkIfThereIsARecordByName(String nameStock) throws ResourceAlreadyRegisteredException {
        Optional<Stock> returnedStock = repository.findByName(nameStock);
        if(returnedStock.isPresent()){
            throw new ResourceAlreadyRegisteredException(nameStock);
        }
    }

    private Stock checkIfThereIsARecordByID(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }
}
