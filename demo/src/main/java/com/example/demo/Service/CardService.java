package com.example.demo.Service;

import com.example.demo.Model.Card;
import com.example.demo.Model.CardStatus;
import com.example.demo.Model.Student;
import com.example.demo.Respository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    public Card findCardById(long cardId) {
        return cardRepository.findById(cardId);
    }

    public Card findCardByStudentId(long student_id) {
        return cardRepository.findByStudentId(student_id);
    }
}
