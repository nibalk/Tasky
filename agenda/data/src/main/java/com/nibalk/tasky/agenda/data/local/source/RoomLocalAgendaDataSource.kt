package com.nibalk.tasky.agenda.data.local.source

import android.database.sqlite.SQLiteFullException
import com.nibalk.tasky.agenda.data.local.dao.AgendaDao
import com.nibalk.tasky.agenda.data.local.database.AgendaDatabase
import com.nibalk.tasky.agenda.domain.model.AgendaItems
import com.nibalk.tasky.agenda.domain.source.local.LocalAgendaDataSource
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result

class RoomLocalAgendaDataSource(
    private val agendaDao: AgendaDao,
    private val agendaDatabase: AgendaDatabase
) : LocalAgendaDataSource {
    override suspend fun fetchAndStoreAgendas(
        agendas: AgendaItems
    ): EmptyResult<DataError.Local> {
        return try {
            agendaDao.updateAgendas(agendaDatabase, agendas)
            Result.Success(Unit)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}
