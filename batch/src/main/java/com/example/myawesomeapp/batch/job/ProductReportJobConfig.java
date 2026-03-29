package com.example.myawesomeapp.batch.job;

import com.example.myawesomeapp.dataaccess.entity.Product;
import com.example.myawesomeapp.dataaccess.repository.ProductRepository;
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
 * Batch job that reads all products, formats a summary line for each one,
 * and writes the report lines to the application log.
 */
@Configuration
public class ProductReportJobConfig {

    private static final Logger log = LoggerFactory.getLogger(ProductReportJobConfig.class);

    @Bean
    public Job productReportJob(JobRepository jobRepository, Step productReportStep) {
        return new JobBuilder("productReportJob", jobRepository)
                .start(productReportStep)
                .build();
    }

    @Bean
    public Step productReportStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  RepositoryItemReader<Product> productItemReader,
                                  ItemProcessor<Product, String> productReportProcessor,
                                  ItemWriter<String> productReportWriter) {
        return new StepBuilder("productReportStep", jobRepository)
                .<Product, String>chunk(10, transactionManager)
                .reader(productItemReader)
                .processor(productReportProcessor)
                .writer(productReportWriter)
                .build();
    }

    @Bean
    public RepositoryItemReader<Product> productItemReader(ProductRepository productRepository) {
        return new RepositoryItemReaderBuilder<Product>()
                .name("productItemReader")
                .repository(productRepository)
                .methodName("findAll")
                .pageSize(10)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Product, String> productReportProcessor() {
        return product -> String.format(
                "[REPORT] Product: %-25s | Price: $%8.2f | Stock: %4d | %s",
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getStock() < 50 ? "LOW STOCK" : "IN STOCK"
        );
    }

    @Bean
    public ItemWriter<String> productReportWriter() {
        return chunk -> chunk.getItems().forEach(line -> log.info("{}", line));
    }
}
