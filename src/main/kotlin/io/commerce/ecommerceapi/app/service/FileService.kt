package io.commerce.ecommerceapi.app.service

import io.commerce.ecommerceapi.app.models.ResourceRepository
import io.commerce.ecommerceapi.core.engine.exception.StorageException
import io.commerce.ecommerceapi.core.engine.exception.StorageFileNotFoundException
import io.commerce.ecommerceapi.utils.random
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream


@Service
class FileServiceImpl(
    storageProperties: StorageProperties,
    val resourceRepository: ResourceRepository
): FileService {
    private var rootLocation: Path? = null
    init {
        rootLocation = Paths.get(storageProperties.location)
    }

    override fun init() {
        try {
            Files.createDirectories(rootLocation!!)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    override fun store(file: MultipartFile) {
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }

            val resource = resolveAndSave(file)
            var filename = resource.stabilizedName

            val destinationFile = rootLocation!!.resolve(Paths.get(filename)).normalize().toAbsolutePath()
            if (destinationFile.parent != rootLocation!!.toAbsolutePath()) {
                throw StorageException("Cannot store file outside current directory.")
            }
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
            }
            resourceRepository.save(resource)
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
    }

    override fun loadAll(): Stream<Path> {
        return try {
            Files.walk(rootLocation, 1)
                .filter { path: Path -> path != rootLocation }
                .map { other: Path? ->
                    rootLocation!!.relativize(
                        other
                    )
                }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String): Path {
        return rootLocation!!.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        return try {
            val file = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation!!.toFile());
    }

    override fun delete(filename: String) {
        resourceRepository.getByStabilizedName(filename)?.let {
            Files.delete(Paths.get(it.fullpath))
            resourceRepository.delete(it)
        }
    }

    private fun resolveAndSave(file: MultipartFile): io.commerce.ecommerceapi.app.models.Resource {
        var stabilizedName: String = file.originalFilename!!
        resourceRepository.getByStabilizedName(stabilizedName)?.let { resource ->
            stabilizedName = resource.stabilizedName.random()
        }
        return io.commerce.ecommerceapi.app.models.Resource(
            file.contentType!!,
            file.originalFilename!!,
            stabilizedName,
            "${rootLocation.toString()}/${stabilizedName}"
        )
    }
}



interface FileService{

    fun init()
    fun store(file: MultipartFile)
    fun loadAll(): Stream<Path>
    fun load(filename: String): Path
    fun loadAsResource(filename: String): Resource
    fun deleteAll();
    fun delete(filename: String)
}


@ConfigurationProperties("storage")
class StorageProperties {
    var location = "upload-dir"
}