package fh.fa.data.repository;

import fh.fa.data.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class MaterialReactiveRepository extends ReactiveRepository<Material> {

    private static final String ERROR_SOURCE = "Material Repository";

    public MaterialReactiveRepository(final JpaRepository<Material, Long> repository) {
        super(repository);
    }

    @Override
    String getRepositoryIdentification() {
        return ERROR_SOURCE;
    }
}
