package com.ronyelison.locadora.integration.controller.json;

import com.ronyelison.locadora.config.TestConfig;
import com.ronyelison.locadora.dto.usuario.TokenDTO;
import com.ronyelison.locadora.integration.dto.UsuarioDeLoginTest;
import com.ronyelison.locadora.integration.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationJsonTest extends AbstractIntegrationTest {
    private static TokenDTO tokenDTO;

    @Test
    @Order(1)
    public void testToken(){
        UsuarioDeLoginTest usuarioDeLogin = new UsuarioDeLoginTest("admin@gmail.com", "admin");

        tokenDTO = given()
                .basePath("/usuario/login")
                .port(TestConfig.SERVER_PORT)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(usuarioDeLogin)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class);

        assertNotNull(tokenDTO);
        assertNotNull(tokenDTO.getToken());
        assertNotNull(tokenDTO.getRefreshToken());
    }

    @Test
    @Order(2)
    public void testRefreshToken(){
        UsuarioDeLoginTest usuarioDeLogin = new UsuarioDeLoginTest("admin@gmail.com", "admin");

        var newtokenDTO = given()
                .basePath("/usuario/refresh")
                .port(TestConfig.SERVER_PORT)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .pathParam("email", usuarioDeLogin.getEmail())
                .header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.getRefreshToken())
                .when()
                .put("{email}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class);

        assertNotNull(newtokenDTO);
        assertNotNull(newtokenDTO.getToken());
        assertNotNull(newtokenDTO.getRefreshToken());
    }

}
