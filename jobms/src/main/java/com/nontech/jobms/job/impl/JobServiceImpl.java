package com.nontech.jobms.job.impl;

import com.nontech.jobms.job.Job;
import com.nontech.jobms.job.JobRepository;
import com.nontech.jobms.job.JobService;
import com.nontech.jobms.job.clients.CompanyClient;
import com.nontech.jobms.job.clients.ReviewClient;
import com.nontech.jobms.job.dto.JobDTO;
import com.nontech.jobms.job.external.Company;
import com.nontech.jobms.job.external.Review;
import com.nontech.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CompanyClient companyClient;
    @Autowired
    ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    private Long nextId = 1L;

    @Override
    public List<JobDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOs = new ArrayList<>();
        return jobs.stream().map(this::convertToJobDTO).collect(Collectors.toList());
    }

    private JobDTO convertToJobDTO(Job job) {

        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        JobDTO jobDTO = JobMapper.mapToJobDTO(job,company,reviews);
        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        System.out.println("Job create service called....");
//        job.setId(nextId++);
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToJobDTO(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;

    }

}
