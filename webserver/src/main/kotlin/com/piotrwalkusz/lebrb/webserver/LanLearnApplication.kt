package com.piotrwalkusz.lebrb.webserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LanLearnApplication

fun main(args: Array<String>) {
    SpringApplication.run(LanLearnApplication::class.java, *args)
}
