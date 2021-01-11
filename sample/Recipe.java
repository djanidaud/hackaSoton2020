package sample;

import java.util.ArrayList;

public class Recipe {
	
	private ArrayList<String> ingredients;
	private String name;
	private String description;
	
	public Recipe(String name, String description){
		ingredients = new ArrayList<>();
		this.name = name;
		this.description = description;
	}
	
	public Recipe(String name){
		ingredients = new ArrayList<>();
		this.name = name;
	}
	
	public Recipe(){
		ingredients = new ArrayList<>();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}

	
	public void addIngredient(String ingredient){
		ingredients.add(ingredient);
	}
	
	public ArrayList<String> getIngredients(){
		return ingredients;
	}

}
