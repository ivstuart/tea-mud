/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.admin.AdminCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.config.ChannelData;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AuctionItem extends AdminCommand implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private Item item;
    private SomeMoney currentBid;
    private Mob highestBidder;
    private Mob seller;
    private int countDown;
    private ScheduledExecutorService scheduledExecutorService;


    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        if (!mob.getRoom().isAuctionHouse()) {
            mob.out("You MUST be in an auction house in order to auction");
            return;
        }

        if (mob.getFight().isEngaged() || mob.getFight().isFighting()) {
            mob.out("You can not auction while engaged in fighting!");
            return;
        }

        if (mob.getInventory().getPurse().getValue() < 500) {
            mob.out("You do not have sufficient funds to auction an item 500 copper required");
            return;
        }

        Item item = mob.getInventory().getItems().get(input);

        if (item == null) {
            mob.out("You do not have a " + input + " in your inventory");
            return;
        }

        AuctionItem auctionItem = World.getAuction(mob.getName());

        if (auctionItem == null) {
            auctionItem = new AuctionItem();
        }

        if (auctionItem.getScheduledExecutorService() != null
                && !auctionItem.getScheduledExecutorService().isTerminated()) {
            mob.out("Auction still in progress please wait before starting a new one");
            return;
        }

        SomeMoney cash = mob.getInventory().getPurse().removeAndConvert(500);
        if (cash == null) {
            mob.out("You do not have sufficient funds to auction an item");
            return;
        }
        mob.getInventory().setPurse(cash);

        // seller = mob;
        auctionItem.setSeller(mob);

        mob.getInventory().remove(item);

        // item = item;
        auctionItem.setItem(item);

        // countDown = 10;
        auctionItem.setCountDown(10);

        if (auctionItem.getScheduledExecutorService() == null || auctionItem.getScheduledExecutorService().isTerminated()) {
            // scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            auctionItem.setScheduledExecutorService(Executors.newSingleThreadScheduledExecutor());
        }

        auctionItem.schedule();

        World.registerAuction(mob, auctionItem);

    }

    private void schedule() {
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
    }

    private ExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    private void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void run() {
        countDown--;

        if (currentBid != null && highestBidder != null) {
            World.out("<" + countDown + "> <" + seller.getName() + "> Item " + item.getName() + " current bidding is <" + highestBidder.getName() + "> " + currentBid, seller.isGood(), ChannelData.AUCTION);
        } else {
            World.out("<" + countDown + "> <" + seller.getName() + "> Item " + item.getName(), seller.isGood(), ChannelData.AUCTION);
        }

        if (countDown <= 0) {
            finishAuction();
        }

    }

    public void finishAuction() {
        countDown = -1;
        scheduledExecutorService.shutdown();
        World.removeAuction(seller);

        if (highestBidder == null) {
            seller.out("There were no bids on your item you are still charged for listing the item");
            seller.getInventory().add(item);
            return;
        }

        highestBidder.getInventory().add(item);
        highestBidder.out("Well done you win the auction and get the item " + item.getBrief());
        seller.getInventory().add(currentBid);
        seller.out("The following coins have been added to your purse " + currentBid);

        currentBid = null;
        highestBidder = null;

    }

    public synchronized boolean bid(Mob bidder, SomeMoney money) {

        if (seller.isGood() != bidder.isGood()) {
            bidder.out("You are not allowed to bid on opposite teams auctions");
            return false;
        }

        if (bidder == seller) {
            bidder.out("You can not bid on your own items in the auction");
            return false;
        }

        if (currentBid == null || money.getValue() > currentBid.getValue()) {

            if (highestBidder != null) {
                highestBidder.getInventory().add(currentBid);
                highestBidder.out("Your bid " + currentBid + " is returned to you");
            }
            currentBid = money;
            highestBidder = bidder;
            countDown = 3;
            return true;
        }
        return false;
    }

    public void setSeller(Mob seller) {
        this.seller = seller;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
