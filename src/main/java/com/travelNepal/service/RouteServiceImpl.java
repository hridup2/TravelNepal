package com.travelNepal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.Route;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.RouteException;
import com.travelNepal.repository.RouteRepository;
import com.travelNepal.repository.SessionRepository;

@Service
public class RouteServiceImpl implements RouteService{
	
	@Autowired
	private RouteRepository routerepo;
	@Autowired
	private SessionRepository sessionrepo;

	@Override
	public Route addRoute(String uuid, Route route) throws LoginException, RouteException {
		CurrentUserSession cus = sessionrepo.findBySessionId(uuid);
		if (cus == null) throw new LoginException("Login please");
		if(route == null) throw new RouteException("Please enter Route details properly");
//		List<Bus> buses = route.getBuses();
//		for(Bus b : buses) {
//			b.setRoute(route);
//		}
		return routerepo.save(route);
	}

	@Override
	public Route updateRoute(String uuid, Route route) throws LoginException, RouteException {
		CurrentUserSession cus = sessionrepo.findBySessionId(uuid);
		if (cus == null) throw new LoginException("Login please");
		Optional<Route> ba = routerepo.findById(route.getRouteId());
		if(ba.isEmpty()) throw new RouteException("Please enter Route details properly");
		return routerepo.save(route);
	}

	@Override
	public Route removeRoute(String uuid, int routeid) throws LoginException, RouteException {
		CurrentUserSession cus = sessionrepo.findBySessionId(uuid);
		if (cus == null) throw new LoginException("Login please");
		Optional<Route> ba = routerepo.findById(routeid);
		if(ba.isEmpty()) throw new RouteException("Please enter Route details properly");
		Route route = ba.get();
		routerepo.delete(route);
		return route;
	}

	@Override
	public Route searchRoute(int routeid) throws LoginException, RouteException {
		// TODO Auto-generated method stub
		Optional<Route> ba = routerepo.findById(routeid);
		if(ba.isEmpty()) throw new RouteException("No route found");
		Route route = ba.get();
		return route;
	}

	@Override
	public List<Route> viewRouteList() throws LoginException, RouteException {
		// TODO Auto-generated method stub
		List<Route> list = routerepo.findAll();
		if(list.isEmpty()) throw new RouteException("No route found");
		return list;
	}
	
}
