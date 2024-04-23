package com.shubhampatil34.BatwaExpenseManager.service;

import com.shubhampatil34.BatwaExpenseManager.converter.BatwaConverter;
import com.shubhampatil34.BatwaExpenseManager.dto.BatwaDTO;
import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.exception.BatwaException;
import com.shubhampatil34.BatwaExpenseManager.repository.BatwaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatwaService {
    @Autowired
    private BatwaRepository batwaRepository;

    @Autowired
    private BatwaConverter batwaConverter;

    public List<BatwaDTO> getAll(){
        List<Batwa> listOfBatwa = (List<Batwa>) batwaRepository.findAllByOrderByPriority();
        List<BatwaDTO> listOfBatwaDTO = new ArrayList<>();

        for (Batwa batwa: listOfBatwa){
            BatwaDTO propertyDTO = batwaConverter.convertEntityToDTO(batwa);
            listOfBatwaDTO.add(propertyDTO);
        }

        return listOfBatwaDTO;
    }

    public BatwaDTO getById(Long id){
        Optional<Batwa> batwa = batwaRepository.findById(id);
        if(batwa.isPresent()){
            return batwaConverter.convertEntityToDTO(batwa.get());
        }
        throw new BatwaException("Batwa doesn't exists for id: " + id);
    }

    public BatwaDTO createOrUpdate(BatwaDTO batwaDTO){
        Batwa batwa = batwaConverter.convertDTOtoEntity(batwaDTO);
        return batwaConverter.convertEntityToDTO(batwaRepository.save(batwa));
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
