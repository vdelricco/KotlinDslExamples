package com.delricco.kotlindslexample

import androidx.work.PeriodicWorkRequest
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class WorkManagerDslTest {
    @Test
    fun `Test WorkerManager DSL`() {
        val workRequests = enqueueWork {
            once {
                worker = TestWorker::class.java

                inputData {
                    key = "URL"
                    value = "https://www.ibotta.com"
                }
            }

            once {
                worker = TestWorker::class.java
            }

            periodic {
                worker = TestWorker::class.java
                interval {
                    time = 3
                    unit = TimeUnit.DAYS
                }
            }
        }

        Assert.assertEquals(3, workRequests.size)
        Assert.assertEquals(TestWorker::class.java.name, workRequests[0].workSpec.workerClassName)
        Assert.assertEquals("https://www.ibotta.com", workRequests[0].workSpec.input.keyValueMap["URL"])
        Assert.assertEquals(PeriodicWorkRequest::class.java, workRequests[2].javaClass)
    }
}
