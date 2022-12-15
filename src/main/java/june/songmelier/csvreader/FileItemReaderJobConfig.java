package june.songmelier.csvreader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class FileItemReaderJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MellonWriter mellonWriter;
    private final TjWriter tjWriter;

    private static final int chunkSize = 1000;

    @Bean
    public Job mellonChartReaderJob() {
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .incrementer(new RunIdIncrementer())
                .start(mellonChartReaderStep())
                .build();
    }

    @Bean
    public Step mellonChartReaderStep() {
        FlatFileItemReader<SongDao> mellonChartReader = chartReader();
        mellonChartReader.setResource(new ClassPathResource("/csv/melon_data.csv"));

        return stepBuilderFactory.get("csvFileItemReaderStep")
                .<SongDao, SongDao>chunk(chunkSize)
                .reader(mellonChartReader)
                .writer(mellonWriter)
                .build();
    }

    @Bean
    public Job tjChartReaderJob() {
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .incrementer(new RunIdIncrementer())
                .start(tjChartReaderStep())
                .build();
    }

    @Bean
    public Step tjChartReaderStep() {
        FlatFileItemReader<SongDao> tjChartReader = chartReader();
        tjChartReader.setResource(new ClassPathResource("/csv/tj_data.csv"));

        return stepBuilderFactory.get("csvFileItemReaderStep")
                .<SongDao, SongDao>chunk(chunkSize)
                .reader(tjChartReader)
                .writer(tjWriter)
                .build();
    }

    public FlatFileItemReader<SongDao> chartReader() {
        /* file read */
        FlatFileItemReader<SongDao> flatFileItemReader = new FlatFileItemReader<>();
//        flatFileItemReader.setResource(new ClassPathResource("/csv/melon_data.csv"));
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding

        /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
        DefaultLineMapper<SongDao> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames("songId", "imageUrl", "publishedDate", "singer", "title");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<SongDao> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(SongDao.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }



    @Bean
    public FlatFileItemReader<SongDao> mellonChartReader() {
        /* file read */
        FlatFileItemReader<SongDao> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("/csv/melon_data.csv"));
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding

        /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
        DefaultLineMapper<SongDao> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames("songId", "imageUrl", "publishedDate", "singer", "title");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<SongDao> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(SongDao.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemReader<SongDao> tjChartReader() {
        /* file read */
        FlatFileItemReader<SongDao> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("/csv/tj_data.csv"));
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding

        /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
        DefaultLineMapper<SongDao> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames("songId", "imageUrl", "publishedDate", "singer", "title");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<SongDao> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(SongDao.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }


}