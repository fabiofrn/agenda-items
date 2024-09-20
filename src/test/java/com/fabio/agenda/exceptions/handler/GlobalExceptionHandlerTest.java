package com.fabio.agenda.exceptions.handler;

import com.fabio.agenda.controller.request.AgendaItemRequest;
import com.fabio.agenda.exceptions.ApplicationException;
import com.fabio.agenda.exceptions.BusinessException;
import com.fabio.agenda.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TestController.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TestController testController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleBusinessException() throws Exception {
        mockMvc.perform(get("/test/businessException")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensagem").value("A pauta já está expirada."));
    }

    @Test
    void handleDataIntegrityViolationException() throws Exception {
        mockMvc.perform(get("/test/dataIntegrityViolation")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensagem").value("Registro duplicado."));
    }

    @Test
    void handleNotFoundExceptionException() throws Exception {
        mockMvc.perform(get("/test/notFoundExceptionException")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensagem").value("Registro não encontrado."));
    }

    @Test
    void handleApplicationException() throws Exception {
        mockMvc.perform(get("/test/applicationException")).andExpect(status().isInternalServerError()).andExpect(jsonPath("$.mensagem").value("Houve um erro inesperado na aplicação."));
    }

    @Test
    void handleValidationExceptions() throws Exception {
        mockMvc.perform(get("/test/methodArgumentNotValidException")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.cpf").value("O campo cpf não pode ser nulo."));
    }
}

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/businessException")
    public void dataInvalidaException() throws BusinessException {
        throw new BusinessException("A pauta já está expirada.");
    }

    @GetMapping("/dataIntegrityViolation")
    public void dataIntegrityViolation() throws BusinessException {
        throw new DataIntegrityViolationException("Registro duplicado.");
    }

    @GetMapping("/notFoundExceptionException")
    public void notFoundExceptionException() throws NotFoundException {
        throw new NotFoundException("Registro não encontrado.");
    }

    @GetMapping("/applicationException")
    public void handleApplicationException() throws RuntimeException {
        throw new ApplicationException("Houve um erro inesperado na aplicação.");
    }

    @GetMapping("/methodArgumentNotValidException")
    public void handleValidationExceptions() throws MethodArgumentNotValidException {

        BindingResult bindingResult = new BeanPropertyBindingResult(new AgendaItemRequest(null), "agendaItemRequest");
        bindingResult.addError(new FieldError("", "cpf", "O campo cpf não pode ser nulo."));

        throw new MethodArgumentNotValidException(null, bindingResult);
    }

}