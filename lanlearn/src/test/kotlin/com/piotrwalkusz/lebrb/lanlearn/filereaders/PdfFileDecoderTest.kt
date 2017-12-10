package com.piotrwalkusz.lebrb.lanlearn.filereaders

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.streams.toList

internal class PdfFileDecoderTest {

    private val pdfReader = PdfFileDecoder(1)

    @Test
    fun `read one page`() {
        val inputStream = javaClass.getResourceAsStream("/one_page_text.pdf")
        val readers = pdfReader.read(inputStream).toStream().toList()
        inputStream.close()

        assertEquals(1, readers.size)
        assertEquals("aaa bbb ccc ddd AAA BBB CCC a-a b-b c-c . ąćężź a;a", readers[0].readText().trimEnd())
    }

    @Test
    fun `read multiple page`() {
        val inputStream = javaClass.getResourceAsStream("/two_pages_text.pdf")
        val readers = pdfReader.read(inputStream).toStream().toList()
        inputStream.close()

        assertEquals(2, readers.size)
        assertEquals(101, readers.map { it.readLines().filter { it.isNotBlank() }.size }.sum())
    }
}