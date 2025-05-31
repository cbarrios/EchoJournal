package com.plcoding.echojournal.echos.data.echo

import com.plcoding.echojournal.core.database.echo.EchoDao
import com.plcoding.echojournal.echos.domain.echo.Echo
import com.plcoding.echojournal.echos.domain.echo.EchoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class RoomEchoDataSource(
    private val echoDao: EchoDao
) : EchoDataSource {

    override fun observeEchos(): Flow<List<Echo>> {
        return echoDao
            .observeEchos()
            .map { echoWithTopics ->
                echoWithTopics.map { it.toEcho() }
            }
    }

    override fun observeTopics(): Flow<List<String>> {
        return echoDao
            .observeTopics()
            .map { topicEntities ->
                topicEntities.map { it.topic }
            }
    }

    override fun searchTopics(query: String): Flow<List<String>> {
        return echoDao
            .searchTopics(query)
            .map { topicEntities ->
                topicEntities.map { it.topic }
            }
    }

    override suspend fun insertEcho(echo: Echo): Boolean {
        return try {
            echoDao.insertEchoWithTopics(echo.toEchoWithTopics())
            true
        } catch (e: Exception) {
            Timber.e(e, "Inserting echo failed")
            false
        }
    }
}