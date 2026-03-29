package com.example.myawesomeapp.batch.job;

import com.example.myawesomeapp.dataaccess.entity.User;
import com.example.myawesomeapp.dataaccess.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

/**
 * Batch job that reads all users and normalises their names to uppercase,
 * simulating a data-sync / cleansing operation.
 */
@Configuration
public class UserSyncJobConfig {

    private static final Logger log = LoggerFactory.getLogger(UserSyncJobConfig.class);

    @Bean
    public Job userSyncJob(JobRepository jobRepository, Step userSyncStep) {
        return new JobBuilder("userSyncJob", jobRepository)
                .start(userSyncStep)
                .build();
    }

    @Bean
    public Step userSyncStep(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                             RepositoryItemReader<User> userItemReader,
                             ItemProcessor<User, User> userItemProcessor,
                             ItemWriter<User> userItemWriter) {
        return new StepBuilder("userSyncStep", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(userItemReader)
                .processor(userItemProcessor)
                .writer(userItemWriter)
                .build();
    }

    @Bean
    public RepositoryItemReader<User> userItemReader(UserRepository userRepository) {
        return new RepositoryItemReaderBuilder<User>()
                .name("userItemReader")
                .repository(userRepository)
                .methodName("findAll")
                .pageSize(10)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<User, User> userItemProcessor() {
        return user -> {
            log.info("Syncing user: {}", user);
            user.setName(user.getName().toUpperCase());
            return user;
        };
    }

    @Bean
    public ItemWriter<User> userItemWriter(UserRepository userRepository) {
        return chunk -> {
            log.info("Writing {} synced users", chunk.getItems().size());
            userRepository.saveAll(chunk.getItems());
        };
    }
}
