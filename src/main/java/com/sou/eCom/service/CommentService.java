package com.sou.eCom.service;

import com.sou.eCom.model.Comment;
import com.sou.eCom.model.Customer;
import com.sou.eCom.model.Product;
import com.sou.eCom.model.dto.CommentRequest;
import com.sou.eCom.model.dto.CommentResponse;
import com.sou.eCom.repo.CommentRepo;
import com.sou.eCom.repo.CustomerRepo;
import com.sou.eCom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ProductRepo productRepo;

    public CommentResponse addComment(long productId, CommentRequest commentRequest, MultipartFile img) throws IOException {
        Comment comment = new Comment();
        Product product = productRepo.findByProductId(productId).orElseThrow(()->new RuntimeException("product not found"));
        Customer customer =customerRepo.findByCustomerId(commentRequest.customerId()).orElseThrow(()->new RuntimeException("customer not found"));



            comment.setProduct(product);
            comment.setCustomer(customer);
            comment.setCommentBody(commentRequest.desc());
            comment.setRating(commentRequest.rating());
            comment.setCreatedDate(LocalDate.now());
        if(img != null)
        {
            comment.setImageName(img.getOriginalFilename());
            comment.setImageType(img.getContentType());
            comment.setImageData(img.getBytes());
        }
        commentRepo.save(comment);
        return toCommentResponse(comment);


    }

    public CommentResponse updateComment(long commentId, CommentRequest commentRequest, MultipartFile img) throws IOException {
            Comment oldComment= commentRepo.findByCommentId(commentId).orElseThrow(()->new RuntimeException("comment not found"));
            oldComment.setRating(commentRequest.rating());
            oldComment.setCommentBody(commentRequest.desc());
        if(img != null)
        {
            oldComment.setImageName(img.getOriginalFilename());
            oldComment.setImageType(img.getContentType());
            oldComment.setImageData(img.getBytes());
        }

        commentRepo.save(oldComment);
        return toCommentResponse(oldComment);
    }

    private CommentResponse toCommentResponse( Comment oldComment) throws IOException {

        CommentResponse commentResponse = new CommentResponse(
                oldComment.getId(),
                oldComment.getCreatedDate(),
                oldComment.getCommentBody(),
                oldComment.getRating(),
                oldComment.getCustomer().getCustomerId(),
                oldComment.getCustomer().getCustomerName(),
                oldComment.getImageName(),
                oldComment.getImageType(),
                oldComment.getImageData()
        );

        return commentResponse;
    }

    public List<CommentResponse> getProductComments(long productId) throws IOException {
        List<Comment>comments=  commentRepo.findByProductId(productId);
        List<CommentResponse>commentResponses=new ArrayList<>();
        for( Comment comment:comments){
            commentResponses.add(toCommentResponse(comment));

        }
        return commentResponses;
    }

    public void deleteComment(long commentId) {
        Comment com=commentRepo.findByCommentId(commentId).orElseThrow(()->new RuntimeException("comment not found"));

        commentRepo.deleteById(commentId);
    }

    public CommentResponse getComment(Long commentId) throws IOException {
        Comment comment= commentRepo.findByCommentId(commentId).orElseThrow(()->new RuntimeException("comment not found"));
        return toCommentResponse(comment);
    }

    public List<CommentResponse> getCustomerComment(Long customerId) throws IOException {
        List<Comment>comments= commentRepo.findByCustomerCustomerId(customerId);
        List<CommentResponse>commentResponses=new ArrayList<>();
        for( Comment comment:comments){
            commentResponses.add(toCommentResponse(comment));

        }
        return commentResponses;
    }
}
