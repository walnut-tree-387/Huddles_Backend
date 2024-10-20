package com.example.Threading.HuddleBuddies;

import com.example.Threading.Users.AppUserService;
import com.example.Threading.exception.types.HuddleNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HuddleBuddyServiceImpl implements HuddleBuddyService {
    private final AppUserService appUserService;
    private final HuddleBuddyRepository huddleBuddyRepository;

    public HuddleBuddyServiceImpl(AppUserService appUserService, HuddleBuddyRepository huddleBuddyRepository) {
        this.appUserService = appUserService;
        this.huddleBuddyRepository = huddleBuddyRepository;

    }

    @Override
    public void createNewBuddyRequest(UUID newBuddyUuid) {
        HuddleBuddy newBuddies = new HuddleBuddy();
        newBuddies.setRequestedBy(appUserService.getCurrentUser());
        newBuddies.setAcceptedBy(appUserService.getUserByUuid(newBuddyUuid));
        newBuddies.setCreatedAt(Instant.now().getEpochSecond());
        newBuddies.setStatus(HuddleRequestStatus.REQUESTED);
        huddleBuddyRepository.save(newBuddies);
    }

    @Override
    public void processBuddyRequest(UUID requestUuid, String action) {
        HuddleBuddy huddleBuddy = getRequestById(requestUuid);
        if(action.equals("ACCEPT")){
            huddleBuddy.setStatus(HuddleRequestStatus.FRIEND);
            huddleBuddyRepository.save(huddleBuddy);
        }
        else if(action.equals("DECLINE")){
            huddleBuddyRepository.delete(huddleBuddy);
        }
    }

    @Override
    public HuddleBuddy getRequestById(UUID requestUuid) {
        Optional<HuddleBuddy> optional = huddleBuddyRepository.findById(requestUuid);
        if (optional.isEmpty()) {
            throw new HuddleNotFoundException(HuddleBuddyServiceImpl.class, "No huddle request found with request uuid: " + requestUuid);
        }
        return optional.get();
    }

    @Override
    public List<HuddleBuddy> getAllPendingBuddyRequests() {
        return huddleBuddyRepository.getAllRequestedEntries(appUserService.getCurrentUser().getUuid(), HuddleRequestStatus.REQUESTED);
    }

    @Override
    public List<HuddleBuddy> getAllBuddies() {
        return huddleBuddyRepository.getAllFriends(appUserService.getCurrentUser().getUuid(), HuddleRequestStatus.FRIEND);
    }
}
