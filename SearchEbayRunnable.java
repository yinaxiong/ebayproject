/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ebayproject;

import java.util.ArrayList;
import java.util.HashMap;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.Amount;
import com.ebay.services.finding.FindItemsAdvancedRequest;
import com.ebay.services.finding.FindItemsAdvancedResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.ItemFilter;
import com.ebay.services.finding.ItemFilterType;
import com.ebay.services.finding.ListingInfo;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SellingStatus;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author nbevacqu
 */
public class SearchEbayRunnable implements Runnable {

    private HashMap<String, String> searchPatternData;
    private ArrayList<HashMap<String, String>> listOfResults;
    private AtomicInteger numSearchesRemaining;
    private final JList jList1;
    private final JLabel statusLabel;

    public SearchEbayRunnable(HashMap<String, String> searchPatternData, ArrayList<HashMap<String, String>> listOfResults, AtomicInteger numSearchesRemaining, JList jList1, JLabel statusLabel) {
        this.searchPatternData = searchPatternData;
        this.listOfResults = listOfResults;
        this.numSearchesRemaining = numSearchesRemaining;
        this.jList1 = jList1;
        this.statusLabel = statusLabel;
    }
    
    public ItemFilter addItemFilter(ItemFilterType filterType, String value) {
        
        ItemFilter itemfilter = new ItemFilter();
        itemfilter.setName(filterType);
        itemfilter.getValue().add(value);
        
        return itemfilter;
        
    }

    public void run() {

        try {
            ClientConfig config = new ClientConfig();
            config.setApplicationId("TonyMond-5bee-41f6-9f21-424915be5a93");
            FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(config);

            FindItemsAdvancedRequest request = new FindItemsAdvancedRequest();

            if(!searchPatternData.containsKey("keywords") || searchPatternData.get("keywords").isEmpty()) {
                searchPatternData.put("keywords", "test");
            }
            request.setKeywords(searchPatternData.get("keywords"));
            
            PaginationInput pi = new PaginationInput();
            pi.setEntriesPerPage(100);
            pi.setPageNumber(1);
            request.setPaginationInput(pi);
            
            ItemFilter objFilter1 = addItemFilter(ItemFilterType.AVAILABLE_TO,"US");
            ItemFilter objFilter2 = addItemFilter(ItemFilterType.LISTING_TYPE,"All");
            ItemFilter objFilter3 = addItemFilter(ItemFilterType.HIDE_DUPLICATE_ITEMS,"true");
            ItemFilter objFilter4 = addItemFilter(ItemFilterType.CURRENCY,"USD");
            
            
            
            List<ItemFilter> itemFilter = request.getItemFilter();
            itemFilter.add(objFilter1);
            itemFilter.add(objFilter2);
            itemFilter.add(objFilter3);

            List<SearchItem> items = new ArrayList<SearchItem>();
            
            System.out.println("making request ");
            FindItemsAdvancedResponse result = serviceClient.findItemsAdvanced(request);
            System.out.println("Ack = "+result.getAck());
            System.out.println("Found " + result.getSearchResult().getCount() + " items.");
            items.addAll(result.getSearchResult().getItem());
            int totalPages = result.getPaginationOutput().getTotalPages();
            System.out.println("total entries = "+result.getPaginationOutput().getTotalEntries());
            
            // repeat multiple times to get all entries
            for(int i = 1; i < totalPages; i++) {
                pi.setPageNumber(i);
                result = serviceClient.findItemsAdvanced(request);
                items.addAll(result.getSearchResult().getItem());
                
                System.out.println("Ack = "+result.getAck());
                System.out.println("Found " + result.getSearchResult().getCount() + " items.");
                
                if(result.getSearchResult().getItem().isEmpty())
                    break;
            }
            
            
            ArrayList<HashMap<String, String>> listOfResults_temp = new ArrayList<HashMap<String, String>>();
            for (SearchItem item : items) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("name", item.getTitle());
                //hm.put("name","name");
                hm.put("image", item.getGalleryURL());
                System.out.println(item);
                ListingInfo listing_info = item.getListingInfo();
                SellingStatus selling_status = item.getSellingStatus();
                Amount amount = selling_status.getConvertedCurrentPrice();
                //System.out.println("currencyID: "+amount.getCurrencyId());
                //System.out.println("value: "+amount.getValue());
                hm.put("price", ""+amount.getValue()+" "+amount.getCurrencyId());
                hm.put("itemId", item.getItemId());
                hm.put("url", item.getViewItemURL());
                listOfResults_temp.add(hm);
            }
            
            // do filtering of listOfResultsTemp here by itemId
            Helpers.filterEbaySearchResultsByItemFilter(listOfResults_temp);
            
            synchronized(this.listOfResults) {
                this.listOfResults.addAll(listOfResults_temp);
            }
        } catch (Exception e) {
            System.err.println("Oops, something bad happened while trying to run the query...");
            e.printStackTrace();
        } finally {
            int numLeft = this.numSearchesRemaining.decrementAndGet();
            if(numLeft == 0) {
                jList1.setEnabled(true);
                this.statusLabel.setText("");
            }
        }

    }
}