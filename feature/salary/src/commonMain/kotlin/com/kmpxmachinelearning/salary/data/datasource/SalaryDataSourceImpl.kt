package com.kmpxmachinelearning.salary.data.datasource

import com.kmpxmachinelearning.salary.data.model.SalaryModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SalaryDataSourceImpl(
    private val client: HttpClient
): SalaryDataSource {
    override suspend fun predictSalary(positionLevel: Double): SalaryModel {
        try {
            println("masuk sini positionLevel: $positionLevel")
            val response: SalaryModel = client.post("http://10.0.2.2:5000/predict") {
                contentType(ContentType.Application.Json)
                setBody("""{"position_level": $positionLevel}""")
            }.body()

            println("masuk sini 5: $response")

            return response
        } catch (e: Exception) {
            println("masuk sini 6: $e")
            throw e
        }
    }
}