package iu.edu.teambash.db;

import io.dropwizard.hibernate.AbstractDAO;
import iu.edu.teambash.core.JobEntity;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by janakbhalla on 10/12/16.
 */
public class JobDao extends AbstractDAO<JobEntity> {
    public JobDao(SessionFactory factory) {
        super(factory);
    }

    public JobEntity create(JobEntity job) {
        return persist(job);
    }

    public List<JobEntity> findJobs(int uid) {

        return list(namedQuery("db.JobEntity.findjobs").setParameter("uid", uid));
    }
}
