package callum.project.uni.rms.service.mapper;

import callum.project.uni.rms.model.req.AccountCreateReq;
import callum.project.uni.rms.model.res.TargetAccount;
import callum.project.uni.rms.service.repository.model.Account;
import org.springframework.stereotype.Component;

import static callum.project.uni.rms.service.mapper.MapperUtils.convertLocalDateToSqlDate;
import static callum.project.uni.rms.service.mapper.MapperUtils.convertSqlDateToLocalDate;

@Component
public class AccountMapper {

    public static TargetAccount mapAccountToTargetAccount(Account dbAccount) {

        return TargetAccount.builder()
                .accountCode(dbAccount.getAccountNumber())
                .accountName(dbAccount.getAccountName())
                .accountManagerId(dbAccount.getAccountManagerId())
                .endDate(convertSqlDateToLocalDate(dbAccount.getEndDate()))
                .startDate(convertSqlDateToLocalDate(dbAccount.getStartDate()))
                .description(dbAccount.getDescription())
                .build();
    }

    public static Account mapAccountCreateReqToAccount(AccountCreateReq createReq) {
       
        return Account.builder()
                .accountName(createReq.getAccountName())
                .accountNumber(createReq.getAccountNumber())
                .accountManagerId(createReq.getAccountManagerId())
                .description(createReq.getDescription())
                .startDate(convertLocalDateToSqlDate(createReq.getStartDate()))
                .endDate(convertLocalDateToSqlDate(createReq.getEndDate()))
                .build();
    }
}
