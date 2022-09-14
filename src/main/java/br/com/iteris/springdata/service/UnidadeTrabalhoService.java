package br.com.iteris.springdata.service;

import br.com.iteris.springdata.domain.entity.UnidadeTrabalho;
import br.com.iteris.springdata.repository.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;
@Service
public class UnidadeTrabalhoService {

    private Boolean system = true;

    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public UnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
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

            int action = scanner.nextInt();

            switch (action){
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    visualizar();
                    break;
                case 3:
                    atualizar(scanner);
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void salvar(Scanner scanner) {
        System.out.println("Digite unidade de trabalho");
        String descricao = scanner.next();
        System.out.println("Digite o endereço");
        String endereco = scanner.next();

        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setDescricao(descricao);
        unidadeTrabalho.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Salvo");
    }

    private void visualizar(){
        Iterable<UnidadeTrabalho> unidadeTrabalhos = unidadeTrabalhoRepository.findAll();
        unidadeTrabalhos.forEach(System.out::println);
    }

    private void atualizar(Scanner scanner){
        System.out.println("Digite o id da unidade para atualizar");
        int id = scanner.nextInt();
        Optional<UnidadeTrabalho> unidadeTrabalhoOptional = unidadeTrabalhoRepository.findById(id);
        UnidadeTrabalho unidadeTrabalho = unidadeTrabalhoOptional.orElseThrow();

        System.out.println("Digite unidade de trabalho");
        String descricao = scanner.next();
        System.out.println("Digite o endereço");
        String endereco = scanner.next();

        unidadeTrabalho.setDescricao(descricao);
        unidadeTrabalho.setEndereco(endereco);

        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Atualizado");
    }

    private void deletar(Scanner scanner){
        int id = scanner.nextInt();
        System.out.println("Digite o id da unidade para deletar");
        unidadeTrabalhoRepository.deleteById(id);
    }

}
