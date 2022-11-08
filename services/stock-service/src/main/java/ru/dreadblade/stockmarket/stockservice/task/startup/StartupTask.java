package ru.dreadblade.stockmarket.stockservice.task.startup;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import ru.dreadblade.stockmarket.stockservice.task.Task;

public interface StartupTask extends ApplicationListener<ApplicationReadyEvent>, Task {
}
