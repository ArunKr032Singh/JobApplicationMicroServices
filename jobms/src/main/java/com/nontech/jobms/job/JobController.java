package com.nontech.jobms.job;

import com.nontech.jobms.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
//        Company c = job.getCompany();

        jobService.createJob(job);
        System.out.println("Job went to create failed");
        return new ResponseEntity<>("Job added successfully",HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id)
    {
        JobDTO jobDTO = jobService.getJobById(id);
        if(jobDTO !=null)
        {
            return  new ResponseEntity<>(jobDTO, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(jobDTO, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id)
    {
        boolean deleted = jobService.deleteJobById(id);
        if(deleted)
        {
            return  new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Job with id "+id+" Not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@RequestBody Job job, @PathVariable Long id){
        boolean updated = jobService.updateJobById(id,job);
        if(updated)
            return new ResponseEntity<>("Job updated successfully",HttpStatus.CREATED);
        return new ResponseEntity<>("Job not found with id "+id,HttpStatus.NOT_FOUND);
    }
}
