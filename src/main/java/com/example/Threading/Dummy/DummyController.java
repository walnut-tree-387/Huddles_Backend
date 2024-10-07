package com.example.Threading.Dummy;

import com.example.Threading.Dummy.Dto.DummyCreateDto;
import com.example.Threading.Dummy.Dto.DummyGetDto;
import com.example.Threading.Dummy.Dto.DummyUpdateDto;
import com.example.Threading.Helpers.AsyncTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/dummy")
public class DummyController {
    private final DummyService dummyService;
    private final AsyncTaskHandler asyncTaskHandler;
    private static final Logger logger = LoggerFactory.getLogger(DummyController.class);
    public DummyController(DummyService dummyService, AsyncTaskHandler asyncTaskHandler) {
        this.dummyService = dummyService;
        this.asyncTaskHandler = asyncTaskHandler;
    }
    @GetMapping
    public ResponseEntity<?> getDummy() {
        return new ResponseEntity<>(dummyService.getAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DummyCreateDto dummyCreateDto) {
        CompletableFuture<Void> future = asyncTaskHandler.executeAsync(() -> {
            dummyService.save(dummyCreateDto);
            return null;
        });
        future.thenRun(() ->{
            logger.info("Dummy created");
        }).exceptionally(ex -> {
            throw new RuntimeException(ex.getMessage());
        });
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable UUID uuid, @RequestBody DummyUpdateDto dummyUpdateDto) {
        CompletableFuture<?> future = asyncTaskHandler.executeAsync(() -> dummyService.update(uuid, dummyUpdateDto));
        DummyGetDto response = (DummyGetDto) future.handle((res, ex) ->{
            if(ex != null) {
                throw new RuntimeException(ex.getMessage());
            }else{
                logger.info("Dummy updated");
                return res;
            }
        }).join();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        dummyService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
