package gr.uth.ratemybite.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class ImageService(@Value("\${file.upload-dir}") val uploadDir: String) {

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

        return filePath.toAbsolutePath().toString()
    }

    @Throws(MalformedURLException::class)
    fun loadAsResource(relativePath: String): Resource {
        val file = Paths.get(uploadDir).resolve(relativePath).normalize()
        val resource = UrlResource(file.toUri())
        if (!resource.exists() || !resource.isReadable) {
            throw NoSuchElementException("Could not read file: $relativePath")
        }
        return resource
    }

    fun randomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}