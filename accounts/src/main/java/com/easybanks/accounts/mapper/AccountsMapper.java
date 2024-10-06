package com.easybanks.accounts.mapper;

import com.easybanks.accounts.dto.AccountsDTO;
import com.easybanks.accounts.entities.Accounts;

public class AccountsMapper {

    public static Accounts mapToAccounts(AccountsDTO accountsDTO, Accounts accounts){
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;

    }

    public static AccountsDTO mapToAccountsDto(Accounts accounts,AccountsDTO accountsDTO){
        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());
        return accountsDTO;
    }
}
