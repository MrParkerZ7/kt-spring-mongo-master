package com.park.spring.mongo.grid.fs.kt.mongogridfskt.controller

import com.mongodb.client.gridfs.model.GridFSUploadOptions
import com.mongodb.gridfs.GridFSDBFile
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsCriteria
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.streams.toList

@RestController
@RequestMapping("/files")
class FilesController(@Autowired var gridFsTemplate: GridFsTemplate) {

    @PostMapping
    fun upLoad(@RequestParam("file") file: MultipartFile): HttpEntity<ByteArray>? {

        val gridFSUploadOptions = GridFSUploadOptions().metadata(Document("Owner", "Park")
                .append("content_type", file.contentType))

        try {
            gridFsTemplate.store(file.inputStream, file.originalFilename, file.contentType, gridFSUploadOptions)

            val resp = "<script>window.location = '/';</script>"
            return HttpEntity(resp.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping
    fun getList(): List<String> = gridFsTemplate.find(Query.query(GridFsCriteria.where(null))).stream()
            .map(GridFSDBFile::getFilename).toList()

    @GetMapping("/{name:.+}")
    fun getFile(@PathVariable("name") name: String): HttpEntity<ByteArray>? {

        try {
            val optionalFile: Optional<GridFSDBFile> = Optional
                    .ofNullable(gridFsTemplate.findOne(Query.query(GridFsCriteria.whereFilename().`is`(name))))

            if (optionalFile.isPresent) {
                var file = optionalFile.get()
                var byteArrayOutputStream = ByteArrayOutputStream()
                file.writeTo(byteArrayOutputStream)

                var headers = HttpHeaders()
                //headers.add(HttpHeaders().contentType.toString(), file.contentType) This method show return null in first parameter
                headers.add("Content-Type", file.contentType)

                return HttpEntity(byteArrayOutputStream.toByteArray(), headers)
            } else
                return ResponseEntity(HttpStatus.NOT_FOUND)

        } catch (e: IOException) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.IM_USED)
        }
    }

    @DeleteMapping("/delete/{name:.+}")
    fun deleteFile(@PathVariable("name") name: String): String = gridFsTemplate
            .delete(Query.query(GridFsCriteria.whereFilename().`is`(name)))
            .let { "Delete FileName: $name Successful" }

}