package br.com.supernova.tickerpanel.service;

import br.com.supernova.tickerpanel.exception.ResourceAlreadyRegisteredException;
import br.com.supernova.tickerpanel.exception.ResourceNotFoundException;
import br.com.supernova.tickerpanel.mapper.StockMapper;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.model.entity.Stock;
import br.com.supernova.tickerpanel.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper mapper = StockMapper.INSTANCE;

    private final StockRepository repository;

    @Transactional
    public StockDTO createStock(StockDTO stockDTO) throws ResourceAlreadyRegisteredException {
        checkIfThereIsARecordByNameAndDate(stockDTO.getName(), stockDTO.getDate());
        Stock stockEntity = mapper.toEntity(stockDTO);
        Stock saveStock = repository.save(stockEntity);
        return mapper.toDTO(saveStock);
    }

    @Transactional(readOnly = true)
    public StockDTO checkStockName(String name) {
        Stock returnedStock = repository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException(name)
        );
        return mapper.toDTO(returnedStock);
    }

    @Transactional(readOnly = true)
    public StockDTO checkStockID(Long id) {
        return mapper.toDTO(checkIfThereIsARecordByID(id));
    }

    @Transactional(readOnly = true)
    public List<StockDTO> listAllStocks() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StockDTO> listAllStocksToday(){
        List<Stock> stockList = repository.findByAllToday(LocalDate.now()).orElseThrow(ResourceNotFoundException::new);
        return stockList.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StockDTO updateStock(StockDTO builderDTO) throws ResourceNotFoundException {
        Optional<Stock> returnedStock = repository.findByStockUpdate(builderDTO.getName(), builderDTO.getDate(), builderDTO.getId());
        if(returnedStock.isPresent())
            throw new ResourceNotFoundException(builderDTO.getName());

        Stock stock = mapper.toEntity(builderDTO);
        Stock updatedStock = repository.save(stock);

        return mapper.toDTO(updatedStock);
    }

    @Transactional
    public void deleteStock(Long id) {
        Stock returnedStock = checkIfThereIsARecordByID(id);
        repository.delete(returnedStock);
    }

    private void checkIfThereIsARecordByNameAndDate(String nameStock, LocalDate dateTicker) throws ResourceAlreadyRegisteredException {
        Optional<Stock> returnedStock = repository.findByNameAndDate(nameStock, dateTicker);
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
