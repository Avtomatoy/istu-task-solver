package ru.avtomaton.istutasksolver.task.presentation

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ServerWebExchange
import java.net.URI

@Controller
@RequestMapping("/")
class GlobalController {

    @GetMapping
    suspend fun redirect(exchange: ServerWebExchange) {
        exchange.response.setStatusCode(HttpStatus.FOUND)
        exchange.response.headers.location = URI("/task-ui")
        exchange.response.setComplete().awaitFirstOrNull()
    }
}