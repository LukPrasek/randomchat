package pl.lukaszprasek.randomchat.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends CrudRepository<BanEntity, Integer> {
    boolean existsByIp(String ip);

}
