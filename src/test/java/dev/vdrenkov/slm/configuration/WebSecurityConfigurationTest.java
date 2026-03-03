package dev.vdrenkov.slm.configuration;

import dev.vdrenkov.slm.controller.AuthorController;
import dev.vdrenkov.slm.controller.ClientController;
import dev.vdrenkov.slm.controller.CsrfController;
import dev.vdrenkov.slm.controller.UserController;
import dev.vdrenkov.slm.dto.ClientDto;
import dev.vdrenkov.slm.jwt.JwtRequestFilter;
import dev.vdrenkov.slm.jwt.JwtTokenUtil;
import dev.vdrenkov.slm.jwt.JwtUserDetailsService;
import dev.vdrenkov.slm.request.UserRequest;
import dev.vdrenkov.slm.service.AuthorService;
import dev.vdrenkov.slm.service.ClientService;
import dev.vdrenkov.slm.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = { AuthorController.class, ClientController.class, UserController.class, CsrfController.class })
@Import({ WebSecurityConfiguration.class, WebSecurityConfigurationTest.SecurityFilterConfig.class })
class WebSecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private ClientService clientService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @MockitoBean
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    void testLogin_publicEndpoint_accessibleWithoutAuthentication() throws Exception {
        final HttpCookie cookie = ResponseCookie.from("Cookie", "jwt-token").httpOnly(true).path("/").build();
        when(userService.login(any(UserRequest.class))).thenReturn(cookie);

        final String json = objectMapper.writeValueAsString(new UserRequest("librarian", "password123"));

        mockMvc
            .perform(post("/login").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    void testCsrf_publicEndpoint_accessibleWithoutAuthentication() throws Exception {
        mockMvc
            .perform(get("/csrf"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andExpect(header().string("Set-Cookie", org.hamcrest.Matchers.containsString("XSRF-TOKEN=")));
    }

    @Test
    void testGetAuthors_withoutAuthentication_forbidden() throws Exception {
        mockMvc.perform(get("/authors")).andExpect(status().isForbidden());
    }

    @Test
    void testGetAuthors_withLibrarian_allowed() throws Exception {
        when(authorService.getAllAuthorsDto()).thenReturn(Collections.emptyList());

        mockMvc
            .perform(get("/authors").with(user("librarian").authorities(new SimpleGrantedAuthority("LIBRARIAN"))))
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteClient_withLibrarian_forbidden() throws Exception {
        mockMvc
            .perform(delete("/clients/1")
                .with(csrf())
                .with(user("librarian").authorities(new SimpleGrantedAuthority("LIBRARIAN"))))
            .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteClient_withAdmin_allowed() throws Exception {
        when(clientService.deleteClient(anyInt())).thenReturn(new ClientDto(1, "N", "S", "0888888888", "n@s.com"));

        mockMvc
            .perform(
                delete("/clients/1").with(csrf()).with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
            .andExpect(status().isNoContent());
    }

    @TestConfiguration
    static class SecurityFilterConfig {
        @Bean
        JwtRequestFilter jwtRequestFilter(final JwtTokenUtil jwtTokenUtil,
            final JwtUserDetailsService jwtUserDetailsService) {
            return new JwtRequestFilter(jwtTokenUtil, jwtUserDetailsService);
        }
    }
}
