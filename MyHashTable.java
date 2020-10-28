
public class MyHashTable {
	int[] keys;
	int[] values;
	int m; //table size
	int n; //item size
	
	public MyHashTable(int m){
		this.m = m;
		keys = new int[m];
		values = new int[m];
	}
	
	int hash(int key){
		return key % this.m;
	}
	
	void printArray(){
		System.out.println("key array: ");
		for (int i = 0; i < this.m; i++)
			if(keys[i] != 0)
				System.out.print("(" + i + ": " + keys[i] + ") ");
		System.out.println("\nvalue array: ");
		for (int i = 0; i < this.m; i++)
			if(values[i] != 0)
				System.out.print("(" + i + ": " + values[i] + ") ");
	}
	
	//Implement other functions
	void put(int key, int value)
	{
		if ((n + 1) >= m/2) // if the table is half full
		{
			rehashing(); // rehash it 
		}
		// find hashed key
		int hashedkey = hash(key);
		int i = -1;
		int nextSquare = 1;
		while (i != hashedkey)
		{
			// if this is the first i being tested
			if (i == -1)
			{
				// set it equal to hashed key
				i = hashedkey;
			}
			// if keys at i is empty
			if (keys[i] == 0)
			{
				// set it equal to key
				keys[i] = key;
				// set values at i equal to value
				values[i] = value;
				// increment n and break loop
				n++;	
				break;
			}
			// increment i to the next qudratic probe
			i = (i + (nextSquare) * nextSquare++) % m;
		}
	
	}
	int get(int key)
	{
		// hash the key
		int hashedkey = hash(key);
		int nextSquare = 1;
		// while the hashed key exists
		while (keys[hashedkey] != 0)
		{
			// if hashed key is the same as key
			if (keys[hashedkey] == key)
			{
				// return the value at hashed key
				return values[hashedkey];
			}
			// itterate hashed key to the next quadratic probe to account for collisions
			hashedkey = (hashedkey + nextSquare * nextSquare++) % m;
		}
		// this means that the key does not exist within the table so we return 0
		System.out.println("Table does not contain key");
		return 0;
	}
	
	boolean contains(int key)
	{
		// returns a boolean of whether or not it is able to get the key
		return get(key) != 0;
	}
	
	void remove(int key)
	{
		// hash the key
		int hashedkey = hash(key);
		int nextSquare = 1;
		// while the hashed key exists
		while (keys[hashedkey] != 0)
		{
			// if we find the hashed key
			if (keys[hashedkey] == key)
			{
				// set the key and the corresponding value equal to 0
				keys[hashedkey] = 0;
				values[hashedkey] = 0;
				// decrement n
				n--;
			}
			// if there is a collision: quadratically probe next hash value
			hashedkey = (hashedkey + nextSquare * nextSquare++) % m;

		}

	}
	void rehashing()
	{
		// find the next doubled prime number
		m = findPrime(m * 2);
		// set keys array by new m
		keys = new int[m];
		// create values array by new m
		values = new int[m];


	}
	// helper method for findPrime
	// checks if x is a prime number
	boolean prime(int x)
	{
		if (x < 2)
		{
			return false;
		}
		for (int i = 2; i <x ; i++)
		{
			if (x % i == 0)
			{
				return false;
			}
		}
		return true;
	}
	
	int findPrime(int x)
	{
		while(!prime(x))
		{
			x++;
		}
		return x;
	}
	
	
	public static void main(String[] args) {
		MyHashTable hashtable = new MyHashTable(11);
		System.out.println(hashtable.hash(5));
		for(int i = 1; i <= 4; i++)
			hashtable.put(11*i, 11*i*10);
		hashtable.printArray();
		for(int i = 1; i <= 4; i++)
			System.out.println(hashtable.get(11*i));
		
		//when i = 5, rehashing function should be called automatically from put function  
		//because hashtable has 5 items, m/2 = 11/2 = 5
	    //the size of new hashtable should be 23
		for(int i = 5; i <= 10; i++)
			hashtable.put(11*i, 11*i*10);
		 
		hashtable.printArray();
		System.out.println();
		//put into the hashtable with more items
		//you can generate your own version of key-value pairs, do not have to (11*i, 11*i*10)
		System.out.println("TESTING PUT FUNCTION WITHOUT COLLISIONS\nAdding pairs (15,30) and (20,20)");
		hashtable.put(15, 30);
		hashtable.put(20, 20);
		hashtable.printArray();
		System.out.println("\nTESTING GET FUNCTION WITHOUT COLLISIONS");
		System.out.println("Getting value at key 15: " + hashtable.get(15));
		System.out.println("Getting value at key 20: " + hashtable.get(20));
		System.out.println("TESTING PUT FUNCTION WITH COLLISIONS");
		hashtable.put(15, 30);
		hashtable.put(15, 50);
		hashtable.put(20, 20);
		hashtable.put(20, 40);
		hashtable.printArray();

		//note that all keys and values must be nonzero, it is important. 
		//because in this homework, we are assuming 0 as the empty bucket.
		
		//test other functions: remove, contain
		System.out.println("\nTESTING FOR CONTAINS FUNCTION");
		System.out.println("Checking if table contains key 15\n" + hashtable.contains(15));
		System.out.println("Checking if table contains key 20\n" + hashtable.contains(20));
		System.out.println("Checking if table contains key 100\n" + hashtable.contains(100));
		System.out.println("TESTING FOR REMOVE FUNCTION");
		hashtable.remove(20);
		System.out.println("Removing value pair at key 20");
		hashtable.printArray();
		hashtable.remove(15);
		System.out.println("\nRemoving value pair at key 15");
		hashtable.printArray();
	}
}
