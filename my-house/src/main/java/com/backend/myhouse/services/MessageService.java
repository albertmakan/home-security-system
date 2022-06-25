package com.backend.myhouse.services;

import com.backend.myhouse.dto.HouseholdMessagesDTO;
import com.backend.myhouse.exception.NotFoundException;
import com.backend.myhouse.model.Device;
import com.backend.myhouse.model.Household;
import com.backend.myhouse.model.Message;
import com.backend.myhouse.model.auth.User;
import com.backend.myhouse.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public List<HouseholdMessagesDTO> findAllForHouseholds(String username, String filter, Date start, Date end) {
        User user = userService.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        List<HouseholdMessagesDTO> householdMessagesDTOList = new ArrayList<>();
        if (user.getHouseholds() == null) return householdMessagesDTOList;

        for (Household household : user.getHouseholds()) {
            if (household.getDevices() == null) continue;
            householdMessagesDTOList.add(new HouseholdMessagesDTO(household.getId(), household.getName(),
                    household.getDevices().stream().collect(Collectors.toMap(d->d.getId().toString(), Device::getName)),
                    messageRepository.findAllByDeviceIdsBetweenDatesWithFilter(
                            start, end, household.getDevices().stream().map(Device::getId).collect(Collectors.toList()), filter)
                    )
            );
        }
        return householdMessagesDTOList;
    }
}
