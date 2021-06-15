package br.com.zup.edu

import carros.Carro
import carros.CarroRepository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION, // para o @BeforeEach rode na mesma transação que o @Testm, ao final sera comitado!
    transactional = false // default => true
)

class CarrosGrocTest {

    @Inject
    lateinit var repository: CarroRepository


    //Aqui a transação é comitada no final, diferente do @Teste
    @BeforeEach
    private fun setup(){
        repository.deleteAll()
    }

    @AfterEach
    fun cleanUp(){
        repository.deleteAll()
    }

    @Test //o micronaut abre uma transação para cada teste e fecha no final
    fun `deve inserir novo carro`(){
        repository.save(Carro(modelo = "Gol", placa = "HPX - 1234"))
        assertEquals(1, repository.count())

    }


    //a transação nao é comitada no final do teste, acontec eo ROLLBACK
    // O padrão do Micronaut faz o RollBack de cada transação
    @Test
    fun `deve encontrar carro por placa`(){
        //cenario
        repository.save(Carro(modelo = "Palio", placa = "OIP - 9876"))
        //ação
        val existente = repository.existsByPlaca("OIP - 9876")
        //validação
        assertTrue(existente)

    }

}