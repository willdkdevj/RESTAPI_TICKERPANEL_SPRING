package br.com.supernova.tickerpanel.controller;

import br.com.supernova.tickerpanel.exception.ResourceAlreadyRegisteredException;
import br.com.supernova.tickerpanel.model.dto.StockDTO;
import br.com.supernova.tickerpanel.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping(value = "/stocks")
    public ResponseEntity<List<StockDTO>> returnAllStocks(){
        return ResponseEntity.ok(service.listAllStocks());
    }

    @GetMapping(value = "/stocks-today")
    public ResponseEntity<List<StockDTO>> returnAllStocksToday(){
        return ResponseEntity.ok(service.listAllStocksToday());
    }

    @GetMapping(value = "/stock-name/{name}")
    public ResponseEntity<StockDTO> searchStockDTOByName(@PathVariable String name) {
        return ResponseEntity.ok(service.checkStockName(name));
    }

    @GetMapping(value = "/stock-id/{id}")
    public ResponseEntity<StockDTO> searchStockDTOByID(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkStockID(id));
    }

    @PostMapping(value = "/stock-create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StockDTO> registerStockDTO(@Valid @RequestBody StockDTO stockDTO) throws ResourceAlreadyRegisteredException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createStock(stockDTO));
    }

    @PutMapping(value = "/stock-update")
    public ResponseEntity<StockDTO> updateStockDTO(@Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(service.updateStock(stockDTO));
    }

    @DeleteMapping(value = "/stock-delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Map<String, Boolean>> deleteStockDTO(@PathVariable Long id) {
        Map<String, Boolean> mapDelete = new HashMap<>();
        mapDelete.put("Record deleted", true);
        service.deleteStock(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapDelete);
    }
}
