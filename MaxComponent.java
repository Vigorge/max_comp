import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxComponent {
    private static void VisitVertex1(Graph g, Graph.Elem v) {
        v.newMark2(1);
        System.out.println("    " + v.getTag() + " [color = red]");
        for (int i : v.getList()) {
            Graph.Elem u = g.getElem(i);
            if (!v.getBuiltRibs().contains(i)) {
                System.out.println("    " + v.getTag() + " -- " + u.getTag() + " [color = red]");
                u.addRib(v.getTag());
            }
            if (u.getMark2() == 0) {
                VisitVertex1(g, u);
            }
        }
        v.newMark2(2);
    }
    private static void DFS(Graph g) {
        for (Graph.Elem v : g.getData()) {
            if (v.getMark2() == 0) {
                VisitVertex2(g, v);
            }
        }
    }
    private static void VisitVertex2(Graph g, Graph.Elem v) {
        v.newMark2(1);
        System.out.println("    " + v.getTag());
        for (int i : v.getList()) {
            Graph.Elem u = g.getElem(i);
            if (!v.getBuiltRibs ().contains(i)) {
                System.out.println("    " + v.getTag() + " -- " + u.getTag());
                u.addRib(v.getTag());
            }
            if (u.getMark2() == 0) {
                VisitVertex2(g, u);
            }
        }
        v.newMark2(2);
    }
    private static ArrayList<int[]> BFS(Graph g, int amount) {
        ArrayList<int[]> chars = new ArrayList<>();
        ArrayDeque<Graph.Elem> q = new ArrayDeque<>();
        for (Graph.Elem w : g.getData()) {
            if (!w.getMark1()) {
                int[] charOfComp = new int[] {0, 0, 0};
                int minVert = amount;
                w.newMark1(true);
                q.addFirst(w);
                while (!q.isEmpty()) {
                    Graph.Elem v = q.pollLast();
                    charOfComp[1] += v.getList().size();
                    charOfComp[0]++;
                    if (v.getTag() < minVert) minVert = v.getTag();
                    for (int i : v.getList()) {
                        Graph.Elem u = g.getElem(i);
                        if (!u.getMark1()) {
                            u.newMark1(true);
                            q.addFirst(u);
                        }
                    }
                }
                charOfComp[1] /= 2;
                charOfComp[2] = minVert;
                chars.add(charOfComp);
            }
        }
        return chars;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int amount = in.nextInt();
        Graph graph = new Graph(amount);
        int ribs = in.nextInt();
        for (int i = 0; i < ribs; i++) {
            int a = in.nextInt(), b = in.nextInt();
            graph.addRib(a, b);
            graph.addRib(b, a);
        }
        ArrayList<int[]> chars = BFS(graph, amount);
        int[] resChar = new int[] {0, 0, 0};
        for (int[] i : chars) {
            if (i[0] > resChar[0]) resChar = i;
            else if (i[0] == resChar[0]) {
                if (i[1] > resChar[1]) resChar = i;
                else if (i[1] == resChar[1]) {
                    if (i[2] < resChar[2]) resChar = i;
                }
            }
        }
        System.out.println("graph {");
        VisitVertex1(graph, graph.getElem(resChar[2]));
        DFS(graph);
        System.out.println("}");
    }
}

class Graph {
    private Elem[] data;
    class Elem {
        private int tag, mark2;
        private ArrayList<Integer> list, builtRibs;
        private boolean mark1;

        Elem(int tag) {
            this.tag = tag;
            list = new ArrayList<>();
            builtRibs = new ArrayList<>();
            mark1 = false;
            mark2 = 0;
        }
        int getTag() {
            return tag;
        }
        ArrayList<Integer> getList() {
            return list;
        }
        ArrayList<Integer> getBuiltRibs() {
            return builtRibs;
        }
        void addRib(int i) {
            builtRibs.add(i);
        }
        boolean getMark1() {
            return mark1;
        }
        void newMark1(boolean mark) {
            this.mark1 = mark;
        }
        int getMark2() {
            return mark2;
        }
        void newMark2(int mark2) {
            this.mark2 = mark2;
        }
    }

    Graph(int amount) {
        data = new Elem[amount];
        for (int i = 0; i < amount; i++) {
            data[i] = new Elem(i);
        }
    }
    void addRib(int start, int dest) {
        data[start].list.add(dest);
    }
    Elem getElem(int i) {
        return data[i];
    }
    Elem[] getData() {
        return data;
    }
}
