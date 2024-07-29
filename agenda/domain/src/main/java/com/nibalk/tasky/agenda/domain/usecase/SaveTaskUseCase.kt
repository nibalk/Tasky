package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.TaskRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

class SaveTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(
        task: AgendaItem.Task,
        isCreateNew: Boolean
    ): EmptyResult<DataError> {
        return if (isCreateNew) {
            taskRepository.createTask(task)
        } else {
            taskRepository.updateTask(task)
        }
    }
}
