package com.anushka.coroutinesdemo1

import kotlinx.coroutines.*

val scrollIntervalInSeconds = 4L

val customCardScroll = 7L

fun main() {

    println("starting...")
    runBlocking {
        println("runBlocking...")
        withContext(Dispatchers.Default) {
            launch {
                println("Delaying in isInfoCard ")
                while (true) {
                    async {
                        delay(scrollIntervalInSeconds * 1000)
                        withContext(Dispatchers.Main) {
                            println("MainThreadDispatched for infoCardRunnable")
                        }
                    }.await()
                }
            }
            launch {
                println("Delaying in isCustomCard ")
                while (true) {
                    async {
                        delay(customCardScroll * 1000)
                        withContext(Dispatchers.Main) {
                            println("MainThreadDispatched for customCardRunnable")
                        }
                    }.await()
                }
            }
            println("...ending")
        }
    }
}