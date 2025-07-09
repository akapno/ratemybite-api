package gr.uth.ratemybite.controllers

import gr.uth.ratemybite.services.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Paths


@RestController
@RequestMapping("/uploads")
class ImageUploadController @Autowired constructor(val imageService: ImageService) {

    @PostMapping("/image")
    fun uploadImage(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        try {
            val filePath = imageService.saveImage(file)
            return ResponseEntity.ok("Image uploaded succesfully: " + filePath)
        } catch (e: IOException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: ${e.message}")
        }
    }

    @GetMapping("/images/{foldername}/{filename}")
    fun getImage(
        @PathVariable foldername: String,
        @PathVariable filename: String
    ): ResponseEntity<Resource> {
        return try {
            val resource: Resource = imageService.loadAsResource("$foldername/$filename")

            val contentType = Files.probeContentType(
                Paths.get(imageService.uploadDir).resolve("$foldername/$filename")
            ) ?: MediaType.APPLICATION_OCTET_STREAM_VALUE

            ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: MalformedURLException) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}