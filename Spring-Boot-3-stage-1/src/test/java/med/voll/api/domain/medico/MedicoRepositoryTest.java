package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Debería retornar nulo cuando el médico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEsc1(){
        // given
        LocalDateTime fechaConsulta = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).
                atTime(10, 0);

        var medico = registrarMedico("jose", "j@123", "123456", Especialidad.CARDIOLOGIA);
        var paciente= registrarPaciente("ANtonio", "a@123", "123456");
        registrarConsulta(medico, paciente, fechaConsulta);

        //when
        Medico medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, fechaConsulta);

        //then
        assertNull(medicoLibre);
    }

    @Test
    @DisplayName("Debería retornar un médico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEsc2() {

        //given
        LocalDateTime fechaConsulta = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).
                atTime(10, 0);

        var medico = registrarMedico("jose", "j@123", "123456", Especialidad.CARDIOLOGIA);

        //when
        Medico medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, fechaConsulta);

        //then
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        Medico medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        Paciente paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fechaConsulta) {
        em.persist(new Consulta(null, medico, paciente, fechaConsulta));
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosRegistroMedico(
                nombre,
                email,
                "123456789",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                "123456789",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "lazo",
                "rojo",
                "guadalajara",
                "321",
                "g4"
        );
    }


}