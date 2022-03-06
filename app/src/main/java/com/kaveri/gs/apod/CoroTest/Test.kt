package com.kaveri.gs.apod.CoroTest

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    // Execute api1() and api2() concurrently and calculate the result

    val time = measureTimeMillis {
        var api1 = async {
            val a = api1()
            a
        }
        var api2=  async {
            val b = api2()
            b
        }
        val result = api1.await() + api2.await()

        println("Response: $result")
    }
    println("Finished in ${time}ms")
}

suspend fun api1(): String {
    delay(2000)
    return "Hello"
}

suspend fun api2(): Int {
    delay(3000)
    return 1
}