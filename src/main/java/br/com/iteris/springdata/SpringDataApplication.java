package br.com.iteris.springdata;

import br.com.iteris.springdata.service.CargoService;
import br.com.iteris.springdata.service.FuncionarioService;
import br.com.iteris.springdata.service.UnidadeTrabalhoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private final CargoService cargoService;
	private final FuncionarioService funcionarioService;
	private final UnidadeTrabalhoService unidadeTrabalhoService;

	private Boolean system = true;

	public SpringDataApplication(CargoService cargoService, FuncionarioService funcionarioService, UnidadeTrabalhoService unidadeTrabalhoService) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeTrabalhoService = unidadeTrabalhoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while(system) {
			System.out.println("Qual ação você quer executar");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Unidade de Trabalho");
			System.out.println("3 - Funcionário");

			int action = scanner.nextInt();

			switch (action){
				case 1:
					cargoService.inicial(scanner);
					break;
				case 2:
					unidadeTrabalhoService.inicial(scanner);
					break;
				case 3:
					funcionarioService.inicial(scanner);
				default:
					system = false;
					break;
			}
		}

	}
}
