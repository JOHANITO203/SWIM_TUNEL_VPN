package com.branded.vpn.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.branded.vpn.core.domain.repository.VpnRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class SubscriptionWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val vpnRepository: VpnRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val result = vpnRepository.refreshSubscription()
            if (result.isSuccess) Result.success() else Result.retry()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        private const val WORK_NAME = "SubscriptionSyncWorker"

        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<SubscriptionWorker>(12, TimeUnit.HOURS)
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()
            
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
