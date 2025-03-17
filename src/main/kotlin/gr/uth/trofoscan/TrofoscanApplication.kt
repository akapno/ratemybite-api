package gr.uth.trofoscan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TrofoscanApplication

fun main(args: Array<String>) {
    runApplication<TrofoscanApplication>(*args)
}
