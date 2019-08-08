
public class Main {
	
    public int maxProfit(int[] prices) {
        //minimum profit
        int profit = 0;
        
        //Runtime of O(n^2)
        for(int i = 0; i < prices.length - 1; i++) {
            for(int j = i + 1; j < prices.length; j++) {
                int tempProf = prices[j] - prices[i];
                if(profit < tempProf) {
                    profit = tempProf;
                }
            }
        }
        
        return profit;
    }

	public void run() {
		
		int[] prices = {1,4,2,6,8,4,2,1};
		
		System.out.println(this.maxProfit(prices));
		
	}

}
