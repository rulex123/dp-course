package problems;

import java.util.Arrays;
import java.util.List;

public class IntegerKnapsack {

  /**
   * Given a list of items with weights and values, as well as a maximum weight, find the
   * combination of items with with the greatest value where the sum of the weights is less than
   * the maximum weight. For simplicity, the items are represented through an array with 2
   * elements, where element[0] is the weight, and element[1] is the value.
   */
  public static void main(String[] args) {
    List<Item> items = Arrays.asList(new Item(5, 2), new Item(10, 12),
        new Item(7, 18));
    System.out.println(integerKnapsackIteratively(items, 14));

    items = Arrays.asList(new Item(2, 6), new Item(2, 10),
        new Item(3, 12));
    System.out.println(integerKnapsackIteratively(items, 5));
  }


  // top-down solution w/o memoization
  private static int integerKnapsack(List<Item> items, int maxWeight) {
    return integerKnapsack(items, maxWeight, 0);
  }

  private static int integerKnapsack(List<Item> items, int maxWeight, int itemIndex) {
    if (itemIndex == items.size()) {
      return 0;
    }

    Item item = items.get(itemIndex);
    if (maxWeight - item.weight < 0) {
      // exclude the item if its weight brings us over the max weight limit
      return integerKnapsack(items, maxWeight, itemIndex + 1);
    }

    // otherwise, try excluding and including current item
    return Math.max(integerKnapsack(items, maxWeight - item.weight, itemIndex + 1) +
                    item.value, integerKnapsack(items, maxWeight, itemIndex + 1));
  }

  // top-down solution w memoization
  private static int integerKnapsackWithCache(List<Item> items, int maxWeight) {
    return integerKnapsackWithCache(items, maxWeight, 0, prepareCache(items.size(), maxWeight));
  }

  private static int integerKnapsackWithCache(List<Item> items, int maxWeight, int itemIndex,
                                              int[][] cache) {
    if (cache[itemIndex][maxWeight] == -1) {
      Item item = items.get(itemIndex);

      if (maxWeight - item.weight < 0) {
        // exclude the item if its weight brings us over the max weight limit
        cache[itemIndex][maxWeight] = integerKnapsack(items, maxWeight, itemIndex + 1);
      }

      // otherwise, try excluding and including current item
      cache[itemIndex][maxWeight] =
          Math.max(integerKnapsack(items, maxWeight - item.weight, itemIndex + 1) +
                   item.value, integerKnapsack(items, maxWeight, itemIndex + 1));
    }

    return cache[itemIndex][maxWeight];
  }

  private static int[][] prepareCache(int noOfItems, int maxWeight) {
    int[][] cache = new int[noOfItems + 1][maxWeight + 1];
    for (int[] row : cache) {
      Arrays.fill(row, -1);
    }
    return cache;
  }


  // bottom-up solution that uses iteration
  private static int integerKnapsackIteratively(List<Item> items, int maxWeight) {
    // the rows represent the items, the columns the possible weights
    int[][] dpTable = new int[items.size() + 1][maxWeight + 1];

    for (int row = 1; row < dpTable.length; row++) {
      for (int col = 1; col < dpTable[row].length; col++) {
        Item item = items.get(row - 1);

        if (item.weight > col) {
          // only option is to exclude the item
          dpTable[row][col] = dpTable[row - 1][col];
        } else {
          // try both including and excluding the item, and pick the max
          dpTable[row][col] = Math.max(item.value + dpTable[row - 1][col - item.weight],
              dpTable[row - 1][col]);
        }
      }
    }

    return dpTable[items.size()][maxWeight];
  }


  private static class Item {

    public final int weight;
    public final int value;

    private Item(final int weight, final int value) {
      this.weight = weight;
      this.value = value;
    }
  }

}
