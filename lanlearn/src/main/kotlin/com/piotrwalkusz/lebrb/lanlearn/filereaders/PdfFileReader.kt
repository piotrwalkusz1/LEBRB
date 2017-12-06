package com.piotrwalkusz.lebrb.lanlearn.filereaders

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import reactor.core.publisher.Flux
import java.io.InputStream
import java.io.Reader

class PdfFileReader(private val pagesPerChunk: Int = 100) : FileReader {

    override fun read(source: InputStream): Flux<Reader> {
        val doc = PDDocument.load(source)
        val pdfTextStripper = PDFTextStripper()

        return Flux.generate({ 0 }, { page, sink ->
            pdfTextStripper.startPage = page
            pdfTextStripper.endPage = page + pagesPerChunk - 1
            sink.next(pdfTextStripper.getText(doc).reader())
            if (page + pagesPerChunk >= doc.numberOfPages)
                sink.complete()
            page + pagesPerChunk
        })
    }
}