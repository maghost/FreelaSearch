package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.service.FreelancerService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaFreelancer extends AbstractAsyncTask<Map<String, String>, List<DtoFreelancer>> {

    @Override
    protected List<DtoFreelancer> executeService(Map<String, String> params) throws IOException {
        FreelancerService service = new FreelancerService();
        return service.findByFiltro(params);
    }

}

