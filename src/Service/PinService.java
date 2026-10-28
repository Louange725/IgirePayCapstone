
package Service;

import DAO.AccountDAO;
import java.sql.SQLException;

public class PinService {

    private final AccountDAO accountDAO = new AccountDAO();

    public void createPin(String account_id, String pin) throws SQLException {
        checkFormat(pin);
        if (accountDAO.findPin(account_id) == null) {
            throw new IllegalArgumentException("Account not found: " + account_id);
        }
        if (accountDAO.findPin(account_id) != null) {
            throw new IllegalStateException("This account already has a PIN. Use change PIN.");
        }
        accountDAO.savePin(account_id, pin);
    }

    public boolean validatePin(String accountId, String pin) throws SQLException {
        String saved = accountDAO.findPin(accountId);
        return saved != null && saved.equals(pin);
    }

    public void changePin(String accountId, String oldPin, String newPin) throws SQLException {
        checkFormat(newPin);
        if (!validatePin(accountId, oldPin)) {
            throw new IllegalArgumentException("Wrong PIN");
        }
        accountDAO.savePin(accountId, newPin);
    }

    private void checkFormat(String pin) {
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits");
        }
    }
}

