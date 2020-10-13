package org.acme.dao.batch;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.quarkus.scheduler.runtime.SimpleScheduler;
import org.acme.dao.AnagramSqlDAO;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Singleton
public class TaskMaster {

    final Logger logger = Logger.getLogger(TaskMaster.class.getName());

    @Inject
    Scheduler scheduledTaskMaster;

    @Inject
    AnagramSqlDAO anagramSqlDAO;

    final String reallyLongString = "Onomatopoeia";

    @Scheduled(every = "P2D", delay = 5, delayUnit = TimeUnit.HOURS,
            identity = "StopTheWorldTask")
    public void pauseAllTasks() {
        scheduledTaskMaster.pause();
    }

    @Scheduled(every = "P2D", delay = 5, delayUnit = TimeUnit.HOURS, identity = "ResumeTheWorldTask")
    public void resumeAllTasks() {
        if (!scheduledTaskMaster.isRunning()) {
            scheduledTaskMaster.resume();
        }
    }
        @Scheduled(cron = "0 15 10 * * ?", identity = "EveryMorning GramGenerator")
        public void generateEveryMorning (ScheduledExecution execution){
            try {
                logger.info("Scheduled for: " + execution.
                        getScheduledFireTime());
                anagramSqlDAO.generateAndSaveAnagram(reallyLongString);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            logger.info("Next execution: " + execution.getTrigger().getNextFireTime());
        }

    }
