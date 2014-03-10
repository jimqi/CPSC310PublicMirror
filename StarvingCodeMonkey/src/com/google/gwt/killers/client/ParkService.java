package com.google.gwt.killers.client;

import java.util.List;

import com.google.gwt.killers.entity.Park;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("park")
public interface ParkService extends RemoteService {
  public List<Park> getParks() throws NotLoggedInException;
  
  
}