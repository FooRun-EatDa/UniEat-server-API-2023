package foorun.unieat.api.model.database.file.repository;

import foorun.unieat.api.model.database.file.entity.ImageFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFileEntity, String> {
}
