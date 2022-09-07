package com.stdio.esm.service;

import com.stdio.esm.dto.AccountDto;
import com.stdio.esm.dto.request.ChangePasswordDto;
import com.stdio.esm.model.Account;
import com.stdio.esm.model.mapper.AccountMapper;
import com.stdio.esm.repository.AccountRepository;
import com.stdio.esm.repository.EmployeeRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);


    /**
     * Change the password of the person who is logged in
     *
     * @param changePasswordDto
     *
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void changePassword(@NotNull ChangePasswordDto changePasswordDto) {
        LOGGER.info("Started change password in service layer");
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            LOGGER.trace("New password and new confirm password not correct");
            //throw new EsmException("Password and confirm password do not match");
        }
        UserDetails esmUserDetail = getCurrentAccountOrElseThrow();
        Account account = accountRepository.findByUsername(esmUserDetail.getUsername()).get();
        LOGGER.info("{} change password", account.getUsername());
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), account.getPassword())) {
            //throw new EsmException("Wrong password");
        }
        account.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        LOGGER.info("Finished change password in service layer");
    }

    /**
     * Reset the password
     *
     * @param username
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void resetPassword(String username) throws UsernameNotFoundException {
        LOGGER.info("Started reset password in service layer");
        accountRepository.findByUsername(username).ifPresentOrElse((account) -> {
            String newPassWord = passwordEncoder.encode(RandomStringUtils.random(6, true, true));
            account.setPassword(newPassWord);
            String email = account.getEmployee().getEmail();
            sendMail(email, newPassWord);
        }, () -> {
           throw  new UsernameNotFoundException("Account is not existed");
        });
        LOGGER.info("Finished reset password in service layer");
    }

    /**
     * Get all list account
     *
     * @return {@link List<AccountDto>>}
     */
    @Transactional(readOnly = true)
    public Map<String, List<AccountDto>> getAllAccount() {
        LOGGER.info("Started get all account in service layer");
        Map<String, List<AccountDto>> responseData = new HashMap<>();
        responseData.put("accounts", accountMapper.accountListToAccountDtoList(accountRepository.findAll().stream().filter(t -> !t.getDeleteFlag()).collect(Collectors.toList())));
        LOGGER.info("Finished get all account in service layer");
        return responseData;
    }

    /**
     * Get account from id
     *
     * @param accountId
     * @return {@link AccountDto}
     */
    @Transactional(readOnly = true)
    public AccountDto getAccountById(@NotNull Long accountId) throws UsernameNotFoundException {
        LOGGER.info("Started get an account by id in service layer");
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new UsernameNotFoundException("Account is not existed"));
        LOGGER.info("Finished get an account by id in service layer");
        return accountMapper.accountToAccountDto(account);
    }

    /**
     * Get account from username
     *
     * @param username
     * @return {@link AccountDto}
     */
    @Transactional(readOnly = true)
    public AccountDto getAccountByUsername( @NotBlank String username) throws UsernameNotFoundException {
        LOGGER.info("Started get an account by username in service layer");
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account is not existed"));
        LOGGER.info("Finished get an account by username in service layer");
        return accountMapper.accountToAccountDto(account);
    }

    /**
     * create new account
     *
     * @param accountDto
     * @return {@link AccountDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public AccountDto addNewAccount( @NotNull AccountDto accountDto) {
        LOGGER.info("Started create an account in service layer");

        // Convert Account DTO to Account Entity.
        Account account = this.convertAccountToSave(accountDto);

        // Save to database.
        Account accountSaved = accountRepository.save(account);

        LOGGER.info("Finished create an account in service layer");

        // Convert Account Entity to Account DTO and return to controller.
        return accountMapper.accountToAccountDto(accountSaved);
    }

    /**
     * Update exist account
     *
     * @param accountDto
     * @return {@link AccountDto}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public AccountDto updateAccount(@NotNull AccountDto accountDto) throws UsernameNotFoundException {
        LOGGER.info("Started update an account in service layer");

        if(accountDto.getId()==0||!accountRepository.existsAccountById(accountDto.getId()))
        {
            throw new UsernameNotFoundException("Account is existed");
        }

        // Convert Account DTO to Account Entity.
        Account account = this.convertAccountToSave(accountDto);

        // Save to database.
        Account accountSaved = accountRepository.save(account);

        LOGGER.info("Finished update an account in service layer");

        // Convert Account Entity to Account DTO and return to controller.
        return accountMapper.accountToAccountDto(accountSaved);
    }

    /**
     * Delete account by id
     *
     * @param accountId
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteAccountById(@NotNull Long accountId) throws UsernameNotFoundException {
        LOGGER.info("Started delete an account in service layer");
        accountRepository.findById(accountId).ifPresentOrElse((account) -> {
            account.setDeleteFlag(true);
            if(account.getEmployee()!=null){
                account.getEmployee().setDeleteFlag(true);
            }
        }, () -> {
            throw new UsernameNotFoundException("Account is not existed");
        });
        LOGGER.info("Finished delete an account in service layer");
    }

    /**
     * Delete account by username
     *
     * @param username
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteAccountByUsername(@NotBlank String username) throws UsernameNotFoundException {
        LOGGER.info("Started delete an account in service layer");
        accountRepository.findByUsername(username).ifPresentOrElse((account) -> {
            account.setDeleteFlag(true);
            if(account.getEmployee()!=null){
                account.getEmployee().setDeleteFlag(true);
            }
        }, () -> {
            throw new UsernameNotFoundException("Account is not existed");
        });
        LOGGER.info("Finished delete an account in service layer");
    }

    /**
     * Convert AccountDto to Account for save
     *
     * @param accountDto
     * @return {@link Account}
     */
    private Account convertAccountToSave(@NotNull AccountDto accountDto) {
        Account account = accountMapper.accountDtoToAccount(accountDto);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        return account;
    }

    private Optional<UserDetails> getCurrentAccountLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
            if (authentication.getPrincipal() instanceof UserDetails) {
                return (UserDetails) authentication.getPrincipal();
            }
            return null;
        });
    }

    /**
     * Returns the current user if any, otherwise returns null
     *
     * @return {@link UserDetails}
     */
    private UserDetails getCurrentAccountOrElseThrow() {
        return getCurrentAccountLogin().orElseThrow(() -> new UsernameNotFoundException("Account is not existed")
        );
    }

    /**
     * Send Mail to reset password
     *
     * @param email
     * @param newPassword
     */
    private void sendMail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Stdio] Reset password successfully");
        message.setText("Please sign in and change this new password: " + newPassword);
        mailSender.send(message);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void addAccountTest(String username, String password, boolean deleteFlag){
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setDeleteFlag(deleteFlag);
        accountRepository.save(account);

    }
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteAccountTest(String username){
        accountRepository.deleteByUsername(username);
    }
}
