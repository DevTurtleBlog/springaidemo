package com.example.aidemo.service;

import java.util.function.Function;

import com.example.aidemo.service.RectangleAreaService.Request;
import com.example.aidemo.service.RectangleAreaService.Response;

public class RectangleAreaService implements Function<Request, Response> {

	// Request for RectangleAreaService
	public record Request(double base, double height) {}
	public record Response(double area) {}

	@Override
	public Response apply(Request r) {
		return new Response(r.base()*r.height());
	}
	
}