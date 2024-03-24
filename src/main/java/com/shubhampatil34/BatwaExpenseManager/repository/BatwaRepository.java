package com.shubhampatil34.BatwaExpenseManager.repository;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatwaRepository extends JpaRepository<Batwa, Long> {
        List<Batwa> findAllByOrderByPriority();
}
