package com.easybanks.accounts.service;

import com.easybanks.accounts.dto.CustomerDTO;

public interface IAccountsService {
    /*
    @return void
    @param CustomerDTO
     */
    void createAccount(CustomerDTO customerDTO);
    CustomerDTO fetchAccounts(String mobileNumber);
    boolean  updateAccountDetails(CustomerDTO customerDTO);
    boolean deleteAnAccount(String mobileNumber);
}
