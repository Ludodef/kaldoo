package com.ludovica.kaldoo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class SyncScheduler {

    private final SyncService syncService;

    public SyncScheduler(SyncService syncService) {
        this.syncService = syncService;
    }

    @Scheduled(fixedRate = 30000) // ogni ora cron = "0 0 * * * *"
    public void scheduledSync() {
        syncService.sincronizzaProdotti();
    }
}
