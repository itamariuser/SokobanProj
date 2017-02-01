package boot;

import client.CLIClient;

public class Run {
	
	public static void main(String args[])
	{
		CLIClient.start(args[0], Integer.parseInt(args[1]));
	}

}
