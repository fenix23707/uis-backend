package by.kovzov.uis.security.service;

import java.util.ArrayList;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityServiceConfig {


    @Bean
    public PasswordValidator passwordValidator() {
        List<Rule> rules = new ArrayList<>();

        rules.add(new LengthRule(8, 20));
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

        return new PasswordValidator(rules);
    }
}
