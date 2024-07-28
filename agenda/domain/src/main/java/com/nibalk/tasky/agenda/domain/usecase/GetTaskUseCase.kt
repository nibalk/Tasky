package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.TaskRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem

class GetTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String): AgendaItem.Task? {
        return taskRepository.getTask(taskId)
    }
}
