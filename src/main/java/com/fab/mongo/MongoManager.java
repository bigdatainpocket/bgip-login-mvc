package com.fab.mongo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

@Component
public class MongoManager { 
	
	
	@Autowired 
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private Mongo mongo;
	

	public <T> List<T> getAllObjects(String collectionName, Class<T> className) throws Exception {
		List<T> objectList = null;
		objectList = mongoTemplate.findAll(className, collectionName);
		return objectList;
	}
	
	public <T> T getObject(String collectionName, Query query, Class<T> className) throws Exception {
		T obj = null;
		obj = mongoTemplate.findOne(query, className, collectionName);
		return obj;
	}
	
	public <T> List<T> getObjects(String collectionName, Query query, Class<T> className) throws Exception {
		List<T> objectList = null;
		objectList = mongoTemplate.find(query, className, collectionName);
		return objectList;
	}
	
	public <T> List<T> getObjectsByField(String collectionName, String fieldName, String fieldValue, Class<T> className) throws Exception {
		List<T> objectList = null;
		Query query = new Query(Criteria.where(fieldName).is(fieldValue));
		objectList = mongoTemplate.find(query, className, collectionName);
		return objectList;
	}

	public <T> T getObjectByField(String collectionName, String fieldName, String fieldValue, Class<T> className) throws Exception {
		T object = null;
		Query query = new Query(Criteria.where(fieldName).is(fieldValue));
		object = mongoTemplate.findOne(query, className, collectionName);
		return object;
	}
	
	
	
	public <T> T getObjectByID(String collectionName, String objectID, Class<T> className) throws Exception {
		T object = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(objectID));
		object = mongoTemplate.findOne(query, className, collectionName);
		return object;
	}
	
	public <T> List<T> getObjectsByID(String collectionName, List<String> objectIDList, Class<T> className) throws Exception {
		List<T> objectList = new ArrayList<T>();
		Criteria[] orCriteriaArray = new Criteria[objectIDList.size()];
		for (String idString : objectIDList) {
			orCriteriaArray[0] = Criteria.where("id").is(idString);
		}
		Criteria orCriteria = new Criteria();
		orCriteria.orOperator(orCriteriaArray);
		
		Query query = new Query();
		query.addCriteria(orCriteria);
		
		objectList = mongoTemplate.find(query, className, collectionName);

		
		return objectList;
	}

	public Object insert(String collectionName, Object object) throws Exception {
		try {
			mongoTemplate.save(object, collectionName);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return object;
	}

	public Object update(String collectionName, Object object) throws Exception {
		try {
			mongoTemplate.save(object, collectionName);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return object;
	}

	public void delete(String collectionName, Object object) throws Exception {
		
		try {
			mongoTemplate.remove(object, collectionName);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	
	public void drop(String collectionName) throws Exception {
		
		try {
			mongoTemplate.dropCollection(collectionName);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	
	/**
	 * Get the next unique ID for a named sequence.
	 * @param db Mongo database to work with
	 * @param seq_name The name of your sequence (I name mine after my collections)
	 * @return The next ID
	 */
	public static String getOrderNextId(DB db, String seq_name) {
		
	    String sequence_collection = "orderseq"; // the name of the sequence collection
	    String sequence_field = "seq"; // the name of the field which holds the sequence
	 
	    DBCollection seq = db.getCollection(sequence_collection); // get the collection (this will create it if needed)
	 
	    // this object represents your "query", its analogous to a WHERE clause in SQL
	    DBObject query = new BasicDBObject();
	    query.put("_id", seq_name); // where _id = the input sequence name
	 
	    // this object represents the "update" or the SET blah=blah in SQL
	    DBObject change = new BasicDBObject(sequence_field, 1);
	    DBObject update = new BasicDBObject("$inc", change); // the $inc here is a mongodb command for increment
	 
	    // Atomically updates the sequence field and returns the value for you
	    DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
	    return res.get(sequence_field).toString();
	}
	
	/**
	 * Get the next unique ID for a named sequence.
	 * @param db Mongo database to work with
	 * @param seq_name The name of your sequence (I name mine after my collections)
	 * @return The next ID
	 */
	public static String getSellerNextId(DB db, String seq_name) {
		
	    String sequence_collection = "sellerseq"; // the name of the sequence collection
	    String sequence_field = "seq"; // the name of the field which holds the sequence
	 
	    DBCollection seq = db.getCollection(sequence_collection); // get the collection (this will create it if needed)
	 
	    // this object represents your "query", its analogous to a WHERE clause in SQL
	    DBObject query = new BasicDBObject();
	    query.put("_id", seq_name); // where _id = the input sequence name
	 
	    // this object represents the "update" or the SET blah=blah in SQL
	    DBObject change = new BasicDBObject(sequence_field, 1000);
	    DBObject update = new BasicDBObject("$inc", change); // the $inc here is a mongodb command for increment
	 
	    // Atomically updates the sequence field and returns the value for you
	    DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
	    return res.get(sequence_field).toString();
	}
	
	/*public DB getMongoDB() throws Exception {
		
		if (mdb == null) {
			mdb = getMongoServer().getDB(DB_NAME);
		}
		
		return mdb;
	}*/
	

}
