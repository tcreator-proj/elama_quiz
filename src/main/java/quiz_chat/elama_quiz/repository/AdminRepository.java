package quiz_chat.elama_quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quiz_chat.elama_quiz.model.Administrator;

@Repository
public interface AdminRepository extends CrudRepository<Administrator, String> {
    Administrator findAdministratorByLogin(String login);
}
