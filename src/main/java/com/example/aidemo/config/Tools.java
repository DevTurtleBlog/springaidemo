package com.example.aidemo.config;

import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.aidemo.service.RectangleAreaService;

@Configuration
public class Tools {
	
	public static final String RECTANGLE_AREA_FUNCTION_NAME = "rectangleAreaFunction";
	public static final String RECTANGLE_AREA_TOOL_NAME = "rectangleAreaTool";
	public static final String RECTANGLE_AREA_FUNCTION_DESCRIPTION = "Calculate the area of a rectangle";
	
	@Bean(RECTANGLE_AREA_FUNCTION_NAME)
	public FunctionCallback rectangleAreaFunctionCallback(RectangleAreaService rectangleAreaService) {
		return FunctionCallbackWrapper.builder(rectangleAreaService)
				.withName(RECTANGLE_AREA_FUNCTION_NAME).withDescription(RECTANGLE_AREA_FUNCTION_DESCRIPTION)
				.withResponseConverter(response -> Double.toString(response.area()))
				.build();
	}
	
	@Bean(RECTANGLE_AREA_TOOL_NAME)
	public OpenAiApi.FunctionTool rectangleAreaFunctionTool() {
		String jsonToolDescription = "{\n"
				+ "    \"type\": \"object\",\n"
				+ "    \"properties\": {\n"
				+ "        \"base\": {\n"
				+ "            \"type\": \"integer\",\n"
				+ "            \"description\": \"The base of the rectangle\"\n"
				+ "        },\n"
				+ "        \"height\": {\n"
				+ "            \"type\": \"integer\",\n"
				+ "            \"description\": \"The height of the rectangle\"\n"
				+ "        }\n"
				+ "    },\n"
				+ "    \"required\": [\"base\", \"height\"]\n"
				+ "}\n"
				+ "";
		
		OpenAiApi.FunctionTool.Function function = 
				new OpenAiApi.FunctionTool.Function(
						RECTANGLE_AREA_FUNCTION_DESCRIPTION, RECTANGLE_AREA_FUNCTION_NAME,
						ModelOptionsUtils.jsonToMap(jsonToolDescription));
		
		return new OpenAiApi.FunctionTool(OpenAiApi.FunctionTool.Type.FUNCTION, function);
	}
	
}
