package com.piotrwalkusz.lebrb.webserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WebserverApplication

fun main(args: Array<String>) {
    SpringApplication.run(WebserverApplication::class.java, *args)
}
