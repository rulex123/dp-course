package problems;

public class MakingChange {

  /**
   * Given an integer representing a specific amount of change and a set of coin sizes, determine
   * the minimum number of coins required to make that amount of change. You may assume there is
   * always a 1 cent coin.
   *
   * (e.g. assuming American coins: 1,5,10 and 25 cents)
   * change(1) = 1 (1)
   * change(6) = 2 (5 + 1)
   * change(47) = 5 (25 + 10 + 10 + 1 + 1)
   */
  public static void main(String[] args) {
    int[] coins = new int[]{ 1, 5, 10, 25 };
    System.out.println(makeChangeIteratively(1, coins));
    System.out.println(makeChangeIteratively(6, coins));
    System.out.println(makeChangeIteratively(47, coins));
  }


  // top-down solution w/o memoization
  private static int makeChange(int change, int[] coins) {
    if (change == 0) {
      return 0; // base case where we stop recursion
    }

    int minNoOfCoins = Integer.MAX_VALUE;
    for (int coin : coins) {
      int remainder = change - coin;
      if (remainder >= 0) {
        int noOfCoins = makeChange(remainder, coins);
        minNoOfCoins = Math.min(minNoOfCoins, noOfCoins);
      }
    }

    return minNoOfCoins + 1;
  }

  // top-down solution with memoization
  private static int makeChangeWithCache(int change, int[] coins) {
    int[] cache = new int[change + 1];
    return makeChangeWithCache(change, coins, cache);
  }

  private static int makeChangeWithCache(int change, int[] coins, int[] cache) {
    if (cache[change] == 0) {
      if (change == 0) {
        return 0; // base case where we stop recursion
      }

      int minNoOfCoins = Integer.MAX_VALUE;
      for (int coin : coins) {
        int remainder = change - coin;
        if (remainder >= 0) {
          int noOfCoins = makeChange(remainder, coins);
          minNoOfCoins = Math.min(minNoOfCoins, noOfCoins);
        }
      }
      cache[change] = minNoOfCoins + 1; // save in cache so that we avoid re-computing every time!
    }

    return cache[change];
  }

  // bottom-up solution that uses iteration
  private static int makeChangeIteratively(int change, int[] coins) {
    int[] table = new int[change + 1]; // this also takes care of setting table[0] to 0

    for (int i = 1; i <= change; i++) {
      int minNoOfCoins = Integer.MAX_VALUE;
      for (int coin : coins) {
        int remainder = i - coin;
        if (remainder >= 0) {
          minNoOfCoins = Math.min(minNoOfCoins, table[remainder]);
        }
      }
      table[i] = minNoOfCoins + 1;
    }

    return table[change];
  }

}



