
public class Main {
	
	/**
	 * Definition for singly-linked list.
	 * public class ListNode {
	 *     int val;
	 *     ListNode next;
	 *     ListNode(int x) { val = x; }
	 * }
	 */
	
	
	
	 public class ListNode {
	      int val;
	      ListNode next;
	      ListNode(int x) { val = x; }

	 }
	    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
	        
	        //add those two lists together
	        int carry = 0;

	        //create a next node for the current node of the sum list
	        
	        ListNode num = null;
	        int val1;
	        int val2;
	        ListNode dummy = new ListNode(0);
	        num = dummy;
	        while(l1 != null || l2 != null) {
	            //initial statement
	            val1 = 0;
	            val2 = 0;
	            if(l1 != null) {
	                val1 = l1.val;
	                l1 = l1.next;
	            }
	            if(l2 != null) {
	                val2 = l2.val;
	                l2 = l2.next;
	            }
	            int val = val1 + val2 + carry;
	            if(val >= 10) {
	                carry = 1;
	                val = val%10;
	            }
	            else {
	                carry = 0;
	            }
	            num.next = new ListNode(val);
	            num = num.next;
	            
	        }
	        if(carry == 1) {
	            num.next = new ListNode(1);
	        }
	        return dummy.next; 
	    }


	public String listString(ListNode list) {
		String slist = "";
		while(list!= null) {
			if(list.next == null) {
				slist += Integer.toString(list.val);
			}
			else {
				slist = Integer.toString(list.val) + ", ";
			}
			list = list.next;
		}
		return slist;
	}
	    
	public void run() {
		// You can edit this to make two different list
		ListNode list1 = new ListNode(1);
		list1.next = new ListNode(2);
		ListNode list2 = new ListNode(2);
		list2.next = new ListNode(3);
		
		ListNode add = addTwoNumbers(list1,list2);
		System.out.println(listString(add));
		
	}

}
