package caltech.data.mongo.utilities.abstracts;

import caltech.data.mongo.utilities.exceptions.ResourceNotFoundException;
import caltech.data.mongo.utilities.interfaces.IDataTransferObject;
import caltech.data.mongo.utilities.interfaces.IDomainMapper;
import caltech.data.mongo.utilities.interfaces.IRepository;
import caltech.data.mongo.utilities.interfaces.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Weverton Souza.
 * Created on 16/06/19
 */
@SuppressWarnings("ALL")
public abstract class AbstractService<D extends AbstractDataTransferObject, K extends Serializable>
        implements IService<D, K> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected IRepository repository;
    protected final IDomainMapper mapper;

    protected AbstractService(final IRepository repository, final IDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public IDataTransferObject saveOrUpdate(D dto) {
        if (null == dto.id) {
            dto.id = UUID.randomUUID().toString();
        }
        return this.mapper.toDTO((AbstractDomain) this.repository.save(this.mapper.toDomain(dto)));
    }

    @Override
    public IDataTransferObject findById(K id) {
        Optional<IDataTransferObject> optionalResource = this.repository.findById(id);
        return this.mapper.toDTO((AbstractDomain) optionalResource.orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public Page<IDataTransferObject> findAll(Pageable pageable) {
        return this.repository
                .findAll(pageable).map(p -> this.mapper.toDTO((AbstractDomain) p));
    }

    @Override
    public void delete(K id) {
        Optional<IDataTransferObject> optionalResource = this.repository.findById(id);
        this.repository.delete(optionalResource.orElseThrow(ResourceNotFoundException::new));
    }
}
