package ca.bertsa.internos.repository;

import ca.bertsa.internos.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    boolean existsByToken(String token);

    PasswordResetToken getByToken(String token);

    boolean existsByTokenAndUnusableTrue(String token);
}
