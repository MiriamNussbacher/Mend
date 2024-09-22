package Mend.validator;

import Mend.repository.ScanTypeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ScanTypeValidator implements ConstraintValidator<ValidScanType, String> {

    @Autowired
    private ScanTypeRepository scanTypeRepository; // Repository to access the DB

    @Override
    public boolean isValid(String scanTypeValue, ConstraintValidatorContext context) {
        if (scanTypeValue == null || scanTypeValue.isEmpty()) {
            return false; // Invalid if null or empty
        }

        // Check if the scanType exists in the database
        return scanTypeRepository.findByScanTypeValue(scanTypeValue).isPresent();
    }
}

