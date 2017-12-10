package com.piotrwalkusz.lebrb.lanlearn.filereaders

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.streams.toList

internal class PlainTextFileDecoderTest {

    private val plainTextReader = PlainTextFileDecoder()

    @Test
    fun read() {
        val inputStream = javaClass.getResourceAsStream("/text.txt")
        val readersList = plainTextReader.read(inputStream).toStream().toList()

        assertEquals(1, readersList.size)
        assertEquals("aaa bbb ccc ddd AAA BBB CCC a-a b-b c-c . ąćężź a;a", readersList[0].readText().trimEnd())
    }
}