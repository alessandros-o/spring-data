package br.com.iteris.springdata.repository;

import br.com.iteris.springdata.domain.entity.Cargo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer> {
}
