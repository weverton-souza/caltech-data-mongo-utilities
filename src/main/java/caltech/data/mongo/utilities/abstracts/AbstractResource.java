package caltech.data.mongo.utilities.abstracts;

import caltech.data.mongo.utilities.generics.Response;
import caltech.data.mongo.utilities.interfaces.IDataTransferObject;
import caltech.data.mongo.utilities.interfaces.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @author Weverton Souza.
 * Created on 16/06/19
 */
@SuppressWarnings("ALL")
public abstract class AbstractResource<T extends AbstractDataTransferObject, K extends Serializable> implements IResource<T, K> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final AbstractService<T, K> abstractService;

    protected AbstractResource(AbstractService<T, K> abstractService) {
        this.abstractService = abstractService;
    }

    @Override
    @PostMapping
    public Response save(@Valid @RequestBody T resource) {
        IDataTransferObject dataTransferObject = abstractService.saveOrUpdate(resource);

        return  new Response<>(
                dataTransferObject,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                HttpStatus.CREATED.getReasonPhrase());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Response update(@Valid @RequestBody T resource) {
        IDataTransferObject dataTransferObject = this.abstractService.saveOrUpdate(resource);

        return new Response<>(
                dataTransferObject,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                HttpStatus.CREATED.getReasonPhrase());
    }

    @Override
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response findById(@PathVariable K id) {
        IDataTransferObject dataTransferObject = this.abstractService.findById(id);

        return new Response<>(
                dataTransferObject,
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase());
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<IDataTransferObject> findAll(Pageable pageable) {
        return abstractService.findAll(pageable);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable K id) {
        this.abstractService.delete(id);
    }
}
