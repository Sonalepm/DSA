// SONALE PALLIPARANGATTU MULLEPATTU cs610 1699 prp
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math.*;

public class hits1699 {

	public static void main(String[] args)
    {
        if(args.length > 3) {
        	System.out.println("Too many arguments !!\n");
            System.out.println("Usage: hits1699 iterations initialvalue filename");
            return;
        }
        else if(args.length < 3){
        	System.out.println("Not enough arguments !!\n");
            System.out.println("Usage: hits1699 iterations initialvalue filename");
            return;
        }
        
        //Reading command line arguments
        int iterations = Integer.parseInt(args[0]);
        int initialValue = Integer.parseInt(args[1]); 	 
        String filename = args[2];
        int n,m ; 	//number of vertices and edges
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
        
        /*Line for validation purpose*/
        /*System.out.println("n = " + n + " m = " + m);*/
        
        
        int N = n ; //number of vertices in the graph
        
        
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
        	System.out.println("Please enter a valid value in the range {0,1,-1,-2}");
            return;
        	    }
              
        int[][] L = new int[N][N];    
        while(inputfile.hasNextInt())
        {
            L[inputfile.nextInt()][inputfile.nextInt()] = 1;             
        }
        
        /*Block for validation purpose*/
        /*for(i = 0; i < N; i++) {
            System.out.println();
            System.out.print(i + ": ");
            for(j = 0; j < N; j++)
              System.out.print(L[i][j] + " ");
    	}*/
        
        hits_1699(iterations,iniVal,filename,L,N);	//Method that performs the HITS algorithm
        
            }catch(FileNotFoundException e) {
            	System.err.println("An IOException was caught!");
            }        
}

	public static void hits_1699(int iterations, double iniVal, String filename, int[][] L, int N) {	

		double[] auth = new double[N];	//authority vector
        double[] hub = new double[N];	//hub vector
        double[] prevAuth = new double[N];	//authority vector of previous iteration
        double[] prevHub = new double[N];	//hub vector of previous iteration
        double errorRate = Math.pow(10, -5);
        double Auth_scalingFactor =0.0;
        double Auth_sumOfSquare = 0.0;
        double Hub_scalingFactor =0.0;
        double Hub_sumOfSquare = 0.0;
        int check= 1;
        int counter= 0;
        int i,j,p,q,l,w;
        
        if(N <= 10)
        {        	
        	//Initialize the values 
        	for(i=0;i<N;i++) {

            	prevAuth[i] = iniVal;
            	prevHub[i] = iniVal;
            	auth[i] = iniVal;
            	hub[i] = iniVal;
            	}
        	
        	/*Block for validation purpose*/
        	/*for(i=0;i<N;i++) {          	
            	System.out.println(prevAuth[i]);   // print only up to 7 decimal values.
            	}
        	System.out.println("--------------------------------------------------------------");
        	for(i=0;i<N;i++) {          	
            	System.out.println(prevHub[i]);   // print only up to 7 decimal values.
            	}
                System.out.println("--------------------------------------------------------------");
        	for(i=0;i<N;i++) {          	
            	System.out.println(auth[i]);   // print only up to 7 decimal values.
            	}
        	System.out.println("--------------------------------------------------------------");
        	for(i=0;i<N;i++) {          	
            	System.out.println(hub[i]);   // print only up to 7 decimal values.
            	}*/
        	/*System.out.println("--------------------------------------------------------------");
        	for(i = 0; i < N; i++) {
                System.out.println();
                System.out.print(i + ": ");
                for(j = 0; j < N; j++)
                  System.out.print(L[i][j] + " ");
        	}
        	System.out.println("--------------------------------------------------------------");*/
        	
        	//check value of iteration .If < -6 , exit. 
        	 if (iterations < -6)
        	 {
        		System.out.println("Error rate is out of bound!! Please enter iteration values >= -6 ");
 		    	System.exit(0);
        	 }
        	 
        	 System.out.print("Base:  0 :");
             for(i = 0; i < N; i++) {
               System.out.printf(" A/H[%d] = %.7f/%.7f",i,Math.round(prevAuth[i]*1000000.0)/1000000.0,Math.round(prevHub[i]*1000000.0)/1000000.0); 
             }
             
             
           //check value of iteration           
             if(iterations > 0)
             {
            	 //print exactly 'iterations' times               	
              	
            	 for(i=0;i<iterations;i++)
            	 {
            		 for(j = 0; j < N; j++) {
                         auth[j] = 0.0;                         
                     }
            		 for(p=0;p<N;p++)
            		 {
            			 for(q=0;q<N;q++)
            			 {       
            				 
            				 auth[p]  +=L[q][p] * hub[q]; 
            				
            			 }
            		 }
            		
            		 for(j = 0; j < N; j++) {
                         hub[j] = 0.0;
                     }
            		 for(p=0;p<N;p++)
            		 {
            			 for(q=0;q<N;q++)
            			 {          				
            				 hub[p]  += L[p][q] * auth[q];
            			 }
            		 }
            		 
            		
                 	
                 	//Scale            		 
            		 Auth_scalingFactor = 0.0;
            		 Auth_sumOfSquare  = 0.0;
            		 for(l = 0; l < N; l++) {
            			 Auth_sumOfSquare  += Math.pow(auth[l],2);    
                     }
                     Auth_scalingFactor =  Math.sqrt(Auth_sumOfSquare); 
                     for(l = 0; l < N; l++) {
                         auth[l] = auth[l]/Auth_scalingFactor;
                        
                     } 
                     
                     Hub_scalingFactor = 0;
            		 Hub_sumOfSquare  = 0;
            		 for(l = 0; l < N; l++) {
            			 Hub_sumOfSquare  += Math.pow(hub[l],2);    
                     }
                     Hub_scalingFactor =  Math.sqrt(Hub_sumOfSquare); 
                     for(l = 0; l < N; l++) {
                         hub[l] = hub[l]/Hub_scalingFactor;
                     } 
                     
                     System.out.println();
                     System.out.printf("Iter: %d :",i+1);
                     for(w = 0; w < N; w++) {
                    	 
                         System.out.printf(" A/H[%d] = %.7f/%.7f",w,Math.round(auth[w]*1000000.0)/1000000.0,Math.round(hub[w]*1000000.0)/1000000.0); 
                     }       
              	 }
             }
             
             else if (iterations <= 0 && iterations >= -6)
             {
            	//The first argument corresponds to error rate as the value is negative or 0.
            	//Try convergence based on error rate . { Value=Error rate : 0=pow(10,-5) ; -1 = pow(10,-1) ; ........ ; -6 = pow(10,-6)  }
            	 
            	 //calculate till difference is less than errorRate       
            	
            	 do {
            		 counter++;
            		 for(int h=0;h<N;h++)
            		 {
            			 prevAuth[h] = auth[h];
            			 prevHub[h] = hub[h];
              		 }

            		 for(j = 0; j < N; j++) {
                         auth[j] = 0.0;
                     }
            		 for(p=0;p<N;p++)
            		 {
            			 for(q=0;q<N;q++)
            			 {         				 
            				 auth[p]  += L[q][p] * hub[q]; 
            			 }
            		 }
            		
            		 for(j = 0; j < N; j++) {
                         hub[j] = 0.0;
                     }
            		 for(p=0;p<N;p++)
            		 {
            			 for(q=0;q<N;q++)
            			 {  
            				 hub[p]  += L[p][q] * auth[q];
            			 }
            		 }
            		 
                 	//Scale            		 
            		 Auth_scalingFactor = 0;
            		 Auth_sumOfSquare  = 0;
            		 for(l = 0; l < N; l++) {
            			 Auth_sumOfSquare  += Math.pow(auth[l],2);    
                     }
                     Auth_scalingFactor =  Math.sqrt(Auth_sumOfSquare); 
                     for(l = 0; l < N; l++) {
                         auth[l] = auth[l]/Auth_scalingFactor;
                        
                     } 
                     
                     Hub_scalingFactor = 0;
            		 Hub_sumOfSquare  = 0;
            		 for(l = 0; l < N; l++) {
            			 Hub_sumOfSquare  += Math.pow(hub[l],2);    
                     }
                     Hub_scalingFactor =  Math.sqrt(Hub_sumOfSquare); 
                     for(l = 0; l < N; l++) {
                         hub[l] = hub[l]/Hub_scalingFactor;
                     } 
                     
                     System.out.println();
                     System.out.printf("Iter:  %d :",counter);
                     for(w = 0; w < N; w++) {
                         System.out.printf(" A/H[%d] = %.7f/%.7f",w,Math.round(auth[w]*1000000.0)/1000000.0,Math.round(hub[w]*1000000.0)/1000000.0); 
                     }
                     check = differencecheck(auth,prevAuth,hub,prevHub,N,iterations);
            	 }while(check ==1 );
             }
             
        }
        else {  										
        	//N greater than 10 and is expected to be less than 1,000,000
        	//Iterations and initial value set to a default value.
        	//calculate till difference is less than errorrate
        	//only print  last iteration
        	
        	int c= 0;
        	double iniValue = 0;
        	iterations = 0 ;
        	iniValue = (double) 1/ N ;
        	errorRate = Math.pow(10, -5);
        	
        	for(i=0;i<N;i++) {
            	prevAuth[i] = iniValue;
            	prevHub[i] = iniValue;
            	auth[i] = iniValue;
            	hub[i] = iniValue;
            	}
        	
        	/*Block for validation purpose*/
        	/*for(i=0;i<N;i++) {
            	System.out.println(prevAuth[i]);   // print only upto 7 decimal values.
            	}
        	System.out.println("--------------------------------------------------------------");
        	for(i=0;i<N;i++) {            	
            	System.out.println(prevHub[i]);   // print only upto 7 decimal values.
            	}
        	System.out.println("--------------------------------------------------------------");
        	for(i = 0; i < N; i++) {
                System.out.println();
                System.out.print(i + ": ");
                for(j = 0; j < N; j++)
                  System.out.print(L[i][j] + " ");
        	}*/
        	
        	
       	 do {
       		 c++;
       		 for(int h=0;h<N;h++)
       		 {
       			 prevAuth[h] = auth[h];
       			 prevHub[h] = hub[h];
         		 }

       		 for(j = 0; j < N; j++) {
                    auth[j] = 0.0;
                }
       		 for(p=0;p<N;p++)
       		 {
       			 for(q=0;q<N;q++)
       			 {
       				 auth[p]  += L[q][p]*hub[q]; 
       			 }
       		 }
       		
       		 for(j = 0; j < N; j++) {
                    hub[j] = 0.0;
                }
       		 for(p=0;p<N;p++)
       		 {
       			 for(q=0;q<N;q++)
       			 { 
       				 hub[p]  += L[p][q]*auth[q];
       			 }
       		 }    
       		 
            //Scale 
       		 Auth_scalingFactor = 0;
       		 Auth_sumOfSquare  = 0;
       		 for(l = 0; l < N; l++) {
       			 Auth_sumOfSquare  += Math.pow(auth[l],2);    
                }
                Auth_scalingFactor =  Math.sqrt(Auth_sumOfSquare); 
                for(l = 0; l < N; l++) {
                    auth[l] = auth[l]/Auth_scalingFactor;                  
                }                
             Hub_scalingFactor = 0;
       		 Hub_sumOfSquare  = 0;
       		 for(l = 0; l < N; l++) {
       			 Hub_sumOfSquare  += Math.pow(hub[l],2);    
                }
                Hub_scalingFactor =  Math.sqrt(Hub_sumOfSquare); 
                for(l = 0; l < N; l++) {
                    hub[l] = hub[l]/Hub_scalingFactor;
                } 
               check = differencecheck(auth,prevAuth,hub,prevHub,N,iterations);
       	 }while(check == 1);
       	 
       	System.out.printf("Iter:  %d\n",c );
       	for(w = 0; w < N; w++) {
            System.out.printf(" A/H[%d] = %.7f/%.7f\n",w,Math.round(auth[w]*1000000.0)/1000000.0,Math.round(hub[w]*1000000.0)/1000000.0); 
        }
        }
		
	}
	
	//Method which checks the difference 
	public static int differencecheck(double[] auth,double[] prevAuth,double[] hub,double[] prevHub,int N,int iterations) {
		double errorRate = Math.pow(10,-5);  //default error rate
		
		    if(iterations<0)
			{ 
			errorRate = Math.pow(10,iterations); 
			}
		    else if (iterations == 0)
			{
			errorRate = Math.pow(10,-5);
			}	
		    
		for(int i = 0 ; i < N; i++) {		
			 
	           if((Math.abs(auth[i]-prevAuth[i]) > errorRate) )
	           {
	        	   return 1;
	           }
	           else if ((Math.abs(hub[i]-prevHub[i]) > errorRate))
	           {
	        	   return 1;
	           }
	       }
		return 0;
		}
}
