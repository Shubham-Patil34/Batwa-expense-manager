package com.shubhampatil34.BatwaExpenseManager.converter;

import com.shubhampatil34.BatwaExpenseManager.dto.BatwaDTO;
import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import org.springframework.stereotype.Component;

@Component
public class BatwaConverter {
    public Batwa convertDTOtoEntity(BatwaDTO BatwaDTO){
        Batwa Batwa = new Batwa();
        if(BatwaDTO.getId() != null){
            Batwa.setId(BatwaDTO.getId());
        }
        Batwa.setName(BatwaDTO.getName());
        Batwa.setDescription(BatwaDTO.getDescription());
        Batwa.setAccountNumber(BatwaDTO.getAccountNumber());
        Batwa.setPriority(BatwaDTO.getPriority());
        Batwa.setCurrentBalance(BatwaDTO.getCurrentBalance());

        return Batwa;
    }
    public BatwaDTO convertEntityToDTO( Batwa Batwa){

        BatwaDTO BatwaDTO = new BatwaDTO();
        BatwaDTO.setId(Batwa.getId());
        BatwaDTO.setName(Batwa.getName());
        BatwaDTO.setDescription(Batwa.getDescription());
        BatwaDTO.setAccountNumber(Batwa.getAccountNumber());
        BatwaDTO.setPriority(Batwa.getPriority());
        BatwaDTO.setCurrentBalance(Batwa.getCurrentBalance());

        return BatwaDTO;
    }
}
