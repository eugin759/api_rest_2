package med.voll.api.controller;//package med.voll.api.controller;
//
//import med.voll.api.domain.consulta.AgendaDeConsultaService;
//import med.voll.api.domain.consulta.DatosAgendarConsulta;
//import med.voll.api.domain.consulta.DatosDetalleConsulta;
//import med.voll.api.domain.medico.Especialidad;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.json.JacksonTester;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureJsonTesters
//class ConsultaControllerTest {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;
//
//    @Autowired
//    private  JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;
//
//    @MockBean
//    private AgendaDeConsultaService agendaDeConsultaService;
//
//    @Test
//    @DisplayName("Deberia retornar 400 cuando los datos son invalidos")
//    @WithMockUser
//    void escenario1() throws Exception {
//
//        //given //when
//        var response = mvc.perform(post("/consultas")).andReturn().getResponse();
//        //then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Test
//    @DisplayName("Deberia retornar 200 cuando los datos son validos")
//    @WithMockUser
//    void escenario2() throws Exception {
//
//        //given
//        var fecha = LocalDateTime.now().plusHours(1);
//        var especialidad = Especialidad.CARDIOLOGIA;
//        var datos = new DatosDetalleConsulta(null, 2l, 5l, fecha);
//
//        // when
//
//        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);
//
//        var response = mvc.perform(post("/consultas")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l, 5l, fecha, especialidad)).getJson())
//        ).andReturn().getResponse();
//        //then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//
//        var jsonEsperado = detalleConsultaJacksonTester.write(new DatosDetalleConsulta(null, 2l, 5l, fecha));
//        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
//    }
//
//}