package com.shubhampatil34.BatwaExpenseManager.service;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.exception.BatwaException;
import com.shubhampatil34.BatwaExpenseManager.repository.BatwaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatwaService {
    @Autowired
    private BatwaRepository batwaRepository;

    public List<Batwa> getAll(){
        return batwaRepository.findAllByOrderByPriority();
    }

    public Batwa getById(Long id){
        Optional<Batwa> batwa = batwaRepository.findById(id);
        if(batwa.isPresent()){
            return batwa.get();
        }
        throw new BatwaException("Batwa doesn't exists for id: " + id);
    }

    public Batwa createOrUpdate(Batwa batwa){
        return batwaRepository.save(batwa);
    }

    public boolean isExists(Long id){
        return  batwaRepository.findById(id).isPresent();
    }

    public boolean delete(Long id){
        Optional<Batwa> batwa = batwaRepository.findById(id);
        if(batwa.isPresent()){
            batwaRepository.delete(batwa.get());
            return true;
        }
        throw new BatwaException("Batwa doesn't exists for id: " + id);
    }

}
