package com.example.Threading.HuddleBuddies;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/huddles/buddies")
public class HuddleBuddyController {

    private final HuddleBuddyServiceImpl huddleBuddyService;

    public HuddleBuddyController(HuddleBuddyServiceImpl huddleBuddyService) {
        this.huddleBuddyService = huddleBuddyService;
    }

    @PostMapping("/create-request/{buddyUuid}")
    public ResponseEntity<?> createRequest(@PathVariable("buddyUuid")UUID buddyUuid){
        huddleBuddyService.createNewBuddyRequest(buddyUuid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/process-request/{requestUuid}")
    public ResponseEntity<?> processRequest(@PathVariable("requestUuid")UUID requestUuid,
                                            @RequestParam("status") String action){
        huddleBuddyService.processBuddyRequest(requestUuid, action);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/requests")
    public ResponseEntity<?> getPendingRequestList(){
        return new ResponseEntity<>(huddleBuddyService.getAllPendingBuddyRequests(), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getBuddyList(){
        return new ResponseEntity<>(huddleBuddyService.getAllBuddies(), HttpStatus.OK);
    }
}
