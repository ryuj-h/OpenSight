package org.example.b104.domain.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.b104.domain.account.controller.record.InquireDepositorAccountNumberRecord;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class InquireDepositorAccountNumberResponse {
    AccountResponseHeader Header;
    String result;
    InquireDepositorAccountNumberRecord REC;
}
