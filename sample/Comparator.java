package sample;

import java.util.ArrayList;

public class Comparator {
	
	private RecipeReader reader;
	private ArrayList<String> userIngredients;
	private ArrayList<Recipe> ableToCook;
	
	public Comparator(String fileName){
		reader = new RecipeReader(fileName);
		reader.readAll();
		userIngredients = new ArrayList<>();
		ableToCook = new ArrayList<>();
	}
	
	public void addUserIngredient(String userIngredient){
		userIngredients.add(userIngredient);
	} 
	
	public ArrayList<String> getUserIngredients(){
		return userIngredients;
	}
	
	public void compare(){
		boolean check, checkFinal, checkIfHave = false;
		
		for(Recipe a : reader.getRecipes()){
			for(Recipe k : ableToCook){
				if(a.equals(k)) checkIfHave = true;
			}
			if(!(a.getIngredients().size() > userIngredients.size())){
				checkFinal = true;
				for(String n : a.getIngredients()){
					check = false;
					for(String m : userIngredients) if(m.equals(n)) check = true;
					if(!check){ 
						checkFinal = false;
						break;
					}
				}
				if(checkFinal /*&& !checkIfHave*/) ableToCook.add(a);
			}
		}
	}
	
	public ArrayList<Recipe> getAbleToCook(){
		return ableToCook;
	}
	
	public ArrayList<Recipe> getRecipeList(){
		return reader.getRecipes();
	}

	public void removeUserIngredient(String toRemove){
		userIngredients.remove(toRemove);
	}

	public void clearAble(){
		ableToCook.clear();
	}
	
	public ArrayList<String> getAllUniqueIngredients(){
		ArrayList<String> toReturn = new ArrayList<>();
		boolean check = false;
		
		for(Recipe n : getRecipeList()){
			for(String m : n.getIngredients()){
				check = false;
				for(String o : toReturn) if(o.equals(m)) check = true;
				if(!check) toReturn.add(m);
			}
		}
		return toReturn;
	}
}
