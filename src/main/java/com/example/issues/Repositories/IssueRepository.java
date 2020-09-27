package com.example.issues.Repositories;

import com.example.issues.Entities.Issue;
import com.example.issues.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface IssueRepository extends CrudRepository<Issue,Integer> {

    @Query("select i from Issue i where i.status = 'OnGoing' or i.status= 'Complete' order by case when i.user.userid = :id then '0' else '1' end, i.created desc " )
    ArrayList<Issue> findAllBy(@Param("id")int id);

    @Query("select i from Issue i where i.status = 'OnGoing' or i.status= 'Complete'")
    ArrayList<Issue> findAll();

    @Query("select i from Issue i where i.status = 'Closed' and i.user.username = :username")
    ArrayList<Issue> findAllClosedByUser(@Param("username")String username);

    @Query("select i from Issue i where i.status = 'Closed'")
    ArrayList<Issue> findAllClosedBy();

    @Transactional
    @Modifying
    @Query("update Issue i set i.status = :status where i.issueid = :id")
    void updateStatusIssue(@Param("id")int id, @Param("status")Status status);

    @Transactional
    @Modifying
    @Query("update Issue i set i.status = :status,i.complete = :time where i.issueid = :id")
    void setDone(@Param("id")int id, @Param("status")Status status, @Param("time")Date time);
}
