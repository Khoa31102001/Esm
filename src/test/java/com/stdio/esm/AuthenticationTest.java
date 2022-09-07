//package com.stdio.esm;
//
//import com.stdio.esm.controller.AuthenticationController;
//import com.stdio.esm.controller.EsmExceptionController;
//import com.stdio.esm.dto.request.LoginDto;
//import com.stdio.esm.dto.response.EsmResponse;
//import com.stdio.esm.dto.response.EsmToken;
//import com.stdio.esm.exception.EsmException;
//import com.stdio.esm.model.Account;
//import com.stdio.esm.repository.AccountRepository;
//import com.stdio.esm.service.AccountService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.util.Date;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class AuthenticationTest {
//
//    @Autowired
//    AuthenticationController authenticationController;
//
//    @Autowired
//    AccountService accountService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    private Integer DURATION_ACCESSTOKEN = 604800000;
//
//    /**
//     * [Login] Test case 001: Login with an account existed in database and delete flag is false.
//     * Expectation: Login success and response data correctly.
//     */
//
//    @Test
//    public void loginTestCase001() throws EsmException {
//        // 1. Setup data to test
//        //1.1 Login with account have username = khoadeptrai, password = khoadeptrai
//        LoginDto loginData = new LoginDto("khoadeptrai", "khoadeptrai");
//        //1.2 Create account have username and password same logindata (khoadeptrai/khoadeptrai)
//        accountService.addAccountTest("khoadeptrai", "khoadeptrai", false);
//        // 2. Execute method with loginData be prepared to test
//        ResponseEntity<Map<String, Object>> result = authenticationController.login(loginData);
//
//        // 3. Verify the result with expectation
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(200, result.getBody().get("code"));
//        assertEquals("success", result.getBody().get("status"));
//        assertEquals("Login successfully!", result.getBody().get("message"));
//        assertNotNull(result.getBody().get("responseData"));
//        assertInstanceOf(EsmToken.class, result.getBody().get("responseData"));
//        EsmToken token = (EsmToken) result.getBody().get("responseData");
//        assertNotNull(token.getAccesstoken());
//        assertNotNull(token.getRefreshToken());
//        assertEquals("khoadeptrai", token.getUsername());
//        assertEquals(new Date().getDate(), token.getIssueDate().getDate());
//        assertEquals(java.sql.Date.from(Instant.now().plusMillis(DURATION_ACCESSTOKEN)).getDate(), token.getExpireDate().getDate());
//
//        // 4. Delete dummy data
//        accountService.deleteAccountTest(loginData.getUsername());
//    }
//
//
//    /**
//     * [Login] Test case 002: Login with an account existed in database and delete flag is true
//     * Expectation: Login failed and response data is null.
//     */
//    @Test
//    public void loginTestCase002()  {
//        // 1. Setup data to test
//        //1.1 Login with account have username = khoadeptrai, password = khoadeptrai
//        LoginDto loginData = new LoginDto("khoadeptrai", "khoadeptrai");
//        ResponseEntity<Map<String, Object>> result = null;
//        //1.2 Create account have username and password same logindata (khoadeptrai/khoadeptrai)
//        accountService.addAccountTest("khoadeptrai", "khoadeptrai", true);
//        // 2. Execute method with loginData be prepared to test
//        try {
//            result = authenticationController.login(loginData);
//        } catch (EsmException exception) {
//            EsmResponse esmResponse = new EsmResponse();
//            esmResponse.setCode(HttpStatus.BAD_REQUEST.value());
//            esmResponse.setStatus(EsmResponse.ERROR);
//            esmResponse.setMessage(exception.getMessage());
//            result = ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
//        }
//        // 3. Verify the result with expectation
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(400, result.getBody().get("code"));
//        assertEquals("error", result.getBody().get("status"));
//        assertNull(result.getBody().get("responseData"));
//        // 4. Delete dummy data
//        accountService.deleteAccountTest(loginData.getUsername());
//    }
//
//    /**
//     * [Login] Test case 003: Login with an account existed in database and delete flag is true
//     * Expectation: Login failed and response data is null.
//     */
//    @Test
//    public void loginTestCase003() {
//
//    }
//
//    @Test
//    public void loginTestCase004() {
//
//    }
//
//    @Test
//    public void loginTestCase005() {
//
//    }
//
//}
