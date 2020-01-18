package com.icosom.social;

/**
 * Created by admin on 21-04-2018.
 */

public class Transfer_Model {
    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    String Balance;
    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    String beneficiaryId;
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryMobileNumber() {
        return beneficiaryMobileNumber;
    }

    public void setBeneficiaryMobileNumber(String beneficiaryMobileNumber) {
        this.beneficiaryMobileNumber = beneficiaryMobileNumber;
    }

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    String beneficiaryName;
    String beneficiaryMobileNumber;

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    String customerMobile;



    String beneficiaryAccountNumber;

    public Transfer_Model(String beneficiaryName, String beneficiaryMobileNumber, String beneficiaryAccountNumber ,String beneficiaryId ,String customerMobile , String Balance) {
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryMobileNumber = beneficiaryMobileNumber;
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
        this.beneficiaryId=beneficiaryId;
        this.customerMobile=customerMobile;
        this.Balance =Balance;
    }
}
