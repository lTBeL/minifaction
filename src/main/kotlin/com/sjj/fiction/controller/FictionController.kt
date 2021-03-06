package com.sjj.fiction.controller

import com.sjj.fiction.model.Book
import com.sjj.fiction.model.Chapter
import com.sjj.fiction.model.GBook
import com.sjj.fiction.model.Result
import com.sjj.fiction.service.FictionService
import com.sjj.fiction.util.toResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class FictionController {
    @Autowired
    lateinit var fictionService: FictionService

    @RequestMapping(value = "/", method = [RequestMethod.GET])
    fun default() = "hello everyone"

    @RequestMapping(value = "/search", method = [RequestMethod.GET])
    fun search(searchKey: String): Mono<Result<List<GBook>>> {
        return fictionService.search(searchKey).map { it.toResult() }
    }

    @RequestMapping(value = "/intro", method = [RequestMethod.GET])
    fun intro(url: String): Mono<Result<Book>> {
        return fictionService.intro(url).map { it.toResult() }
    }

    @RequestMapping(value = "/chapter", method = [RequestMethod.GET])
    fun chapter(url: String): Mono<Result<Chapter>> {
        return fictionService.chapter(url).map { it.toResult() }
    }

}