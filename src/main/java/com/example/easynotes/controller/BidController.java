package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Bid;
import com.example.easynotes.model.ResponseBid;
import com.example.easynotes.model.ResponseData;
import com.example.easynotes.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import java.util.List;

/**
 * @authour created by Eric Nelson 2022.1.7
 **/
@RestController
@CrossOrigin(origins = "http://192.168.104.109:3000")
@RequestMapping("/api")
public class BidController {

    @Autowired
    BidRepository bidRepository;

    @GetMapping("/bids")
    public List<Bid> getAllBids() {
       return bidRepository.findAllBid();
    }

    @PostMapping(value= "/bids")
    public ResponseBid createBid(@Valid Bid bid) {
        if(bid.getUser_name()!=null){
            if(bid.getBid_statement()!=null){
                bidRepository.save(bid);
                return new ResponseBid("200","Success", bid);
            }else {
                return new ResponseBid("407","Not Exists", "bid_statement Not exists");
            }
        }
        else
            return new ResponseBid("407","Not Exists", "username Not exists");
    }

    @GetMapping("/bids/{id}")
    public Bid getBidById(@PathVariable(value = "id") Long BidId) {
        return bidRepository.findById(BidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "id", BidId));
    }

    @PutMapping("/bids/{id}")
    public Bid updateBid(@PathVariable(value = "id") Long Bidid,
                           @Valid  Bid BidDetails) {
        Bid bid = bidRepository.findById(Bidid)
                .orElseThrow(() -> new ResourceNotFoundException("Bid", "id", Bidid));
        bid.setClient_name(BidDetails.getClient_name());
        bid.setUser_name(BidDetails.getUser_name());
        bid.setClient_price(BidDetails.getClient_price());
        bid.setBid_statement(BidDetails.getBid_statement());
        bid.setClient_country(BidDetails.getClient_country());
        bid.setChat(BidDetails.getChat());
        bid.setClient_verify_id(BidDetails.getClient_verify_id());
        bid.setClient_verify_payment(BidDetails.getClient_verify_payment());
        bid.setClient_join_date(BidDetails.getClient_join_date());
        bid.setBid_num(BidDetails.getBid_num());
        bid.setWhen_jobs(BidDetails.getWhen_jobs());
        return BidDetails;
    }

    @DeleteMapping("/bids/{id}")
    public ResponseBid deleteBid(@PathVariable(value = "id") Long bidId) {
//        ResponseBid resdata;
//        Bid bid = bidRepository.findById(bidId)
//                .orElseThrow(() -> new ResourceNotFoundException("Bid", "id", bidId));
//        bid.setDel_flag((long) 1);
//        Bid updatedBid = bidRepository.save(bid);
//        resdata = new ResponseBid("200","Success", updatedBid);
//        return resdata;
        bidRepository.deleteBid(bidId);
        return new ResponseBid("200","wer","");
    }
}
