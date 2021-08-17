package io.commerce.ecommerceapi.app.controller

import io.commerce.ecommerceapi.app.service.FileService
import io.commerce.ecommerceapi.core.io.BasicController
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.nio.file.Path
import java.util.stream.Stream


@RestController
@RequestMapping("/api/private/files")
class FileController(
    private val fileService: FileService
) : BasicController<FileService>(fileService) {


    @GetMapping
    fun listUploadedFiles(): Stream<Path> {
        val fileList = fileService.loadAll()
        return fileList
    }

    @GetMapping("/files/{filename:.+}")
    fun serveFile(@PathVariable filename: String): ResponseEntity<Resource?>? {
        val file: Resource = fileService.loadAsResource(filename)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename().toString() + "\"")
            .body<Resource?>(file)
    }

    @PostMapping
    fun handleFileUpload(@RequestParam("file") file: MultipartFile, redirectAttributes: RedirectAttributes): String? {
        fileService.store(file)
        return "success"
    }

    @DeleteMapping("{filename:.+}")
    fun handleDelete(@PathVariable filename: String){
        fileService.delete(filename)
    }


}