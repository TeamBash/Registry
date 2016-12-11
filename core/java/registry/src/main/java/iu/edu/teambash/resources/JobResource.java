package iu.edu.teambash.resources;

import io.dropwizard.hibernate.UnitOfWork;
import iu.edu.teambash.core.JobEntity;
import iu.edu.teambash.db.JobDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by janakbhalla on 10/12/16.
 */
@Path("/jobs")
public class JobResource {

    private JobDao jobDao;

    public JobResource(JobDao jobDao){
        this.jobDao = jobDao;
    }

    @POST
    @UnitOfWork
    @Path("/createJob")
    @Consumes(MediaType.APPLICATION_JSON)
    public JobEntity createJob(JobEntity jobEntity) {
        return jobDao.create(jobEntity);
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getDetails/{uid}")
    public List<String> getJobDetails(@PathParam("uid") int uid){
        return jobDao.findJobs(uid).stream().map(JobEntity::getJobname).collect(Collectors.toList());
    }
}
