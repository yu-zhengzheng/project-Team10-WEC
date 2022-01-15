import java.util.Scanner;

/**
 * @author Zhengzheng Yu
 */
public class Main {
    public static int[][] map;  //object that's used to store the map
    public static boolean[][] visited;
    public static int height;   //height of the map
    public static int width;    //width of the map
    public static int[][] list; //list storing the path nodes between 2 nodes
    public static int stops;    //number of stops beside start and end

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Map generation and display
        System.out.println("Input map height and width:");
        height = sc.nextInt();
        width = sc.nextInt();
        map = new int[height][width];
        visited = new boolean[height][width];
        //Generate the map
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int type = (int) (Math.random() * 20);
                if (type < 3)
                    map[i][j] = 0;  //2/20 of the map will be obstacles
                else map[i][j] = 1; //The rest will be roads
            }

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) visited[i][j] = false;

        //Display the map
        System.out.println("The map:\nObstacles are denoted by o");
        draw();

        //Record the start, destination and waypoints
        System.out.println("Number of stops:");
        stops = sc.nextInt();
        list = new int[stops + 2][2];
        System.out.println("Top left is 0 0, below it is 1 0");
        System.out.println("Start coordinate:");
        list[0][0] = sc.nextInt();
        list[0][1] = sc.nextInt();
        for (int i = 1; i < stops + 1; i++) {
            System.out.println("Stop " + i + " coordinate:");
            list[i][0] = sc.nextInt();
            list[i][1] = sc.nextInt();
        }
        System.out.println("End coordinate:");
        list[stops + 1][0] = sc.nextInt();
        list[stops + 1][1] = sc.nextInt();

        //Find the path
        //In a real carnival a path can always be found so the situation where no path can be found is not taken care of
        System.out.println("Coordinates of all nodes in the path:");
        for (int i = 0; i <= stops; i++) {
            for (int h = 0; h < height; h++)
                for (int w = 0; w < width; w++) visited[h][w] = false;
            //Find the path between 2 waypoints
            Node[] path = route(list[i][0], list[i][1], list[i + 1][0], list[i + 1][1], 18);  // try 16 first then add 1 each time
            //Display the coordinates of all nodes in the path
            for (int j = path.length - 1; j > -1; j--) {
                System.out.println(path[j].getI() + " " + path[j].getJ());
                map[path[j].getI()][path[j].getJ()] = 2;
            }
        }
        System.out.println(list[stops + 1][0] + " " + list[stops + 1][1]);
        map[list[stops + 1][0]][list[stops + 1][1]] = 2;

        //Draw the map
        System.out.println("Visual representation of the path:\nVisited nodes are denoted by #");
        draw();

    }

    /**
     * Draws the map at the console
     */
    private static void draw() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[i][j] == 0)
                    System.out.print("o "); //o for obstacles
                else if (map[i][j] == 1)
                    System.out.print("+ "); //+ for roads
                else System.out.print("# ");//# for path
            }
            System.out.println();
        }
    }

    /**
     * Finds the path between 2 nodes
     *
     * @param si    the i coordinate of the starting node
     * @param sj    the j coordinate of the ending node
     * @param ei    the i coordinate of the starting node
     * @param ej    the j coordinate of the ending node
     * @param steps the maximum allowed number of recursions, <18 recommended
     * @return a Node array containing all the nodes in the partial path
     */
    private static Node[] route(int si, int sj, int ei, int ej, int steps) {
        //return false if start is out of bounds of the map or is obstacle
        if (si < 0 || si == height || sj < 0 || sj == width || steps == 0 || map[si][sj] == 0 || visited[si][sj])
            return null;
        else {
            //visited[si][sj]=true;
            //System.out.println(si+ " "+sj + "is true");
            Node[] path;
            //if start and end are neighbors return start as path
            if (si == ei && (sj == ej + 1 || sj == ej - 1) || (sj == ej && (si == ei + 1 || si == ei - 1))) {
                path = new Node[1];
                path[0] = new Node(si, sj);
                //else try to find the path from the 4 neighbors of start
            } else {
                Node[] path1 = route(si + 1, sj, ei, ej, steps - 1); //path from the node below start
                Node[] path2 = route(si - 1, sj, ei, ej, steps - 1); //path from the node above start
                Node[] path3 = route(si, sj + 1, ei, ej, steps - 1); //path from the node to the right of start
                Node[] path4 = route(si, sj - 1, ei, ej, steps - 1); //path from the node to the left of start
                //store the shortest path in tmp
                Node[] tmp = min(min(path1, path2), min(path3, path4));
                //if no path is found return null
                if (tmp == null) return null;
                    //if path is found concatenate start to the path
                else {
                    path = new Node[tmp.length + 1];
                    System.arraycopy(tmp, 0, path, 0, tmp.length);
                    path[tmp.length] = new Node(si, sj);
                }
            }
            return path;
        }
    }

    /**
     * Finds the shorter path
     *
     * @param p1 path 1
     * @param p2 path 2
     * @return the shorter path
     */
    private static Node[] min(Node[] p1, Node[] p2) {
        //if either path 1 or path 2 is empty, return the other
        if (p1 == null) return p2;
        else if (p2 == null) return p1;
            //else return the shorter of the 2
        else if (p1.length < p2.length) return p1;
        else return p2;
    }
}
