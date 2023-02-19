// UNTESTED since 2019

void dfsFindSize(int i, int par) {
	size[i] = 1;
	for(int j : adj[i]) {
		if(j != par && isActive[j]) {
			dfsFindSize(j, i);
			size[i] += size[j];
		}
	}
}
void decompose(int i, int curlevel) {
	dfsFindSize(i, -1);
	int curCentroid = i, curpar = -1;
	boolean foundCentroid = false;
	while(!foundCentroid) {
		boolean centroidFlag = true;
		int possibleCentroid = -1;
		for(int j : adj[curCentroid]) {
			if(j != curpar && isActive[j]) {
				if(size[j] > size[i] / 2) {
					centroidFlag = false; possibleCentroid = j;
				}
			}
			if(!centroidFlag) 
				break;
		}
		curpar = curCentroid;
		if(!centroidFlag)
			curCentroid = possibleCentroid;
		else
			foundCentroid = true; 
	}
	// logic here use only active nodes use curcentroid as root
	isActive[curCentroid] = false; 
	for(int j : adj[curCentroid]) {
		if(isActive[j])
			decompose(j, curlevel + 1);
	}
	level[curCentroid] = curlevel;
}
// global variables
ArrayList<Integer> adj[];
int size[];
int level[];
boolean isActive[];
// to be used when calling decompose
size = new int[n];
level = new int[n];
isActive = new boolean[n];
Arrays.fill(isActive, true);
decompose(0, 0);