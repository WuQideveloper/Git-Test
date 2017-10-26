package e1;

import java.util.Scanner;

public class Q1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       Scanner s = new Scanner(System.in);
       String b;
       int c,d,e=0,f;
       while(true){
    	   e=0;
       System.out.println("Please Input:");
      
    	   b = s.nextLine();
    	   c = b.length();
    	   for(int i = 0; i< c; i++){
    		   char letter = b.charAt(i);
    		   if(letter != 'a' && letter !='b'){
    			   e = -1;
    			   break;
    		   }
    		   if(letter == 'b'){
    			   e++;
    	   }
      	}
    	   f = e%2;
    	   if(f == 0){
    		   System.out.println("No");
    	   }else if(f == 1){
    		   System.out.println("Yes");
    	   }else{
    		   System.out.println("fault");
    	   }
       }
	} 
	
}