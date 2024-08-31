import java.util.*;

class Solution {
    public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {
        Map<Integer, List<Edge>> g = new HashMap<>();
        boolean[] changed = new boolean[edges.length];
        for(int i = 0; i < edges.length;i++) {
            int[] e = edges[i];
            if(!g.containsKey(e[0])) {
                g.put(e[0], new ArrayList<>());
            }
            if(e[2] == -1) {
                changed[i] = true;
            }
            g.get(e[0]).add(new Edge(e[1], e[2] == -1 ? Integer.MAX_VALUE / 100 : e[2]));
            if(!g.containsKey(e[1])) {
                g.put(e[1], new ArrayList<>());
            }
            g.get(e[1]).add(new Edge(e[0], e[2] == -1 ? Integer.MAX_VALUE / 100: e[2]));
        }

        PriorityQueue<Path> pq = new PriorityQueue<>(Comparator.comparingInt((Path p) -> p.total));
        pq.offer(new Path(source, 0));
        int[] vis = new int[n];
        Arrays.fill(vis, Integer.MAX_VALUE);
        while(!pq.isEmpty()) {
            Path curPath = pq.poll();
            int curNode = curPath.node;
            int curTotal = curPath.total;

            if(vis[curNode] < curTotal) {
                continue;
            }
            vis[curNode] = curTotal;
            for(Edge e : g.get(curNode)) {
                pq.offer(new Path(e.to, curTotal + e.cost));
            }
        }
        if(vis[destination] < target) {
            return new int[][]{};
        }

        g = new HashMap<>();
        for(int[] e : edges) {
            if(!g.containsKey(e[0])) {
                g.put(e[0], new ArrayList<>());
            }
            g.get(e[0]).add(new Edge(e[1], e[2] == -1 ? 1: e[2]));
            if(!g.containsKey(e[1])) {
                g.put(e[1], new ArrayList<>());
            }
            g.get(e[1]).add(new Edge(e[0], e[2] == -1 ? 1: e[2]));
        }

        vis = new int[n];
        Arrays.fill(vis, Integer.MAX_VALUE);
        Path end = null;
        pq = new PriorityQueue<>(Comparator.comparingInt((Path p) -> p.total));
        pq.offer(new Path(source, 0));

        while(!pq.isEmpty()) {
            Path curPath = pq.poll();
            int curNode = curPath.node;
            int curTotal = curPath.total;

            if(vis[curNode] < curTotal) {
                continue;
            }
            vis[curNode] = curTotal;
            if(curNode == destination) {
                end = curPath;
            }
            for(Edge e : g.get(curNode)) {
                Path p = new Path(e.to, curTotal + e.cost);
                p.prev = curPath;
                pq.offer(p);
            }
        }
        //System.out.println(Arrays.toString(vis));
        List<Integer> path = new ArrayList<>();
        //System.out.println(end.node);
        while(end != null) {
            path.add(end.node);
            end = end.prev;
        }
        //System.out.println(path);
        Map<List<Integer>, Integer> map = new HashMap<>();

        for(int i = 0; i < edges.length; i++) {
            List<Integer> e1 = new  ArrayList(List.of(edges[i][0], edges[i][1]));
            List<Integer> e2 = new ArrayList(List.of(edges[i][1], edges[i][0]));
            map.put(e1, i);
            map.put(e2, i);
        }
        List<Integer> used = new ArrayList<>();
        for(int i = path.size()- 1; i > 0; i--){
            int index = map.get( new ArrayList( List.of(path.get(i), path.get(i - 1)) ) );
            used.add(index);
        }
        System.out.println("Used : " + used);
        for(int i = 0; i < edges.length; i++) {
            if(!changed[i] && edges[i][2] == -1) {
                edges[i][2] = Integer.MAX_VALUE;
            }else if(edges[i][2] == -1){
                edges[i][2] = 1;
            }
        }
        int dif =target - vis[destination];
        edges[used.get(0)][2] = dif + 1;
        return edges;
    }
}
class Edge {
    int to;
    int cost;
    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;

    }
}

class Path {
    int node;
    int total;
    Path prev = null;
    Path(int node, int total) {
        this.node = node;
        this.total = total;
    }
}