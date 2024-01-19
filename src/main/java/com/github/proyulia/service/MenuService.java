package com.github.proyulia.service;

import com.github.proyulia.error.DeletionNotAllowedException;
import com.github.proyulia.error.ErrorType;
import com.github.proyulia.error.NotFoundException;
import com.github.proyulia.mapper.MenuMapper;
import com.github.proyulia.model.Menu;
import com.github.proyulia.model.Restaurant;
import com.github.proyulia.repository.MenuRepository;
import com.github.proyulia.repository.RestaurantRepository;
import com.github.proyulia.to.MenuRequestTo;
import com.github.proyulia.to.MenuResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        Menu persisted = menuRepository.save(mapper.requestToMenuEntity(menuTo, restaurant));
        return mapper.entityToMenuResponse(persisted);
    }

    @Transactional
    public void update(MenuRequestTo menuRequestTo, int id, int restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        mapper.updateEntity(menu, menuRequestTo);
        menuRepository.save(menu);
    }

    public List<MenuResponseTo> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title))
                .stream()
                .map(mapper::entityToMenuResponse)
                .collect(Collectors.toList());
    }

    public MenuResponseTo get(int id, int restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        return mapper.entityToMenuResponse(menu);
    }

    public void delete(int id, int restaurantId) {
        isDeletionAllowed(id);
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND.title));
        menuRepository.deleteExistedByIdAndRestaurantId(id, restaurantId);
    }

    private void isDeletionAllowed(int id) {
        Menu menu = menuRepository.getExisted(id);
        if (menu.getDate().isBefore(LocalDate.now())) {
            throw new DeletionNotAllowedException();
        }
    }
}
