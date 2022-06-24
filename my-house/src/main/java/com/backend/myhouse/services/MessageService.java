package com.backend.myhouse.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public List<HouseholdMessagesDTO> findAllForHouseholds(String username, String filter, String start, String end)
            throws ParseException {
        User user = userService.findByUsername(username).orElse(null);
        Date from = start == "" ? new Date(345423600000l) : new SimpleDateFormat("yyyy-MM-dd").parse(start);
        Date to = end == "" ? new Date() : new SimpleDateFormat("yyyy-MM-dd").parse(end);

        List<HouseholdMessagesDTO> householdMessages = new ArrayList<HouseholdMessagesDTO>();
        for (Household household : user.getHouseholds()) {
            HouseholdMessagesDTO householdMessagesDTO = new HouseholdMessagesDTO(household.getId(), household.getName(),
                    new ArrayList<Message>());
            if (household.getDevices() == null) {
                continue;
            }
            for (Device device : household.getDevices()) {
                for (Message message : messageRepository.findAllByDevice_Id(device.getId())) {
                    if (message.getMessage().toLowerCase().contains(filter.toLowerCase())
                            && message.getTimestamp().after(from) && message.getTimestamp().before(to))
                        householdMessagesDTO.getMessages().add(message);
                }
            }
            householdMessages.add(householdMessagesDTO);
        }

        return householdMessages;
    }

}
