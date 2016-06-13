package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.service.FreelancerService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;

public class AsyncTaskFreelancer extends AbstractAsyncTask<DtoFreelancer, DtoFreelancer> {

    @Override
    protected DtoFreelancer executeService(DtoFreelancer dtoFreelancer) throws IOException {
        FreelancerService service = new FreelancerService();
        return service.save(dtoFreelancer);
    }

}

