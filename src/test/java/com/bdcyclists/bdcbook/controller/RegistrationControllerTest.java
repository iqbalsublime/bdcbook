package com.bdcyclists.bdcbook.controller;

import com.bdcyclists.bdcbook.BdcbookApplication;
import com.bdcyclists.bdcbook.WebTestConstants;
import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.RegistrationForm;
import com.bdcyclists.bdcbook.dto.RegistrationFormBuilder;
import com.bdcyclists.bdcbook.service.DuplicateEmailException;
import com.bdcyclists.bdcbook.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.bdcyclists.bdcbook.dto.RegistrationFormAssert.assertThatRegistrationForm;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BdcbookApplication.class)
@WebAppConfiguration
public class RegistrationControllerTest {

    private static final String EMAIL = "contact@bazlur.com";
    private static final String MALFORMED_EMAIL = "contact.bazlur.com";
    private static final String FIRST_NAME = "Bazlur";
    private static final String LAST_NAME = "Rahman";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRMED = "passwordConfirmed";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private UserService userServiceMock;

    @Before
    public void setUp() throws Exception {
        // Setup Spring test in webapp-mode (same config as spring-boot)
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    public void showRegistrationPage_ShouldRenderRegistrationView() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name(RegistrationController.VIEW_NAME_REGISTRATION_PAGE));
    }

    @Test
    public void showRegistrationPage_ShouldRegistrationPageWithEmptyForm() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name(RegistrationController.VIEW_NAME_REGISTRATION_PAGE))
                .andExpect(model().attribute("registrationForm", allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_CONFIRMED, isEmptyOrNullString())
                )));
    }

    @Test
    public void registerUserAccount_EmptyForm_ShouldRenderRegistrationFormWithValidationErrors() throws Exception {
        mockMvc.perform(post("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name(RegistrationController.VIEW_NAME_REGISTRATION_PAGE))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_RGISTRATION_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_CONFIRMED, isEmptyOrNullString())
                )))
                .andExpect(model().attributeHasFieldErrors(
                        WebTestConstants.MODEL_ATTRIBUTE_RGISTRATION_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME,
                        WebTestConstants.FORM_FIELD_PASSWORD,
                        WebTestConstants.FORM_FIELD_PASSWORD_CONFIRMED
                ));
    }


    //@Test TODO have to figure out null pointer exception
    public void registerUserAccount_EmailAlreadyExists_ShouldRenderRegistrationFormWithFieldError() throws Exception {
        RegistrationForm registrationForm = new RegistrationFormBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .passwordConfirmed(PASSWORD_CONFIRMED).build();

        when(userServiceMock.registerNewUserAccount(registrationForm)).thenThrow(new DuplicateEmailException(""));

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                        .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                        .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                        .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                        .param(WebTestConstants.FORM_FIELD_PASSWORD_CONFIRMED, PASSWORD)

        )
                .andExpect(status().isOk())
                .andExpect(view().name(RegistrationController.VIEW_NAME_REGISTRATION_PAGE))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_RGISTRATION_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD))
                )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_RGISTRATION_FORM, WebTestConstants.FORM_FIELD_EMAIL));


        ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor.forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        RegistrationForm formObject = registrationFormArgument.getValue();
        assertThatRegistrationForm(formObject)
                .hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME)
                .hasPassword(PASSWORD)
                .hasPasswordConfirmed(PASSWORD);
    }

    @Test
    public void registerUserAccount_WithMalformedEmail_ShouldRenderRegistrationFormWithValidationError() throws Exception {
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(WebTestConstants.FORM_FIELD_EMAIL, MALFORMED_EMAIL)
                        .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                        .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                        .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                        .param(WebTestConstants.FORM_FIELD_PASSWORD_CONFIRMED, PASSWORD)

        )
                .andExpect(status().isOk())
                .andExpect(view().name(RegistrationController.VIEW_NAME_REGISTRATION_PAGE))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_RGISTRATION_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(MALFORMED_EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD))
                )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_RGISTRATION_FORM, WebTestConstants.FORM_FIELD_EMAIL));

    }

    //@Test TODO have to figure out null pointer exception
    public void registerUserAccount_NormalRegistration_ShouldCreateNewUserAccountAndRenderHomePage() throws Exception {
        User registered = User.getBuilder()
                .id(1L)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();

        when(userServiceMock.registerNewUserAccount(isA(RegistrationForm.class))).thenReturn(registered);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                        .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                        .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                        .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                        .param(WebTestConstants.FORM_FIELD_PASSWORD_CONFIRMED, PASSWORD)

        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/"));


        ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor.forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        RegistrationForm formObject = registrationFormArgument.getValue();
        assertThatRegistrationForm(formObject)
                .hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME)
                .hasPassword(PASSWORD)
                .hasPasswordConfirmed(PASSWORD);
    }
}
