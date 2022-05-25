import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan

class ExpeditivoDesc: DescribeSpec({
    val rene = Persona("Rene")
    val laura = Persona("Laura")

    describe("testear documento unico") {
        val solicitudConstancia = Documento("Solicitud de constancia de examen")
        it("registrar un documento invalido") {
            shouldThrowExactly<DocumentoException> {
                MesaDeEntradas.recibir(rene, solicitudConstancia)
            }
        }
        it("registrar un documento valido firmado") {
            solicitudConstancia.firmar()
            MesaDeEntradas.recibir(rene, solicitudConstancia).shouldBeGreaterThan(0)
        }
    }

    describe("testear expediente") {
        val inscripcionCarrera = Expediente("Inscripcion")
        val tituloSecundario = Documento("Titulo secundario")
        tituloSecundario.firmar()
        val cartaPresentacion = Documento("La carta de presentacion")
        inscripcionCarrera.add(tituloSecundario)
        inscripcionCarrera.add(cartaPresentacion)

        it("registrar un expediente con alguno de los documentos no firmados") {
            shouldThrowExactly<DocumentoException> {
                MesaDeEntradas.recibir(laura, inscripcionCarrera)
            }
        }
        it("registrar un expediente valido con todos los documentos firmados") {
            cartaPresentacion.firmar()
            MesaDeEntradas.recibir(laura, inscripcionCarrera).shouldBeGreaterThan(0)
        }

        val materiasAprobadas = Documento("Materias que aprobe en la otra facu")
        val programaDeLasMaterias = Documento("Listadito de programa de materias que aprobe")
        val aprobarPorEquivalencia = Expediente("Solicitud de aprobar materias por equivalencia")
        programaDeLasMaterias.firmar()
        materiasAprobadas.firmar()
        cartaPresentacion.firmar()
        aprobarPorEquivalencia.add(materiasAprobadas)
        aprobarPorEquivalencia.add(programaDeLasMaterias)

        it("registrar un expediente valido que luego sera sub expediente") {
            MesaDeEntradas.recibir(laura, aprobarPorEquivalencia)
        }

        inscripcionCarrera.add(aprobarPorEquivalencia)
        it("registrar un expediente con un subexpediente") {
            MesaDeEntradas.recibir(laura, inscripcionCarrera)
        }

    }

}
)
