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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping("/stocks")
    public ResponseEntity<List<StockDTO>> returnAllStocks(){
        return ResponseEntity.ok(service.listAllStocks());
    }

    @GetMapping("/stock-name/{name}")
    public ResponseEntity<StockDTO> searchStockDTOByName(@PathVariable String name) {
        return ResponseEntity.ok(service.checkStockName(name));
    }

    @GetMapping("/stock-id/{id}")
    public ResponseEntity<StockDTO> searchStockDTOByID(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkStockID(id));
    }

    @PostMapping("/stock-create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StockDTO> registerStockDTO(@Valid @RequestBody StockDTO stockDTO) throws ResourceAlreadyRegisteredException {
        return ResponseEntity.ok(service.createStock(stockDTO));
    }

    @PutMapping("/stock-update/{id}")
    public ResponseEntity<StockDTO> updateStockDTO(@PathVariable Long id, @Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(service.updateStock(id, stockDTO));
    }

    @DeleteMapping("/stock-delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteStockDTO(@PathVariable Long id) {
        Map<String, Boolean> mapDelete = new HashMap<>();
        mapDelete.put("Record deleted", true);
        service.deleteStock(id);
        return ResponseEntity.ok(mapDelete);
    }
}
