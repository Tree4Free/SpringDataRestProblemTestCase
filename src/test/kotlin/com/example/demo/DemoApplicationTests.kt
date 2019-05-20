package com.example.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import javax.persistence.EntityManager

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    lateinit var myEntityRepository: MyEntityRepository

    @Autowired
    lateinit var immutableEntityRepository: ImmutableEntityRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testPut() {
        val myEntity = myEntityRepository.save(MyEntityKt(name = "TestName", description = "TestDescription"))
        val update = this.mockMvc.perform(
            put("/kotlin/${myEntity.id}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    """
                    {
                        "name": "TestNameModified",
                        "description": "TestDescriptionModified"
                    }
                """.trimIndent()
                )
        ).andReturn().response.contentAsByteArray.let { objectMapper.readValue(it, MyEntityKt::class.java) }

        entityManager.clear()

        val updatedEntity = myEntityRepository.findById(myEntity.id!!).get()

        assertThat(updatedEntity.name).isEqualTo("TestNameModified")
        assertThat(updatedEntity.description).isEqualTo("TestDescriptionModified")
    }

    @Test
    fun testPatch() {
        val myEntity = myEntityRepository.save(MyEntityKt(name = "TestName", description = "TestDescription"))
        val update = this.mockMvc.perform(
            patch("/kotlin/${myEntity.id}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    """
                    {
                        "name": "TestNameModified",
                        "description": "TestDescriptionModified"
                    }
                """.trimIndent()
                )
        ).andReturn().response.contentAsByteArray.let { objectMapper.readValue(it, MyEntityKt::class.java) }

        val updatedEntity = myEntityRepository.findById(myEntity.id!!).get()

        assertThat(updatedEntity.name).isEqualTo("TestNameModified")
        assertThat(updatedEntity.description).isEqualTo("TestDescriptionModified")
    }

    @Test
    fun testPutJava() {
        val myEntity = immutableEntityRepository.save(MyEntityJava(null, 0, "TestName", "TestDescription"))
        val update = this.mockMvc.perform(
             put("/java/${myEntity.id}")
                  .accept(MediaType.APPLICATION_JSON_UTF8)
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content(
                       """
                    {
                        "name": "TestNameModified",
                        "description": "TestDescriptionModified"
                    }
                """.trimIndent()
                  )
        ).andReturn().response.contentAsByteArray.let { objectMapper.readValue(it, MyEntityJava::class.java) }

        val updatedEntity = immutableEntityRepository.findById(myEntity.id!!).get()

        assertThat(updatedEntity.name).isEqualTo("TestNameModified")
        assertThat(updatedEntity.description).isEqualTo("TestDescriptionModified")
    }

    @Test
    fun testPatchJava() {
        val myEntity = immutableEntityRepository.save(MyEntityJava(null, 0, "TestName","TestDescription"))
        val update = this.mockMvc.perform(
             patch("/java/${myEntity.id}")
                  .accept(MediaType.APPLICATION_JSON_UTF8)
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content(
                       """
                    {
                        "name": "TestNameModified",
                        "description": "TestDescriptionModified"
                    }
                """.trimIndent()
                  )
        ).andReturn().response.contentAsByteArray.let { objectMapper.readValue(it, MyEntityJava::class.java) }

        val updatedEntity = immutableEntityRepository.findById(myEntity.id!!).get()

        assertThat(updatedEntity.name).isEqualTo("TestNameModified")
        assertThat(updatedEntity.description).isEqualTo("TestDescriptionModified")
    }

}

