package snmaddula.random.poc.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import snmaddula.random.poc.app.entity.AlphaEntity;

@Repository
public interface AlphaRepo extends JpaRepository<AlphaEntity, Long>  {

}
