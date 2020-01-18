package caltech.data.mongo.utilities.interfaces;

import caltech.data.mongo.utilities.abstracts.AbstractDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author Weverton Souza.
 * Created on 19/09/19
 */
@Repository
public interface IRepository<D extends AbstractDomain, K extends Serializable> extends MongoRepository<D, K> {
}
