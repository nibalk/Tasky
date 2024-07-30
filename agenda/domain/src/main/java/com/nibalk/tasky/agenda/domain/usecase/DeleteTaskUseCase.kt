package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String) {
        return taskRepository.deleteTask(taskId)
    }
}
