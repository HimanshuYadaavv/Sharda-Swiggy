package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

public class App {
    User user;
    Dish[] dishList=new Dish[18];
    Restro[] restroList=new Restro[6];

    Location[] locationList=new Location[18];


    App()
    {
        Location userLocation = new Location("000",50,67);
        this.user = new User("Himanshu Yadav",userLocation);
    }

    public User getUser() {
        return user;
    }

    public void parseRestroData() throws IOException{
        BufferedReader restroData=Files.newBufferedReader(Paths.get("C:\\Users\\DELL\\IdeaProjects\\shardaswiggy\\data\\restro.csv"));
        String lines;
        for(int restcnt=0;(lines=restroData.readLine())!=null;restcnt++){
            String[] data=lines.split(",");
            restroList[restcnt]=new Restro(data[0],data[1],data[2]);
            Dish[] tempMenu=new Dish[3];
            Location tempLocation=null;
            for(int menucnt=0,localcnt=0;(menucnt< dishList.length)&&(dishList[menucnt]!=null);menucnt++){
                if(dishList[menucnt].getRestroID().equals(data[0])) {
                    tempMenu[localcnt] = dishList[menucnt];
                    localcnt++;
                }
            }
            restroList[restcnt].setMenu(tempMenu);

            for(int locationcnt=0;(locationcnt<locationList.length)&&(locationList[locationcnt]!=null);locationcnt++){
                if(locationList[locationcnt].getRestroID().equals(data[0])){
                    tempLocation=locationList[locationcnt];
                }
            }
            restroList[restcnt].setLocation(tempLocation);
        }

    }
    public void parseDishData() throws IOException{
        BufferedReader dishData=Files.newBufferedReader(Paths.get("C:\\Users\\DELL\\IdeaProjects\\shardaswiggy\\data\\dish.csv"));
        String lines;
        for(int dishcnt=0;(lines=dishData.readLine())!=null;dishcnt++){
            String[] data = lines.split(",");
            dishList[dishcnt]=new Dish(data[0],data[1],data[2],Integer.parseInt(data[3]));
        }

    }
    public void parseLocationData() throws IOException{
        BufferedReader locationData=Files.newBufferedReader(Paths.get("C:\\Users\\DELL\\IdeaProjects\\shardaswiggy\\data\\location.csv"));
        String lines;
        for(int locationcnt=0;(lines=locationData.readLine())!=null;locationcnt++){
            String[] data = lines.split(",");
            locationList[locationcnt]=new Location(data[0],Float.parseFloat(data[1]),Float.parseFloat(data[2]));
        }
    }

    public double deliveryTime(Location userLocation, Location restroLocation){
        double diff=Math.abs(Math.pow((restroLocation.getLatitude()-userLocation.getLatitude()),2))+Math.abs(Math.pow((restroLocation.getLongitude()-userLocation.getLongitude()),2));
        return Math.sqrt(diff);
    }


    public void browse() throws NullPointerException {
        System.out.println("**********************************************************************");
        System.out.println("Please choose Dishes from the Following Menu");

        int restrocount=1;
        int dishcount=1;

        for(Restro r:restroList){
            System.out.println("**********************************************************************");
            Dish[] tempMenu=r.getMenu();
            System.out.println(restrocount + ": "+ r.getRestroName() + "Location: "+ r.getRestroAddress()+"\tExpected Delivery Time: " + (int)deliveryTime(this.user.getLocation(),r.getLocation())+" mins");
            for(Dish d:tempMenu){
                System.out.println("\t" + restrocount + "." + dishcount + " " + d.getDishName()+ " "+d.getPrice());
                dishcount++;
            }
            dishcount=1;
            restrocount++;
        }
    }

    public static void main(String []args) throws IOException, InvalidPathException {
        App swiggyApp=new App();
        swiggyApp.parseLocationData();
        swiggyApp.parseDishData();
        swiggyApp.parseRestroData();
        System.out.println("**********************************************************************");
        System.out.println("Hey " +swiggyApp.getUser().getUserName()+" Welcome to our Swiggy App ");
        System.out.println("How would you like to Order ? \n 1 - From the Menu  \n 2 - Search for Dish");
        Scanner sc=new Scanner(System.in);
        System.out.println("Your Choice:");
        int choice=sc.nextInt();
        if(choice==1){
            swiggyApp.browse();
        }
        else if(choice==2){
            System.out.println("Not Avalible");
        }
        else{
            System.out.println("Invalid Input");
        }

    }
}

