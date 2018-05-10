package com.codebind.maven_at_the_movies;
import com.ibm.*;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.AddTrainingDataOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World! lol" );
        Discovery service = new Discovery("2017-11-07");
        service.setUsernameAndPassword
        	("37ff4622-1560-44b5-9b69-4d5c1f42b64d", "XyugnUAcbpFN");
        String name = service.getName();
        System.out.println("Discovery running!");
        System.out.println(name);
        
        String environmentId = "60a93d55-3a70-458e-9d2c-148be2817131";
        String collectionId = "bb70f2b2-cb3c-40ad-8c2d-484a1405eb08";
        QueryOptions queryOptions = new QueryOptions.Builder(environmentId, collectionId).build();
        QueryResponse queryResponse = service.query(queryOptions).execute();
        
        System.out.println("Empty query.");
    }
}
