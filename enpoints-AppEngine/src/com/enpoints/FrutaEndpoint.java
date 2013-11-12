package com.enpoints;

import com.enpoints.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "frutaendpoint", namespace = @ApiNamespace(ownerDomain = "enpoints.com", ownerName = "enpoints.com", packagePath = ""))
public class FrutaEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listFruta")
	public CollectionResponse<Fruta> listFruta(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Fruta> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Fruta as Fruta");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Fruta>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Fruta obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Fruta> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getFruta")
	public Fruta getFruta(@Named("nombre") String nombre) {
		EntityManager mgr = getEntityManager();
		Fruta fruta = null;
		try {
			fruta = (Fruta) mgr.createQuery("select from Fruta as Fruta where Nombre =:Nombre").setParameter("Nombre", nombre).getSingleResult();
			
		} finally {
			mgr.close();
		}
		return fruta;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param fruta the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertFruta")
	public Fruta insertFruta(Fruta fruta) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsFruta(fruta)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(fruta);
		} finally {
			mgr.close();
		}
		return fruta;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param fruta the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateFruta")
	public Fruta updateFruta(Fruta fruta) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsFruta(fruta)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(fruta);
		} finally {
			mgr.close();
		}
		return fruta;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeFruta")
	public void removeFruta(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Fruta fruta = mgr.find(Fruta.class, id);
			mgr.remove(fruta);
		} finally {
			mgr.close();
		}
	}

	private boolean containsFruta(Fruta fruta) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Fruta item = mgr.find(Fruta.class, fruta.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
