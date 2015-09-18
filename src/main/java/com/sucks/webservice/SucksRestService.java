package com.sucks.webservice;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sucks.db.SucksDBManager;
import com.sucks.dto.SucksDto;


import com.google.gson.Gson;

/**
 * Created by swachtest on 01/05/15.
 */
@Path("/sucks")
public class SucksRestService {

    @POST
    @Path("/sucked")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostSucksData(SucksDto sucksDto) {

        String result = "Track saved : " + sucksDto;
        SucksDBManager.insertSuckData(sucksDto);
        return Response.status(201).entity(result).build();

    }

    @GET
        @Path("/sucked")
        @Produces("application/json")
        public String getsucksData(@QueryParam("param") String msg)
        {
            String sucksData = null;
            try
            {
                List<SucksDto> sucksDataList = SucksDBManager.getSucksData();

                Gson gson = new Gson();
                sucksData = gson.toJson(sucksDataList);
                if(sucksDataList != null)
                System.out.println("Total sucksData from rest API  "+sucksDataList.size());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Exception Error"); //Console
            }
            return sucksData;
        }





}
