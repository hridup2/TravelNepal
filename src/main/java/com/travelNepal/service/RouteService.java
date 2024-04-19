package com.travelNepal.service;

import java.util.List;

import com.travelNepal.entity.Route;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.RouteException;

public interface RouteService {
	public Route addRoute(String uuid, Route route) throws LoginException, RouteException;
	public Route updateRoute(String uuid, Route route) throws LoginException, RouteException;
	public Route removeRoute(String uuid, int routeid) throws LoginException, RouteException;
	public Route searchRoute(int routeid) throws LoginException, RouteException;
	public List<Route> viewRouteList() throws LoginException, RouteException;
}