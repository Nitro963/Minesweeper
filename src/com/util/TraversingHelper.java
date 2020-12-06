package com.util;

import java.io.Serializable;
import java.util.ArrayList;

public class TraversingHelper implements Serializable{

	private static final long serialVersionUID = -6073684041833232327L;
	private final int ar[] = {1 , 0, -1 ,0 ,1 ,-1 ,1 ,-1};
    private final int ac[] = {0 ,1 ,0 ,-1 ,1 ,-1 ,-1 ,1}; 
    private boolean vis[][];
    private int n ,m;

    public TraversingHelper(int n, int m) {
		super();
		this.n = n;
		this.m = m;
		vis = new boolean[n + 5][m + 5];
	}
    
    public void  reset() {
    	for(int i = 0 ; i < n ; i++)
    		for(int j = 0 ; j < m ; j++)
    			vis[i][j] = false;
    }
	
    public boolean isValid(int x , int y){
            return x < n && y < m && x >= 0 && y >= 0;
    }
	    
    public final ArrayList<Pair> getNeighbours(int x ,int y) {
		ArrayList<Pair> neighbours = new ArrayList<>();
    	for (int i = 0; i < 8; i++) {
			int nx = x + ar[i];
			int ny = y + ac[i];
			if (isValid(nx, ny))
				neighbours.add(new Pair(nx ,ny));
    	}
    	return neighbours;
	}

    public boolean isVis(int i ,int j) {
        return vis[i][j];
    }
    
    public void markVis(int i ,int j){
        vis[i][j] = true;
    }
 
    public void unmarkVis(int i ,int j) {
    	vis[i][j] = false;
    }
}