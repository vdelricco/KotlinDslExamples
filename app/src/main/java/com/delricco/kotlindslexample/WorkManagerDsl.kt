package com.delricco.kotlindslexample

import android.content.Context
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

typealias WorkerClass = Class<out ListenableWorker>
@DslMarker annotation class WorkManagerDsl

fun enqueueWork(block: WorkManagerEnqueuer.() -> Unit) = WorkManagerEnqueuer().apply(block).enqueueList()

@WorkManagerDsl class WorkManagerEnqueuer{
    //private val workManager = WorkManager.getInstance()
    private val workRequestList = mutableListOf<WorkRequest>()

    fun once(block: SingleTimeWorkRequestBuilder.() -> Unit) =
        workRequestList.add(SingleTimeWorkRequestBuilder().apply(block).build())
    fun periodic(block: PeriodicWorkRequestBuilder.() -> Unit) =
        workRequestList.add(PeriodicWorkRequestBuilder().apply(block).build())

    fun enqueueList() = workRequestList
    //fun enqueue() = workManager.enqueue(workRequestList)
}

@WorkManagerDsl class SingleTimeWorkRequestBuilder : WorkerRequestBuilder() {
    override lateinit var worker: WorkerClass

    fun build() = OneTimeWorkRequest.Builder(worker).setInputData(dataBuilder.build()).build()
}

@WorkManagerDsl class PeriodicWorkRequestBuilder : WorkerRequestBuilder() {
    override lateinit var worker: WorkerClass
    private var interval: Interval = IntervalBuilder().build()

    fun interval(block: IntervalBuilder.() -> Unit) { interval = IntervalBuilder().apply(block).build() }

    fun build() = PeriodicWorkRequest.Builder(worker, interval.time, interval.unit).setInputData(dataBuilder.build()).build()

    data class Interval(val time: Long, val unit: TimeUnit)

    @WorkManagerDsl inner class IntervalBuilder {
        var time = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
        var unit = TimeUnit.MILLISECONDS

        fun build() = Interval(time, unit)
    }
}

@WorkManagerDsl abstract class WorkerRequestBuilder {
    abstract val worker: WorkerClass
    var dataBuilder = Data.Builder()

    fun inputData(block: DataBuilder.() -> Unit) { dataBuilder.putAll(DataBuilder().apply(block).build()) }
}

@WorkManagerDsl class DataBuilder {
    lateinit var key: String
    lateinit var value: Any

    fun build() = Data.Builder().put(key, value).build()
}

class TestWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork() = Result.SUCCESS
}
