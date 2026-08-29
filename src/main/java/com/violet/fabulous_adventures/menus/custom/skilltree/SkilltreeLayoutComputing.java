package com.violet.fabulous_adventures.menus.custom.skilltree;

import java.util.*;

public class SkilltreeLayoutComputing {

    public record NodePosition(double x, int y){
    }
    private static final int X_OFFSET = 30;
    private static final int Y_OFFSET = 30;
    private static final int X_ANCHOR = 0;
    private static final int Y_ANCHOR = 0;

    public static int assignSlot(String nodeId, Map<String, Integer> slots, int[] nextLeafslot) {
        List<String> children = SkilltreeStructure.NODE_CHILDREN.get(nodeId);
        if (children == null) {
            slots.put(nodeId, nextLeafslot[0]);
            nextLeafslot[0]++;
            return nextLeafslot[0]-1;
        }else{
            //case of a parent having children
            int sum = 0;
            for (String child : children) {
                sum += assignSlot(child, slots, nextLeafslot);
            }
            int parentX = sum/children.size();
            slots.put(nodeId, parentX);
            return parentX;
        }
    }
    public static void calculateNodePositions(String nodeId, int row, Map<String, Integer> slots, Map<String, NodePosition> nodePositions) {
        double x = slots.get(nodeId);
        int y = row;
        List<String> children = SkilltreeStructure.NODE_CHILDREN.get(nodeId);
        nodePositions.put(nodeId, new NodePosition(x * X_OFFSET + X_ANCHOR, y * Y_OFFSET + Y_ANCHOR));
        if (children == null) {
            return;
        }else{
            for (String child : children) {
                calculateNodePositions(child,y+1, slots, nodePositions);
            }
        }
    }
    public static Map<String, NodePosition> computeLayout() {
        Map<String, Integer> slots = new HashMap<>();
        int[] nextLeafSlot = {0};
        Map<String, NodePosition> nodePositions = new HashMap<>();

        List<String> roots = new ArrayList<>();
        for (String nodeId : SkilltreeStructure.NODE_PARENTS.keySet()) {
            if (SkilltreeStructure.NODE_PARENTS.get(nodeId).equals("root")) {
                roots.add(nodeId);
            }
        }

        for (String root : roots) {
            assignSlot(root, slots, nextLeafSlot);
        }

        for (String root : roots) {
            calculateNodePositions(root, 0, slots, nodePositions);
        }

        return nodePositions;
    }
}
