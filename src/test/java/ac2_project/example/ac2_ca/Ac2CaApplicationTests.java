package ac2_project.example.ac2_ca;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ac2CaApplicationTests {

    @Test
    void contextLoads() {
        // Este teste valida o carregamento do contexto, como explicado acima.
    }

    @Test
    void applicationContextMain() {
        // Este teste executa o main() apenas para fins de cobertura.
        Ac2CaApplication.main(new String[] {});
    }
}