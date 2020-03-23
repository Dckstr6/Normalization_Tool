import java.lang.*;
import java.util.*;


//  GO TO LAST FOR ALL DATA STRUCTURE REFERENCES
class Normal{
	String s1;
	String s2;
	Vector<String>ckey;
	Vector<String>skey;
	int prime_attributes[];

	Normal(String s1,String s2,Vector<String>ckey,Vector<String>skey,int prime_attributes[]){
		this.s1 = s1;
		this.s2 = s2;
		this.ckey = ckey;
		this.skey = skey;
		this.prime_attributes = prime_attributes;
	}

	int check_2nf(String s1,String s2,Vector<String>ckey,int prime_attributes[]){
		//System.out.println("Checking for 2NF");
		int s1_flag = 0;
		for(int i=0;i<ckey.size();i++){
			if(s1.equals((ckey.get(i)).toString())==true){
			//	System.out.println("Match Found in ckey");
				s1_flag = 1;
				break;
			}
		}
		if(s1_flag==1){
		//	System.out.println("IN 2NF");
			return 1;
		}
		int s2_flag = 0;
		for(int i=0;i<s2.length();i++){
			if(prime_attributes[(int)(s2.charAt(i)-'a')]==0){
			//	System.out.println(s2+ " not prime attributes");
				s2_flag = 1;
				break;
			}
		}
		if(s2_flag==0){
		//	System.out.println("IN 2NF");
			return 1;
		}
		s1_flag = 0;
		for(int i=0;i<s1.length();i++){
			if(prime_attributes[(int)(s1.charAt(i)-'a')]==0){
			//	System.out.println("Not subset of ckey");
				s1_flag = 1;
			}
		}
		if(s1_flag==1){
		//	System.out.println("IN 2NF");
			return 1;
		}
		return 0;
	}
	int check_3nf(String s1,String s2,Vector<String>skey,int prime_attributes[]){
	//	System.out.println("Checing 3nf");
		int t_flag = 0;
		for(int i=0;i<skey.size();i++){
			if(s1.equals((skey.get(i)).toString())==true){
			//	System.out.println("Found match in skey");
				t_flag = 1;
				break;
			}
		}
		if(t_flag==1){
		//	System.out.println("In 3nf");
			return 1;
		}
		int s2_flag = 0;
		for(int i=0;i<s2.length();i++){
			if(prime_attributes[(int)(s2.charAt(i)-'a')]==0){
			//	System.out.println("Not prime attr");
				s2_flag = 1;
				break;
			}
		}
		if(s2_flag==0){
		//	System.out.println("In 3nf");
			return 1;
		}
	//	System.out.println("NOt 3nf");
		return 0;
	}
	int check_bcnf(String s1,String s2,Vector<String>skey){
		int t_flag = 0;
		for(int i=0;i<skey.size();i++){
			if(s1.equals((skey.get(i)).toString())==true){
				//System.out.println("Found match in skey");
				t_flag = 1;
				break;
			}
		}
		if(t_flag==1){
			//System.out.println("IN BCNF");
			return 1;
		}
		//System.out.println("Not in bcnf");
		return 0;
	}
}
 class obj1{
	String str;
	int visited[];
	int check[];
	int global_flag = 0;
	int result;
	Vector<String>left;
	Vector<String>right;
	public obj1(String str,int visited[],int check[],Vector<String>left,Vector<String>right){
		this.str = str;
		this.visited = visited;
		this.check = check;
		this.left = left;
		this.right = right;
		init(visited);
		init(check);
		global_flag = 0;
		result = start(str,visited,check,left,right);
	}
	public void init(int arr[]){
		for(int i=0;i<26;i++){
			arr[i] = 0;
		}
	}
	public void change(char ch,int visited[],int check[],Vector<String>left,Vector<String>right){
		check[(int)(ch-'a')] = 1;
	//	System.out.println("Setting Check Array");
		// for(int i=0;i<26;i++){
		// 	System.out.print(check[i]+" ");
		// }
		int temp_flag = 0;
		int row = 0;
		int index = 0;
		for(int i=0;i<right.size();i++){
			int flag = 0;
			String b = (right.get(i)).toString();
			for(int j=0;j<b.length();j++){
				if(b.charAt(j)==ch){
					flag = 1;
					row = i;
					index = j;
					break;
				}
			}
			if(flag==1){
				break;
			}
		}
		//System.out.println("Found "+ch+" at "+row+" "+index);
		String a = (left.get(row)).toString();
		//System.out.print("Corresesponding left "+a);
		//System.out.println(" ");
		for(int i=0;i<a.length();i++){
			if(visited[(int)(a.charAt(i)-'a')]==0){
				temp_flag = 1;
			//	System.out.println(a.charAt(i)+" is also not visited");
				if(check[(int)(a.charAt(i)-'a')]==1){
				//	System.out.println(a.charAt(i)+" has check==1. Hecnce fucked");
					global_flag = 1;
					return;
				}
				else{
					//System.out.println("Calling change on "+a.charAt(i));
					change(a.charAt(i),visited,check,left,right);
				}
			}
		}
		if(temp_flag==0 && global_flag==0){
			//System.out.println("Full left is visited, Hence right also visit for "+ch);
			visited[(int)(ch-'a')] = 1;
		}
		return;
	}
	public int iterate(String str,int visited[],int check[],Vector<String>left,Vector<String>right){
		int temp_flag = 0;
		for(int i=0;i<left.size();i++){
			String a = (left.get(i)).toString();
		//	System.out.println("Checking left fd: "+a);
			for(int j=0;j<a.length();j++){
				if(visited[(int)(a.charAt(j)-'a')]==0){
					if(check[(int)(a.charAt(j)-'a')]==0){
					//	System.out.println(a.charAt(j)+" not visited yet,hence calling check");
						change(a.charAt(j),visited,check,left,right);
					}
					if(global_flag==1){
						return 0;
					}
				}
			}
			for(int j=0;j<a.length();j++){
				if(visited[(int)(a.charAt(j)-'a')]==0){
					temp_flag = 1;
					break;
				}
			}
			if(temp_flag==0){
				String b = (right.get(i)).toString();
				//System.out.println("Setting "+b+" visited to 1");
				for(int j=0;j<b.length();j++){
					visited[(int)(b.charAt(j)-'a')] = 1;
				}
			}
		}
		return 1;
	}
	public int start(String str,int visited[],int check[],Vector<String>left,Vector<String>right){
		int flag = 0;
		//System.out.println("initial visited:");
		// for(int i=0;i<26;i++){
		// 	System.out.print(visited[i]+" ");
		// }
		for(int i=0;i<str.length();i++){
			visited[(int)(str.charAt(i)-'a')] = 1;
		}
	//	System.out.println("Edited Visited:");
		return iterate(str,visited,check,left,right);
	}
}


public class test{
	public static void init(int visited[]){
		for(int i=0;i<26;i++){
			visited[i] = 0;
		}
	}
	public static String sortString(String inputString) 
    { 
        // convert input string to char array 
        char tempArray[] = inputString.toCharArray(); 
          
        // sort tempArray 
        Arrays.sort(tempArray); 
          
        // return new sorted string 
        return new String(tempArray); 
    } 
	public static void main(String args[]) throws Exception{
		Scanner scan = new Scanner(System.in);
		String total = scan.nextLine(); 		 // Scanning All Attributes' string
		String[] total_array = total.split(","); // Splitting the string on the basis of , and storing in an array of strings
		String total_attr="";
		for(int i=0;i<total_array.length;i++){
			total_attr = total_attr + total_array[i];
		}
		int arr[] = new int[26];

		for(int i=0;i<26;i++){
			arr[i] = -1; 						// initializing whole array to -1 (this is just an array to indicate essential attr)
		}

		int arr1[] = new int[26];

		for(String a : total_array){
			char ch = a.charAt(0);
			arr[ch-97] = 0;     				// initializing all present attributes to 0
		}

		for(int i=0;i<26;i++){
			arr1[i] = arr[i];   				 // duplicating array
		}

		Vector<String>left = new Vector<String>();    // vector storing leftside of each FD
		Vector<String>right = new Vector<String>();   // vector storing rightside of each FD

		for(int i=0;i<4;i++){
			String temp = scan.nextLine();							// Scanning the FDs
			StringTokenizer str = new StringTokenizer(temp," ");    // Using StringTokenizer to split LHS and RHS by " ".
			String temp_left = sortString(str.nextToken());
			String temp_right = sortString(str.nextToken());
			if(temp_left.equals(temp_right)==false){
				left.add(temp_left);    							
				right.add(temp_right);
			}								
		}

		for(int k=0;k<right.size();k++){
			String temp = (right.get(k)).toString();	// taking RHS of each FD
			for(int j=0;j<temp.length();j++){		
				char ch = temp.charAt(j);				// Taking each character of RHS
				arr1[ch-97] = 1;						// editing temp array to find essential attributes
			}		
		}

		String essential = "";							// empty string for essential attributes
		for(int i=0;i<26;i++){
			if(arr1[i]==0){
				char ch = (char)('a' + i);				// appending character to essential attribute string.
				essential = essential + ch;
			}
		}
		String non_ess = "";						// Initializing empty String for non-essential attributes.
		for(int i=0;i<26;i++){
			if(arr1[i]==1){							// checking condition for non-essential attributes.
				char ch = (char)('a'+i);
				non_ess = non_ess + ch;				// appending character to non-ess string.
			}
		}

		System.out.println("essential : "+essential);
		System.out.println("Non-Essential: "+non_ess);

		int length_non_ess = non_ess.length();											// From this point
		int temp = 1;
		int temp1;																		//
		String temp_arr[] = new String[(length_non_ess*(length_non_ess+1)/2)+1];
		temp_arr[0] = essential;
		for(int i=0;i<length_non_ess;i++){												//
			for(int j=i;j<length_non_ess;j++){											//
				temp_arr[temp] = non_ess.substring(i,j+1);								//
				temp++;																	//
			}																			//
		}			
																		// Copied from internet(to find all subsets)
		for(int i=1;i<temp;i++){
			temp_arr[i] = temp_arr[i] + essential;										// adding essential attributes to the sets.
		}
		// temp_Arr contains all sets

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* WHAT CONTAINS WHAT:
	---	------------------------------------------------------------------------------------------------------------------------
	|	1) total_attr string : contains all attributes
	|	2) essential string : contains essential attributes
	|	3) non-ess string : contains non-essential attributes
	|	4) int arr[] : contains -1 or 0 indicating which attributes are present(0) out of 26 alphabets
	|	5) int arr1[] : contains -1 , 0 ,1 indicating non-attributes , non-essential attributes and essential attributes respectively
	|	6) temp_arr[] : array of strings. Contains all the sets of combinations of non-essential attributes and essential attributes like abc,abd,abe,abcd,abce,abde etc (here ab are essential attributes)
	|	7) Vector left : contains left side of functional dependencies (Stored as Strings).
	|	8) Vector right : contains right side of functional dependencies (Stored as Strings).
		9) Vector skey : contains all skeys
		10) Vector ckey : contains all ckeys
		11) int max_normal[] : max normal forms of all fds
	-----------------------------------------------------------------------------------------------------------------------------

		*/
	///////////////////////////////////////// Finding prime attributes and ckeys /////////////////////////////////////////////////////
		int visited[] = new int[26];
		visited[0] = 1;
		int check[] = new int[26];
		Vector<String>skey = new Vector<String>();
		Vector<String>ckey = new Vector<String>();
		for(int i=0;i<temp_arr.length;i++){
			obj1 obj = new obj1(temp_arr[i],visited,check,left,right);
			if(obj.global_flag==0){
				skey.add(temp_arr[i]);
			}
		}
	//	System.out.println("\n");
	//	System.out.println("\n");
		
		int visited1[] = new int[26];
		int visited2[] = new int[26];
		int removed[] = new int[skey.size()];
		for(int i=0;i<skey.size();i++){
			removed[i] = 0;
		}
		for(int i=0;i<skey.size();i++){
			if(removed[i]==0){
				for(int j=0;j<26;j++){
					visited1[j] = 0;
				}
				String s = (skey.get(i)).toString();
			//	System.out.println("Taken "+s+" as reference");
				for(int j=0;j<s.length();j++){
					visited1[(int)(s.charAt(j)-'a')] = 1;
				}
				for(int j=i+1;j<skey.size();j++){
					for(int k=0;k<26;k++){
						visited2[k] = 0;
					}
					int ckey_flag = 0;
					String s2 = (skey.get(j)).toString();
				//	System.out.println("Taken "+s2+" to check with "+s);
					for(int k=0;k<s2.length();k++){
						visited2[(int)(s2.charAt(k)-'a')] = 1;
					}

					for(int k=0;k<26;k++){
						if(visited1[k]==1 && visited2[k]==0){
							ckey_flag = 1;
							break;
						}
					}
					if(ckey_flag==0){
					//	System.out.println("Setting removed for "+s2);
						removed[j] = 1;
					}
				}
			}
		}
		for(int i=0;i<skey.size();i++){
			if(removed[i]==0){
				ckey.add(skey.get(i));
			}
		}
		// System.out.println("\n");
		// System.out.println("\n");
		for(int i=0;i<skey.size();i++){
			String skey_temp = skey.get(i);
			String skey_final = sortString(skey_temp);
			skey.set(i,skey_final);
		}
		for(int i=0;i<ckey.size();i++){
			String ckey_temp = ckey.get(i);
			String ckey_final = sortString(ckey_temp);
			ckey.set(i,ckey_final);
		}
		System.out.println("SKEY:");
		for(int i=0;i<skey.size();i++){
			System.out.println(skey.get(i));
		}
		System.out.println("CKEY");
		for(int i=0;i<ckey.size();i++){
			System.out.println(ckey.get(i));
		}
		int prime_attributes[] = new int[26];
		init(prime_attributes);
		for(int i=0;i<ckey.size();i++){
			String ck = (ckey.get(i)).toString();
			for(int j=0;j<ck.length();j++){
				prime_attributes[(int)(ck.charAt(j)-'a')] = 1;
			}
		}
	
		// At this point, we have all super keys, candidate keys and prime attributes and non-prime attributes.

		//////////////////////////////////////////////////////// Finding Normal Forms /////////////////////////////////////////////////

		int max_normal[] = new int[left.size()];
		for(int i=0;i<left.size();i++){
			max_normal[i] = 1;
		}

		for(int i=0;i<left.size();i++){
			String left_fd = (left.get(i)).toString();
			String right_fd = (right.get(i)).toString();

		//	System.out.println("Checking for "+left_fd+" and "+right_fd);

			Normal obj2 = new Normal(left_fd,right_fd,ckey,skey,prime_attributes);
			if(obj2.check_2nf(left_fd,right_fd,ckey,prime_attributes)==1){
			//	System.out.println("Setting 2");
				max_normal[i]  = 2;
			}
			if(obj2.check_3nf(left_fd,right_fd,skey,prime_attributes)==1){
			//	System.out.println("Setting 3");
				max_normal[i] = 3;
			}
			if(obj2.check_bcnf(left_fd,right_fd,skey)==1){
			//	System.out.println("Setting 4");
				max_normal[i] = 4;
			}
		}
		System.out.println(" ");
		for(int i=0;i<left.size();i++){
			System.out.print(max_normal[i]+" ");
		}
		// Now we have max_normal forms of all fds in max_normal array

		
		int normal_form = max_normal[0];
		for(int i=1;i<left.size();i++){
			if(max_normal[i]<normal_form){
				normal_form = max_normal[i];
			}
		}
		System.out.println("The Normal Form: "+normal_form);

		/////////////////////////////////////////////////////Decomposition into x+1////////////////////////////////////////////////////////////////
		
		

	}
}