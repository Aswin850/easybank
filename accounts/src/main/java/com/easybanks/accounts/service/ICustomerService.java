package com.easybanks.accounts.service;

import com.easybanks.accounts.dto.CustomerDetailsDTO;

public interface ICustomerService {
    CustomerDetailsDTO getCustomerDetail(String mobileNumber,String correlationID);
}
