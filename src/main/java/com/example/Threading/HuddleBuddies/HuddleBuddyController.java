package com.example.Threading.HuddleBuddies;

import com.example.Threading.HuddleBuddies.Dto.HuddleBuddyGetDto;
import com.example.Threading.Users.AppUserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/huddles/buddies")
public class HuddleBuddyController {

    private final HuddleBuddyServiceImpl huddleBuddyService;
    private final AppUserService appUserService;

    public HuddleBuddyController(HuddleBuddyServiceImpl huddleBuddyService, AppUserService appUserService) {
        this.huddleBuddyService = huddleBuddyService;
        this.appUserService = appUserService;
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
        List<HuddleBuddy> buddies = huddleBuddyService.getAllBuddies();
        List<HuddleBuddyGetDto> response = new ArrayList<>();
        buddies.forEach(buddy ->{
            if(buddy.getRequestedBy().getUuid().equals(appUserService.getCurrentUser().getUuid())){
                response.add(new HuddleBuddyGetDto(buddy.getRequestedBy().getUuid(),
                        buddy.getRequestedBy().getName()));
            }
            else response.add(new HuddleBuddyGetDto(buddy.getAcceptedBy().getUuid(),
                    buddy.getAcceptedBy().getName()));
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
