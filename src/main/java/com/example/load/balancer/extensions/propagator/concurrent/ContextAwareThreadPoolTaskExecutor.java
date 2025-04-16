/*
 * Copyright (c) 2017 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.load.balancer.extensions.propagator.concurrent;

import com.example.load.balancer.extensions.context.ExecutionContext;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Copies current {@link ExecutionContext} to delegate composite executor of {@link AsyncListenableTaskExecutor} and {@link SchedulingTaskExecutor}.
 * <p>{@link org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor} use case.
 *
 * @author Nadim Benabdenbi
 */
public class ContextAwareThreadPoolTaskExecutor implements AsyncListenableTaskExecutor, SchedulingTaskExecutor {
    /**
     * The delegate async listenable task executor propagator.
     */
    private final ContextAwareAsyncListenableTaskExecutor asyncListenableTaskExecutorPropagator;
    /**
     * The delegate scheduling task executor propagator.
     */
    private final ContextAwareSchedulingTaskExecutor schedulingTaskExecutorPropagator;

    /**
     * Constructor.
     *
     * @param asyncListenableTaskExecutor The delegate async listenable task executor.
     * @param schedulingTaskExecutor      The delegate scheduling task executor.
     */
    public ContextAwareThreadPoolTaskExecutor(@NotNull AsyncListenableTaskExecutor asyncListenableTaskExecutor,
                                              @NotNull SchedulingTaskExecutor schedulingTaskExecutor) {
        asyncListenableTaskExecutorPropagator = new ContextAwareAsyncListenableTaskExecutor(asyncListenableTaskExecutor);
        schedulingTaskExecutorPropagator = new ContextAwareSchedulingTaskExecutor(schedulingTaskExecutor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Runnable task, long startTimeout) {
        schedulingTaskExecutorPropagator.execute(task, startTimeout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<?> submit(Runnable task) {
        return schedulingTaskExecutorPropagator.submit(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return schedulingTaskExecutorPropagator.submit(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean prefersShortLivedTasks() {
        return schedulingTaskExecutorPropagator.prefersShortLivedTasks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Runnable command) {
        asyncListenableTaskExecutorPropagator.execute(command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return asyncListenableTaskExecutorPropagator.submitListenable(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return asyncListenableTaskExecutorPropagator.submitListenable(task);
    }
}
