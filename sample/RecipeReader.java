package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RecipeReader{

    private String fileName;
    private BufferedReader reader;
    private ArrayList<Recipe> list;

    public RecipeReader(String fileName){
    	
        try{
            this.fileName = fileName;
            reader = new BufferedReader(new FileReader(fileName));
        }catch(Exception e){
            System.out.println(e);
        }
        
        list = new ArrayList<>();
    }
    
    //Format name;description;ingredient1;ingredient2;
    public void readRecipe(){
        String toRead = "";
        String[] division = new String[25];     
        
        try{
            toRead = reader.readLine();
            division = toRead.split(";");
            Recipe toAdd = new Recipe(division[0], division[1]);
                       
            for(int i = 2; i < division.length; i++){
            	if(division[i] != null) toAdd.addIngredient(division[i]);
            	else break;
            }
            
            list.add(toAdd);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void readAll(){
    	do{
    		readRecipe();
    	}while(fileIsReady());
    }

    public boolean fileIsReady(){
        Boolean check = false;
        try {
            check = reader.ready();
        }catch(Exception e){
            System.out.println(e);
        }
        return check;
    }

    public ArrayList<Recipe> getRecipes(){
        return list;
    }
}
