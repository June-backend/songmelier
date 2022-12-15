package june.songmelier.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class CsvController {

    private final JobLauncher csvFileItemReaderJob;
    private final ApplicationContext context;

    @GetMapping("/mellonchart/{id}")
    public String mellonJob(@PathVariable(name = "id") String id) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Job job = context.getBean("mellonChartReaderJob", Job.class);
        HashMap<String, JobParameter> map = new HashMap<>();
        map.put("id", new JobParameter(id));
        csvFileItemReaderJob.run(job, new JobParameters(map));

        return "ok";
    }

    @GetMapping("/tjchart/{id}")
    public String tjJob(@PathVariable(name = "id") String id) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Job job = context.getBean("tjChartReaderJob", Job.class);
        HashMap<String, JobParameter> map = new HashMap<>();
        map.put("id", new JobParameter(id));
        csvFileItemReaderJob.run(job, new JobParameters(map));

        return "ok";
    }
}
