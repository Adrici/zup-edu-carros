package carros

import io.micronaut.core.annotation.Introspected
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.GenerationType.*
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Introspected
data class Carro (@field:NotBlank @Column(nullable = false) val modelo: String?,
                  @field:NotBlank @Column(nullable = false, unique = true) val placa: String?) {

   @Id
   @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null

}
