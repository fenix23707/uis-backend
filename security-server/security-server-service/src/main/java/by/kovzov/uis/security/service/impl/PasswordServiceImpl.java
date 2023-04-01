package by.kovzov.uis.security.service.impl;

import by.kovzov.uis.common.exception.InvalidPasswordException;
import by.kovzov.uis.security.service.api.PasswordService;
import lombok.RequiredArgsConstructor;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String validateAndEncodePassword(String password) {
        verifyPassword(password);
        return passwordEncoder.encode(password);
    }

    private void verifyPassword(String password) {
        RuleResult result = passwordValidator.validate(new PasswordData(password));
        if (!result.isValid()) {
            throw new InvalidPasswordException(passwordValidator.getMessages(result));
        }
    }
}
