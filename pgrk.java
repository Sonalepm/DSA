//SONALE PALLIPARANGATTU MULLEPATTU cs610 1699 prp
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math.*;


public class pgrk1699 {

	public static void main(String[] args)
    {
		
		if(args.length > 3) {
        	System.out.println("Too many arguments !!\n");
            System.out.println("Usage: pgrk1699 iterations initialvalue filename");
            return;
        }
        else if(args.length < 3){
        	System.out.println("Not enough arguments !!\n");
            System.out.println("Usage: pgrk1699 iterations initialvalue filename");
            return;
        }
		
		//Reading command line arguments
        int iterations = Integer.parseInt(args[0]);
        int initialValue = Integer.parseInt(args[1]); 	 
        String filename = args[2];
        int n,m ; 	
    	double iniVal = (double)initialValue;
    	
    	/*Block for validation purpose*/
        /*System.out.println(iterations);
        System.out.println(initialValue);
        System.out.println(filename);*/
    	
       //Read file to get the value of n and m 
        try {
        Scanner inputfile = new Scanner(new File(filename));
        n = inputfile.nextInt();
        m = inputfile.nextInt();
       
        // System.out.println("n = " + n + " m = " + m);
        int N = n ; //total number of pages in corpus
        
        
        switch(initialValue) {
        
        case -1:
        	iniVal =(double) 1/ N;
        	break;        	
        case -2:
        	iniVal =  1/(Math.sqrt(N));
        	break;
        case 0 : 
        	iniVal = 0.0;
        	break;
        case 1:
        	iniVal = 1.0;
        	break;
        default:
        	System.out.println("Initial value can be -2, -1, 0 or 1 . Please enter a valid value.");
            return;
        	    }
        
        int[][] L = new int[N][N];    
        while(inputfile.hasNextInt())
        {
            L[inputfile.nextInt()][inputfile.nextInt()] = 1; 
            
        }
        
        pgrk_1699(iterations,iniVal,filename,L,N);
        
    }
        catch(FileNotFoundException e) {
        	System.err.println("An IOException was caught!");
        }
    }
	
	public static void pgrk_1699(int iterations, double iniVal, String filename, int[][] L, int N) {
		
		int[] C = new int[N];     		 // Number of outgoing links of page Ti
		double[] PR = new double[N];     // Page rank of Ti 
		double[] P = new double[N]; 	 //Stores page rank of nodes. 
		double[] PR_prev = new double[N];
		int Ti = 0;
		int i,v,j,k;		
		int check= 1;
		
		for(i = 0; i < N; i++) {        
            for(j = 0; j < N; j++) {
                C[Ti] += L[i][j];
            }
            Ti++;
        }
		
		for (j=0;j<N;j++)
		{
			PR[j] = iniVal;
		}
		
		/*Block for validation purpose*/
		/*for (Ti=0;Ti<N;Ti++)
		{
			System.out.println("C : "+C[Ti]);
		}
		for (Ti=0;Ti<N;Ti++)
		{
			System.out.printf("PR : %.7f",PR[Ti]);
		}*/
		
			
		if(N<=10)
		{      
			if (iterations < -6)
			{
				System.out.println("Error rate is out of bound!! Please enter iteration values >= -6 ");
				System.exit(0);
			}
		        
			System.out.print("Base: 0  :");
		        for( Ti = 0; Ti <N; Ti++) {
		            System.out.printf("  P[%d]=%.7f",Ti,Math.round(PR[Ti]*1000000.0)/1000000.0);
		        }
			//Loop to find PR(Ti)/C(Ti)
		        if(iterations > 0)
		        {
		        	//The first argument becomes number of iterations as value is positive
		        	//print exactly 'iteration' number of times.
		        	for(i=0;i<iterations;i++)
		        	{
		        		//First initialize all the values to 0.
		        		for(v = 0; v<N; v++) {
		                    P[v] = 0.0;
		                  }
		                
		        			        		
		                  for(j = 0; j < N; j++) {
		                    for(k = 0; k < N; k++)
		                    {
		                        if(L[k][j] == 1) {
		                            P[j] += PR[k]/C[k];
		                        } 
		                    }
		                  }
		                                 
		                  
		                    System.out.printf("\nIter: %d   :",i+1);
		                    for(Ti=0;Ti<N;Ti++)
		                    {
		                    	PR[Ti] = 0.15/N + 0.85 * P[Ti];
		                    	System.out.printf("  P[%d]=%.7f",Ti,Math.round(PR[Ti]*1000000.0)/1000000.0);
		                    }  
		                  
		        	}
		        	System.out.println("\n");
		        }
		        else if(iterations <= 0){
		        	
		        	//The first argument corresponds to error rate as the value is negative or 0.
		        	//Try convergence based on error rate . { Value=Error rate : 0=pow(10,-5) ; -1 = pow(10,-1) ; ........ ; -6 = pow(10,-6)  }
		        	// System.out.println("\nFind convergence ");
		        		i=0;
		        	do {
		        		i++;
		        		for(j = 0; j<N; j++) {
		                    PR_prev[j] = PR[j];
		                  }
						
		        		for(v = 0; v<N; v++) {
		                    P[v] = 0.0;
		                  }
		        		for(j = 0; j < N; j++) {
		                    for(k = 0; k < N; k++)
		                    {
		                        if(L[k][j] == 1) {
		                            P[j] += PR[k]/C[k];
		                        } 
		                    }
		                  }
		        		       		 
		                    System.out.printf("\nIter: %d   :",i);
		                   for(Ti=0;Ti<N;Ti++)
		                    {
		                    	PR[Ti] = 0.15/N + 0.85 * P[Ti];
		                    	System.out.printf("  P[%d]=%.7f",Ti,Math.round(PR[Ti]*1000000.0)/1000000.0);
		                    }
		                                      
		        		check = differencecheck(PR_prev,PR,N,iterations);						
		        	}while(check==1);
		        	
		        }
		        System.out.println("\n");
		}
		else if (N > 10) {
			// large graph 
			//iteration value and initial value are set to 0 and -1
			iterations = 0 ;
        	iniVal = (double) 1/ N ;
			
        	for (j=0;j<N;j++)
    		{
    			PR[j] = iniVal;
    		}
        	//Iteration = 0 .Use convergence method.
        	i=1;
        	do {
        		 
        		for(j = 0; j<N; j++) {
                    PR_prev[j] = PR[j];
                  }
				
        		for(v = 0; v<N; v++) {
                    P[v] = 0.0;
                  }
        		for(j = 0; j < N; j++) {
                    for(k = 0; k < N; k++)
                    {
                        if(L[k][j] == 1) {
                            P[j] += PR[k]/C[k];
                        } 
                    }
                  }
        		       		 
                  
                   for(Ti=0;Ti<N;Ti++)
                    {
                    	PR[Ti] = 0.15/N + 0.85 * P[Ti];
                    	
                    }
               
                                      
        		check = differencecheck(PR_prev,PR,N,iterations);
        		i++;
				
        	}while(check==1);
        	System.out.printf("Iter:  %d\n",i );
        	for(Ti = 0; Ti < N; Ti++) {
                System.out.printf(" P[%d]=%.7f\n",Ti,Math.round(PR[Ti]*1000000.0)/1000000.0); 
            }
        	
		}
		System.out.println("\n");
	}
	
	public static int differencecheck(double[] PR_prev,double[] PR,int N,int iterations) {
		double errorRate = Math.pow(10,-5); ; //default error rate
		
		if(iterations<0)
			{ 
			errorRate = Math.pow(10,iterations); 
			}
		else if (iterations == 0)
			{
			errorRate = Math.pow(10,-5);
			}		
		
		for(int i = 0 ; i < N; i++) {
			
			if((Math.abs(PR[i]-PR_prev[i])) > errorRate)
	           {
	        	   return 1;
	           }
	           
	       }
		return 0;
		}
}
