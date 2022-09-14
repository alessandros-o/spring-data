package br.com.iteris.springdata.service;

import br.com.iteris.springdata.domain.dto.FuncionarioProjecao;
import br.com.iteris.springdata.domain.entity.Cargo;
import br.com.iteris.springdata.domain.entity.Funcionario;
import br.com.iteris.springdata.domain.entity.UnidadeTrabalho;
import br.com.iteris.springdata.repository.CargoRepository;
import br.com.iteris.springdata.repository.FuncionarioRepository;
import br.com.iteris.springdata.repository.UnidadeTrabalhoRepository;
import br.com.iteris.springdata.specification.SpecificationFuncionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class FuncionarioService {

    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private FuncionarioRepository funcionarioRepository;
    private CargoRepository cargoRepository;
    private UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              CargoRepository cargoRepository, UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner){
        while (system){
            System.out.println("Qual opção deseja executar?");
            System.out.println("0 - SAIR");
            System.out.println("1 - Salvar");
            System.out.println("2 - Visualizar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("5 - Buscar por nome");
            System.out.println("6 - Buscar com filro");
            System.out.println("7 - Buscar por data");
            System.out.println("8 - Buscar por descrição do cargo");
            System.out.println("9 - Buscar por descrição da unidade de trabalho");
            System.out.println("10 - Visualizar lista de funcionário com salário");
            System.out.println("11 - Fazer pesquisa dinâmica");

            int action = scanner.nextInt();

            switch (action){
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    visualizar(scanner);
                    break;
                case 3:
                    atualizar(scanner);
                    break;
                case 4:
                    deletar(scanner);
                    break;
                case 5:
                    visualizarPorNome(scanner);
                    break;
                case 6:
                    visualizarComFiltro(scanner);
                    break;
                case 7:
                    pesquisarPorDataMaior(scanner);
                    break;
                case 8:
                    pesquisarPorDescricaoDeCargo(scanner);
                    break;
                case 9:
                    pesquisarPorDescricaoDaUnidadeTrabalho(scanner);
                    break;
                case 10:
                    visualizarFuncionarioComSalario();
                    break;
                case 11:
                    pesquisaDinamica(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void salvar(Scanner scanner){
        System.out.println("Nome do funcionário:");
        String nome = scanner.next();
        System.out.println("CPF do funcionário:");
        String cpf = scanner.next();
        System.out.println("Salário do funcionário:");
        Double salario = scanner.nextDouble();
        System.out.println("Data de admissão do funcionário:");
        String dataContratacao = scanner.next();
        System.out.println("Id do Cargo do funcionário:");
        Integer cargoId = scanner.nextInt();

        List<UnidadeTrabalho> unidades = unidades(scanner);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> optionalCargo = cargoRepository.findById(cargoId);
        Cargo cargo = optionalCargo.orElseThrow();
        funcionario.setCargo(cargo);
        funcionario.setUnidadeTrabalhos(unidades);

        funcionarioRepository.save(funcionario);
        System.out.println("Salvo");
    }

    private List<UnidadeTrabalho> unidades(Scanner scanner){
        Boolean system = true;

        List<UnidadeTrabalho> unidadeTrabalhos = new ArrayList<>();
        List<Integer> idsList = new ArrayList<>();
        while (system){
            System.out.println("Digite os ids da unidade de trabalho ou 0 para SAIR");
            int id = scanner.nextInt();
            if (id != 0){
                idsList.add(id);
            }else {
                system = false;
            }
        }
        unidadeTrabalhoRepository.findAllById(idsList).forEach(x -> unidadeTrabalhos.add(x));
        return unidadeTrabalhos;
    }

    private void visualizar(Scanner scanner){
        System.out.println("Qual página você deseja visualizar");
        Integer page = scanner.nextInt();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "nome"));
        Page<Funcionario> listaFuncionarios = funcionarioRepository.findAll(pageable);
        System.out.println(listaFuncionarios);
        System.out.println("Página atual " + listaFuncionarios.getNumber());
        System.out.println("Total elemento " + listaFuncionarios.getTotalElements());
        listaFuncionarios.forEach(System.out::println);
    }

    private void atualizar(Scanner scanner){
        System.out.println("Digite o id do funcionário para atualizar");
        int id = scanner.nextInt();
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        Funcionario funcionario = funcionarioOptional.orElseThrow();

        System.out.println("Nome do funcionário:");
        String nome = scanner.next();
        System.out.println("CPF do funcionário:");
        String cpf = scanner.next();
        System.out.println("Salário do funcionário:");
        Double salario = scanner.nextDouble();
        System.out.println("Data de admissão do funcionário:");
        String dataContratacao = scanner.next();
        System.out.println("Id do Cargo do funcionário:");
        Integer cargoId = scanner.nextInt();

        List<UnidadeTrabalho> unidades = unidades(scanner);

        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> optionalCargo = cargoRepository.findById(cargoId);
        Cargo cargo = optionalCargo.orElseThrow();
        funcionario.setCargo(cargo);
        funcionario.setUnidadeTrabalhos(unidades);

        funcionarioRepository.save(funcionario);
        System.out.println("Atualizado");
    }

    private void deletar(Scanner scanner){
        System.out.println("Digite o id do funcionário para deletar");
        int id = scanner.nextInt();
        funcionarioRepository.deleteById(id);
        System.out.println("Deletado");

    }

    private void visualizarPorNome(Scanner scanner){
        System.out.println("Digite o nome para procurar");
        String nome = scanner.next();
        List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);
        funcionarios.forEach(System.out::println);
    }

    private void visualizarComFiltro(Scanner scanner){
        System.out.println("Digite o nome para pesquisa");
        String nome = scanner.next();
        scanner.nextLine();
        System.out.println("Digite o valor do salário");
        double salario = scanner.nextDouble();
        System.out.println("Digite a data de contratação");
        String data = scanner.next();

        List<Funcionario> lista = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, LocalDate.parse(data, formatter));
        lista.forEach(System.out::println);
    }

    public void pesquisarPorDataMaior(Scanner scanner){
        System.out.println("Digite a data para pesquisa");
        String data = scanner.next();

        List<Funcionario> lista = funcionarioRepository.findDataContratacaoMaior(LocalDate.parse(data, formatter));
        lista.forEach(System.out::println);
    }

    public void pesquisarPorDescricaoDeCargo(Scanner scanner){
        System.out.println("Digite a descrição do cargo");
        String descricao = scanner.next();

        List<Funcionario> lista = funcionarioRepository.findByCargoDescricao(descricao);
        lista.forEach(System.out::println);
    }

    public void pesquisarPorDescricaoDaUnidadeTrabalho(Scanner scanner){
        System.out.println("Digite a descrição da unidade de trabalho");
        String descricao = scanner.next();

        List<Funcionario> lista = funcionarioRepository.findUnidadeTrabalhos_Descricao(descricao);
        lista.forEach(System.out::println);
    }

    public void visualizarFuncionarioComSalario(){
        List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
        list.forEach(f -> System.out.println("Funcionário: id: " + f.getId() +
                " | nome: " + f.getNome() + " | salario: " + f.getSalario()));
    }

    public void pesquisaDinamica(Scanner scanner){
        System.out.println("Digite o nome");
        String nome = scanner.next();

        if (nome.equalsIgnoreCase("NULL")){
            nome = null;
        }

        System.out.println("Digite o cpf");
        String cpf = scanner.next();

        if (cpf.equalsIgnoreCase("NULL")){
            cpf = null;
        }

        System.out.println("Digite o salário");
        Double salario = scanner.nextDouble();

        if (salario == 0){
            salario = null;
        }

        System.out.println("Digite a data de contratação");
        String data = scanner.next();

        LocalDate dataContratacao;
        if (data.equalsIgnoreCase("NULL")){
            dataContratacao = null;
        }else {
            dataContratacao = LocalDate.parse(data, formatter);
        }

        List<Funcionario> funcionarios = funcionarioRepository.findAll(Specification.where(
                SpecificationFuncionario.nome(nome))
                .or(SpecificationFuncionario.cpf(cpf))
                .or(SpecificationFuncionario.salario(salario))
                .or(SpecificationFuncionario.dataContratacao(dataContratacao))
        );
        funcionarios.forEach(System.out::println);
    }
}
