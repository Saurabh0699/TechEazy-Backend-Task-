package com.example.TakeeazyTask.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TakeeazyTask.Model.SubjectManagement;


@Repository
public interface SubjectManagementRepository extends JpaRepository<SubjectManagement, Long>{

}
