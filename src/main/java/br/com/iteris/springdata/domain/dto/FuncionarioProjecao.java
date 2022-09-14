package br.com.iteris.springdata.domain.dto;

public interface FuncionarioProjecao {
    Integer getId();
    String getNome();
    Double getSalario();

    //Uma outra alternativa para essa interface seria fazer uma classe DTO de response com os atributos
    //e o construtor recebendo os atributos na ordem da query
}
