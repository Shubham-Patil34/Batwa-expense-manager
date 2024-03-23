package com.shubhampatil34.BatwaExpenseManager.service;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.repository.BatwaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatwaService {
    @Autowired
    private BatwaRepository batwaRepository;
    public Batwa createOrUpdate(Batwa batwa){
        if(batwa.getId() == null){
            batwaRepository.save(batwa);
        }
        else{
            batwaRepository.save(batwa);
        }
        return batwa;
    }
}
