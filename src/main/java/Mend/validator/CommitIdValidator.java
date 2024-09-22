package Mend.validator;

import Mend.repository.CommitRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CommitIdValidator implements ConstraintValidator<ValidCommitId, Integer> {

    @Autowired
    private CommitRepository commitRepository;

    @Override
    public boolean isValid(Integer commitId, ConstraintValidatorContext context) {
        if (commitId == null) {
            return false;
        }

        return commitRepository.existsById(commitId);
    }
}
