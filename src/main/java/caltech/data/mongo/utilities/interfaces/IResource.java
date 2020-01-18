package caltech.data.mongo.utilities.interfaces;

import caltech.data.mongo.utilities.abstracts.AbstractDataTransferObject;
import caltech.data.mongo.utilities.generics.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * @author Weverton Souza.
 * Created on 19/09/19
 */
public interface IResource<D extends AbstractDataTransferObject, K extends Serializable> {
        Response save(final D resource);
        Response update(final D resource);
        Response findById(final K id);
        Page<IDataTransferObject> findAll(final Pageable pageable);
        void delete(K id);
}
