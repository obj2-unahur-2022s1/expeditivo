
class DocumentoException : Exception("Problema al generar un documento") {

}

class Persona(val name: String) {

}

object MesaDeEntradas {
    var ultimoTramite = 0

    fun recibir(solicitante: Persona, tramite: Tramite): Int {
        if (!tramite.esValido()) throw DocumentoException()
        tramite.id = ultimoTramite.inc()
        return tramite.id
    }
}

interface Validable {
    fun esValido(): Boolean = false
}


// Component
interface Tramite: Validable {
    var id: Int

}

// Leaf
class Documento(val descripcion: String): Tramite {
    override var id: Int = 0

    var firmado = false

    fun firmar() { firmado = true }

    override fun esValido() = firmado
}

// Composite
class Expediente(val descripcion: String): Tramite {
    override var id: Int = 0

    private val tramites = mutableListOf<Tramite>()

    fun add(tramite: Tramite ) = tramites.add(tramite)

    override fun esValido(): Boolean {
        return tramites.all { it.esValido() }
    }
}
