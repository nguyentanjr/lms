package com.example.demo.Controller;

import com.example.demo.Model.Card;
import com.example.demo.Model.Student;
import com.example.demo.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping("/retrieveCardInfoById/{id}")
    public Card retrieveCardInfo(@PathVariable long id) {
        return cardService.findCardById(id);
    }

    @GetMapping("/retrieveCardInfoByStudentId")
    public Card retrieveCardInfoByStudentId(@RequestParam long student_id) {
        return cardService.findCardByStudentId(student_id);
    }

//    @DeleteMapping("/deleteAllCards")
//    ResponseEntity<Void> deleteAllCards() {
//        cardService.deleteAllCards();
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/deleteCardById/{cardId}")
//    ResponseEntity<Void> deleteCardById(@PathVariable long cardId) {
//        cardService.deleteCardById(cardId);
//        return ResponseEntity.noContent().build();
//    }
}
