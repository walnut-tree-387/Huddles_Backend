package com.example.Threading.HuddleBuddies;

import java.util.List;
import java.util.UUID;

public interface HuddleBuddyService {
    void createNewBuddyRequest(UUID newBuddyUuid);
    void processBuddyRequest(UUID requestUuid, String action);
    HuddleBuddy getRequestById(UUID requestUuid);
    List<HuddleBuddy> getAllPendingBuddyRequests();
    List<HuddleBuddy> getAllBuddies();
}
