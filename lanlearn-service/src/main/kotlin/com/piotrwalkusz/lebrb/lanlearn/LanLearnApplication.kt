package com.piotrwalkusz.lebrb.lanlearn

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WordscounterApplication

fun main(args: Array<String>) {
    SpringApplication.run(WordscounterApplication::class.java, *args)
}
