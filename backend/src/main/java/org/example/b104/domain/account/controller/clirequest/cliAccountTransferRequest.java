package org.example.b104.domain.account.controller.clirequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class cliAccountTransferRequest {
    private String depositBankCode;
    private String depositAccountNo;
    private Long transactionBalance;
    private String withdrawalBankCode;
    private String withdrawalAccountNo;
    private String depositTransactionSummary;
    private String withdrawalTransactionSummary;
}
