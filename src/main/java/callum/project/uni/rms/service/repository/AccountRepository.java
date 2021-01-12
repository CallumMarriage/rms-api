package callum.project.uni.rms.service.repository;

import callum.project.uni.rms.service.repository.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    List<Account> findAllByAccountName(String accountName);
}
