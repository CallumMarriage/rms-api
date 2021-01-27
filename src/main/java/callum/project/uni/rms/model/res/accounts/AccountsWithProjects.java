package callum.project.uni.rms.model.res.accounts;

import callum.project.uni.rms.model.res.AbstractServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class AccountsWithProjects extends AbstractServiceResponse {

    private List<AccountAndProjects> accountAndProjectsList;
}
