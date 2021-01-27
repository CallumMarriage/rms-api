package callum.project.uni.rms.service;

import callum.project.uni.rms.service.exception.ServiceException;
import callum.project.uni.rms.model.res.TargetProject;
import callum.project.uni.rms.model.res.accounts.AccountAndProjects;
import callum.project.uni.rms.model.res.accounts.AccountsWithProjects;
import callum.project.uni.rms.model.res.projects.Projects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectAndAccountService {

    private final ProjectService projectService;

    private final AccountService accountService;

    public AccountsWithProjects retrieveAccountsWithProjects(Long userId) throws ServiceException {

        try {

            Projects projects = projectService.retrieveProjectsByProjectManager(userId);

            Map<String, AccountAndProjects> map = new HashMap<>();

            for (TargetProject project : projects.getProjects()) {

                if (map.containsKey(project.getAccountNumber())) {
                    AccountAndProjects accountAndProjects = map.get(project.getAccountNumber());
                    accountAndProjects.addProjectToProjects(project);
                    map.replace(project.getAccountNumber(), accountAndProjects);
                } else {
                    map.put(project.getAccountNumber(), AccountAndProjects.builder()
                            .projects(List.of(project))
                            .targetAccount(accountService.getTargetAccountById(project.getAccountNumber()))
                            .build());
                }
            }

            return AccountsWithProjects.builder()
                    .accountAndProjectsList(new ArrayList<>(map.values()))
                    .build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Issue retrieve Account info for account with project search");
        }
    }
}
