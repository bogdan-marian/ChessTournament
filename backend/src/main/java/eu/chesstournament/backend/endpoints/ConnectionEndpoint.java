package eu.chesstournament.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import eu.chesstournament.backend.entities.Connection;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
		name = "connectionApi",
		version = "v1",
		resource = "connection",
		namespace = @ApiNamespace(
				ownerDomain = "entities.backend.chesstournament.eu",
				ownerName = "entities.backend.chesstournament.eu",
				packagePath = ""
		)
)
public class ConnectionEndpoint {

	private static final Logger logger = Logger.getLogger(ConnectionEndpoint.class.getName());

	private static final int DEFAULT_LIST_LIMIT = 20;

	static {
		// Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
		ObjectifyService.register(Connection.class);
	}

	/**
	 * Returns the {@link Connection} with the corresponding ID.
	 *
	 * @param id the ID of the entity to be retrieved
	 * @return the entity with the corresponding ID
	 * @throws NotFoundException if there is no {@code Connection} with the provided ID.
	 */
	@ApiMethod(
			name = "get",
			path = "connection/{id}",
			httpMethod = ApiMethod.HttpMethod.GET)
	public Connection get(@Named("id") Long id) throws NotFoundException {
		logger.info("Getting Connection with ID: " + id);
		Connection connection = ofy().load().type(Connection.class).id(id).now();
		if (connection == null) {
			throw new NotFoundException("Could not find Connection with ID: " + id);
		}
		return connection;
	}

	/**
	 * Inserts a new {@code Connection}.
	 */
	@ApiMethod(
			name = "insert",
			path = "connection",
			httpMethod = ApiMethod.HttpMethod.POST)
	public Connection insert(Connection connection) {
		// Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
		// You should validate that connection.id has not been set. If the ID type is not supported by the
		// Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
		//
		// If your client provides the ID then you should probably use PUT instead.
		ofy().save().entity(connection).now();
		logger.info("Created Connection.");

		return ofy().load().entity(connection).now();
	}

	/**
	 * Updates an existing {@code Connection}.
	 *
	 * @param id         the ID of the entity to be updated
	 * @param connection the desired state of the entity
	 * @return the updated version of the entity
	 * @throws NotFoundException if the {@code id} does not correspond to an existing
	 *                           {@code Connection}
	 */
	@ApiMethod(
			name = "update",
			path = "connection/{id}",
			httpMethod = ApiMethod.HttpMethod.PUT)
	public Connection update(@Named("id") Long id, Connection connection) throws NotFoundException {
		// TODO: You should validate your ID parameter against your resource's ID here.
		checkExists(id);
		ofy().save().entity(connection).now();
		logger.info("Updated Connection: " + connection);
		return ofy().load().entity(connection).now();
	}

	/**
	 * Deletes the specified {@code Connection}.
	 *
	 * @param id the ID of the entity to delete
	 * @throws NotFoundException if the {@code id} does not correspond to an existing
	 *                           {@code Connection}
	 */
	@ApiMethod(
			name = "remove",
			path = "connection/{id}",
			httpMethod = ApiMethod.HttpMethod.DELETE)
	public void remove(@Named("id") Long id) throws NotFoundException {
		checkExists(id);
		ofy().delete().type(Connection.class).id(id).now();
		logger.info("Deleted Connection with ID: " + id);
	}

	/**
	 * List all entities.
	 *
	 * @param cursor used for pagination to determine which page to return
	 * @param limit  the maximum number of entries to return
	 * @return a response that encapsulates the result list and the next page token/cursor
	 */
	@ApiMethod(
			name = "list",
			path = "connection",
			httpMethod = ApiMethod.HttpMethod.GET)
	public CollectionResponse<Connection> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
		limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
		Query<Connection> query = ofy().load().type(Connection.class).limit(limit);
		if (cursor != null) {
			query = query.startAt(Cursor.fromWebSafeString(cursor));
		}
		QueryResultIterator<Connection> queryIterator = query.iterator();
		List<Connection> connectionList = new ArrayList<Connection>(limit);
		while (queryIterator.hasNext()) {
			connectionList.add(queryIterator.next());
		}
		return CollectionResponse.<Connection>builder().setItems(connectionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
	}

	private void checkExists(Long id) throws NotFoundException {
		try {
			ofy().load().type(Connection.class).id(id).safe();
		} catch (com.googlecode.objectify.NotFoundException e) {
			throw new NotFoundException("Could not find Connection with ID: " + id);
		}
	}
}