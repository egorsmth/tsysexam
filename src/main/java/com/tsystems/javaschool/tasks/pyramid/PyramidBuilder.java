package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if (inputNumbers.contains(null) || inputNumbers.size() == Integer.MAX_VALUE - 1 || !this.isValidNumberOfElements(inputNumbers.size()))
            throw new CannotBuildPyramidException();

        LinkedList<LinkedList<Integer>> pyramid = new LinkedList<>();

        Collections.sort(inputNumbers);
        Iterator<Integer> iter = inputNumbers.iterator();

        while (iter.hasNext()) {
            int size = pyramid.size();
            int nextRowSize = 2 * size + 1;

            boolean isZero = false;
            LinkedList<Integer> nextRow = new LinkedList<>();
            for (int i = 0; i < nextRowSize; i++) {
                if (!iter.hasNext())
                    throw new CannotBuildPyramidException();

                nextRow.add(isZero ? 0 : iter.next());
                isZero = !isZero;
            }

            for (LinkedList<Integer> previousRow : pyramid) {
                previousRow.addFirst(0);
                previousRow.addLast(0);
            }

            pyramid.add(nextRow);
        }

        return pyramid.stream()
                .map(list -> list.stream().mapToInt(i -> i).toArray())
                .toArray(int[][]::new);
    }

    boolean isValidNumberOfElements(int size) {
        int i = 1;
        int acum = 2;
        while (i < size) {
            i += acum;
            acum++;
            if (i==size)
                return true;
        }
        return false;
    }
}
