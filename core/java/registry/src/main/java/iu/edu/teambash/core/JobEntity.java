package iu.edu.teambash.core;

import javax.persistence.*;

@Entity
@Table(name = "job", schema = "TeamBash", catalog = "")
@NamedQueries({
        @NamedQuery(name = "db.JobEntity.findjobs",
                query = "SELECT u FROM JobEntity u WHERE u.uid = :uid"),
})
public class JobEntity {
    private int jobid;
    private String jobname;
    private Integer uid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobid", nullable = false)
    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    @Basic
    @Column(name = "jobname", nullable = true, length = 45)
    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    @Basic
    @Column(name = "uid", nullable = true)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobEntity jobEntity = (JobEntity) o;

        if (jobid != jobEntity.jobid) return false;
        if (jobname != null ? !jobname.equals(jobEntity.jobname) : jobEntity.jobname != null) return false;
        if (uid != null ? !uid.equals(jobEntity.uid) : jobEntity.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = jobid;
        result = 31 * result + (jobname != null ? jobname.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        return result;
    }
}
