package ConnectMongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoConnection {


    public static MongoDatabase mongoDatabase =null;

    public static MongoDatabase connectToMongoDB (){


        MongoClient mongoClient = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("Students");
        System.out.printf("DataBAse Connected");

        return mongoDatabase;
    }

    public static String insertToMongoDB(User user) {
        String profile= user.getStudentName();
        MongoDatabase mongoDatabase = connectToMongoDB();
        MongoCollection<Document> collection = mongoDatabase.getCollection ("profile");
        Document document= new Document().append("studentName", user.getStudentName()).append("studentID", user.getStudentID()).append("studentDOB", user.getStudentDOB()
        );
        collection.insertOne(document);
        return profile + "has been registered";
    }
    public static List<User> readFromMongoDB(){

        List <User> list = new ArrayList<User>();
        User user = new User();
        MongoDatabase mongoDatabase = connectToMongoDB();
        MongoCollection<Document> collection =mongoDatabase.getCollection("mymom");
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for (Document doc : iterable){

            String id = "";
            int idInt = 0;
            String studentName = (String)doc.get("studentName");
            user.setStudentName(studentName);
            String studentID = (String)doc.get("studentID");
            user.setStudentID(studentID);
            String studentDOB = (String)doc.get("studentDOB");
            user.setStudentDOB(studentDOB);
            user = new User(studentName, studentID, studentDOB);
            list.add(user);
        }
        return list;
    }

    public static void main(String[] args)  throws Exception{
        insertToMongoDB (new User( "Jesus","101", "12/25/0000"));
        List<User> user = readFromMongoDB();
        for (User person:user){
            System.out.println(person.getStudentName() + " " +person.getStudentID() +" " +person.getStudentDOB());
            System.out.println("Congrats you passed the test");
        }

    }
}
