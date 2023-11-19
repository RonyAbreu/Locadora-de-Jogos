package com.ronyelison.locadora.integration.controller.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ronyelison.locadora.config.TestConfig;
import com.ronyelison.locadora.integration.dto.JogoTestDTO;
import com.ronyelison.locadora.integration.dto.TokenTestDTO;
import com.ronyelison.locadora.integration.dto.UsuarioDeLoginTest;
import com.ronyelison.locadora.integration.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JogoControllerXMLTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static JogoTestDTO jogoTestDTO;

    @BeforeAll
    public static void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        jogoTestDTO = new JogoTestDTO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonProcessingException {
        UsuarioDeLoginTest usuarioLogin = new UsuarioDeLoginTest("admin@gmail.com","admin");

        var token = given()
                .basePath("/usuario/login")
                .contentType(TestConfig.CONTENT_TYPE_XML)
                .body(usuarioLogin)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenTestDTO.class)
                .getToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + token)
                .setBasePath("/api/v1/jogos")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter((new ResponseLoggingFilter(LogDetail.ALL)))
                .build();

    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockJogo();

        var content = given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_XML)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_RONYELISON)
                .body(jogoTestDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        JogoTestDTO jogoCriado = objectMapper.readValue(content, JogoTestDTO.class);
        jogoTestDTO = jogoCriado;

        assertNotNull(jogoCriado);
        assertNotNull(jogoCriado.getId());
        assertNotNull(jogoCriado.getNome());
        assertNotNull(jogoCriado.getGenero());
        assertNotNull(jogoCriado.getValor());
        assertNotNull(jogoCriado.getQuantidadeEmEstoque());
        assertNotNull(jogoCriado.getUrlDaImagem());

        assertTrue(jogoCriado.getId() > 0);

        assertEquals("Novo Jogo",jogoCriado.getNome());
        assertEquals("Terror",jogoCriado.getGenero());
        assertEquals(10.00,jogoCriado.getValor());
        assertEquals(4,jogoCriado.getQuantidadeEmEstoque());
        assertEquals("img.com",jogoCriado.getUrlDaImagem());
    }

    @Test
    @Order(2)
    public void testCreateWithWrongOrigin(){
        mockJogo();

        var content = given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_XML)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_FALSE)
                .body(jogoTestDTO)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);

        assertEquals("Invalid CORS request",content);
    }

    @Test
    @Order(3)
    public void findById() throws JsonProcessingException {
        mockJogo();

        var content = given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_XML)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_RONYELISON)
                .pathParam("id",jogoTestDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        var jogoCriado = objectMapper.readValue(content, JogoTestDTO.class);

        assertNotNull(content);
        assertNotNull(jogoCriado.getId());
        assertNotNull(jogoCriado.getNome());
        assertNotNull(jogoCriado.getGenero());
        assertNotNull(jogoCriado.getValor());
        assertNotNull(jogoCriado.getQuantidadeEmEstoque());
        assertNotNull(jogoCriado.getUrlDaImagem());

        assertTrue(jogoCriado.getId() > 0);

        assertEquals("Novo Jogo",jogoCriado.getNome());
        assertEquals("Terror",jogoCriado.getGenero());
        assertEquals(10.00,jogoCriado.getValor());
        assertEquals(4,jogoCriado.getQuantidadeEmEstoque());
        assertEquals("img.com",jogoCriado.getUrlDaImagem());
    }

    @Test
    @Order(4)
    public void findByIdWithWrongOrigin(){
        mockJogo();

        var content = given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_XML)
                .header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_FALSE)
                .pathParam("id",jogoTestDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content);

        assertEquals("Invalid CORS request",content);
    }

    private void mockJogo() {
        jogoTestDTO.setNome("Novo Jogo");
        jogoTestDTO.setGenero("Terror");
        jogoTestDTO.setValor(10.00);
        jogoTestDTO.setQuantidadeEmEstoque(4);
        jogoTestDTO.setUrlDaImagem("img.com");
    }

}

