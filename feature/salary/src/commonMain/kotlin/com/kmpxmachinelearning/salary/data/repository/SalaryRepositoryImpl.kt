package com.kmpxmachinelearning.salary.data.repository

import com.kmpxmachinelearning.salary.data.datasource.SalaryDataSource
import com.kmpxmachinelearning.salary.data.mapper.toEntity
import com.kmpxmachinelearning.salary.domain.entity.SalaryEntity
import com.kmpxmachinelearning.salary.domain.entity.SalaryParamsEntity
import com.kmpxmachinelearning.salary.domain.repository.SalaryRepository
import com.kmpxmachinelearning.shared.core.error.NetworkException
import com.kmpxmachinelearning.shared.core.error.NetworkFailure
import com.kmpxmachinelearning.shared.core.error.NotFoundException
import com.kmpxmachinelearning.shared.core.error.NotFoundFailure
import com.kmpxmachinelearning.shared.core.error.ServerException
import com.kmpxmachinelearning.shared.core.error.ServerFailure
import com.kmpxmachinelearning.shared.core.error.TimeoutException
import com.kmpxmachinelearning.shared.core.error.TimeoutFailure
import com.kmpxmachinelearning.shared.core.error.UnauthorizedException
import com.kmpxmachinelearning.shared.core.error.UnauthorizedFailure
import com.kmpxmachinelearning.shared.core.error.UnknownException
import com.kmpxmachinelearning.shared.core.error.UnknownFailure
import com.kmpxmachinelearning.shared.core.network.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SalaryRepositoryImpl(
    private val salaryDataSource: SalaryDataSource
): SalaryRepository {

    override fun predictSalary(paramsSalaryEntity: SalaryParamsEntity): Flow<RequestState<SalaryEntity?>> =
        flow {
            emit(RequestState.Loading)

            try {
                val response = salaryDataSource.predictSalary(
                    paramsSalaryEntity.positionLevel
                )

                emit(RequestState.Success(response?.toEntity()))

            } catch (e: UnauthorizedException) {
                emit(RequestState.ErrorV2(UnauthorizedFailure(e.message ?: "")))

            } catch (e: NotFoundException) {
                emit(RequestState.ErrorV2(NotFoundFailure(e.message ?: "")))

            } catch (e: ServerException) {
                emit(RequestState.ErrorV2(ServerFailure(e.message ?: "")))

            } catch (e: NetworkException) {
                emit(RequestState.ErrorV2(NetworkFailure()))

            } catch (e: TimeoutException) {
                emit(RequestState.ErrorV2(TimeoutFailure()))

            } catch (e: UnknownException) {
                emit(RequestState.ErrorV2(UnknownFailure(e.message ?: "")))

            } catch (e: Exception) {
                emit(RequestState.ErrorV2(ServerFailure("Terjadi kesalahan")))
            }
        }
}