package com.backend.myhouse.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.myhouse.dto.HouseholdMessagesDTO;
import com.backend.myhouse.model.Device;
import com.backend.myhouse.model.Household;
import com.backend.myhouse.model.Message;
import com.backend.myhouse.model.auth.User;
import com.backend.myhouse.repository.MessageRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public List<HouseholdMessagesDTO> findAllForHouseholds(String username) {
        User user = userService.findByUsername(username).orElse(null);

        List<HouseholdMessagesDTO> householdMessages = new ArrayList<HouseholdMessagesDTO>();
        for (Household household : user.getHouseholds()) {
            HouseholdMessagesDTO householdMessagesDTO = new HouseholdMessagesDTO(household.getId(), household.getName(),
                    new ArrayList<Message>());
            if (household.getDevices() == null) {
                continue;
            }
            for (Device device : household.getDevices()) {
                householdMessagesDTO.getMessages().addAll(messageRepository.findAllByDevice_Id(device.getId()));
            }
            householdMessages.add(householdMessagesDTO);
        }

        return householdMessages;
    }

}
