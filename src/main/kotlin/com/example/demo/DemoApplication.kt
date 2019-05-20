package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Version

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Entity
data class MyEntityKt(
     @Id
     @GeneratedValue
     val id: UUID? = null,
     @Version
     val version: Long = 0,
     val name: String = "",
     val description: String = ""
)

@RepositoryRestResource(path = "kotlin")
interface MyEntityRepository : JpaRepository<MyEntityKt, UUID>

@RepositoryRestResource(path = "java")
interface ImmutableEntityRepository : JpaRepository<MyEntityJava, UUID>