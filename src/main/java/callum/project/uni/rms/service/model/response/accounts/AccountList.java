package callum.project.uni.rms.service.model.response.accounts;

import callum.project.uni.rms.service.model.response.AbstractServiceResponse;
import callum.project.uni.rms.service.model.response.TargetAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Builder
public class AccountList extends AbstractServiceResponse {

    private List<TargetAccount> accountList;
}
