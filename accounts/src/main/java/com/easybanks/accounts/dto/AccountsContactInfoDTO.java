package com.easybanks.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// In records type clase the field initialized during object creation can not be changed
//they can only be read using getter methode
@ConfigurationProperties("accounts")
@Getter
@Setter
public class AccountsContactInfoDTO {
    private String message;
    private Map<String,String>contactDetails;
    private List<String>onCallSupport;

}
