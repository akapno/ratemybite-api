package gr.uth.ratemybite.controllers

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


@RestController
@RequestMapping("/uploads")
class ImageUploadController(@Value("\${file.upload-dir}") private val uploadDir: String) {

    @PostMapping("/image")
    fun uploadImage(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        try {
            val filePath = saveImage(file)
            return ResponseEntity.ok("Image uploaded succesfully: " + filePath)
        } catch (e: IOException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image.")
        }
    }

    @GetMapping("/images/{foldername}/{filename}")
    fun getImage(
        @PathVariable foldername: String,
        @PathVariable filename: String
    ): ResponseEntity<Resource> {
        return try {
            val filePath = Paths.get(uploadDir)
                .resolve(foldername)
                .resolve(filename)
                .normalize()
            val resource: Resource = UrlResource(filePath.toUri())

            if (resource.exists()) {
                ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: MalformedURLException) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @Throws(IOException::class)
    fun saveImage(file: MultipartFile): String {
        val uploadPath = Paths.get(uploadDir)
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }

        var randomDirPath: Path
        do {
            val randomName = randomString(10)
            randomDirPath = uploadPath.resolve(randomName)
        } while (Files.exists(randomDirPath))
        Files.createDirectories(randomDirPath)

        val fileName = file.originalFilename ?: "unknown"
        val filePath = randomDirPath.resolve(fileName)
        Files.copy(file.inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)

        return filePath.toString()
    }

    fun randomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}