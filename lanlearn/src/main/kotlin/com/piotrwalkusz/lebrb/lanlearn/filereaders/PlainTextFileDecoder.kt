package com.piotrwalkusz.lebrb.lanlearn.filereaders

import reactor.core.publisher.Flux
import java.io.InputStream
import java.io.Reader

class PlainTextFileDecoder : FileDecoder {

    override fun read(source: InputStream): Flux<Reader> {
        return Flux.just(source.bufferedReader())
    }
}