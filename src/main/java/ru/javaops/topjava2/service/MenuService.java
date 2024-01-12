package ru.javaops.topjava2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.mapper.MenuMapper;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.MenuRequestTo;
import ru.javaops.topjava2.to.MenuResponseTo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper mapper;

    @Transactional
    public MenuResponseTo create(MenuRequestTo menuTo, int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("No restaurant with id = " + restaurantId));

        Menu entity = mapper.requestToMenuEntity(menuTo, restaurant);
        Menu persisted = menuRepository.save(entity);
        return mapper.entityToMenuResponse(persisted);
    }

    @Transactional
    public void update(MenuRequestTo menuRequestTo, int id, int restaurantId) {
        Menu menu = menuRepository.getExisted(id);
        mapper.updateEntity(menu, menuRequestTo);
        menuRepository.save(menu);
    }

    public List<MenuResponseTo> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId).stream()
                .map(mapper::entityToMenuResponse)
                .collect(Collectors.toList());
    }
}
