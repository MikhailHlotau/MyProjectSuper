package com.glotov.myprojectsuper.service.impl;


import com.glotov.myprojectsuper.model.MenuItem;
import com.glotov.myprojectsuper.repository.MenuItemRepository;
import com.glotov.myprojectsuper.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> findAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem addMenuItem(MenuItem menuItem) {
        // Дополнительные проверки и логика, если необходимо
        return menuItemRepository.save(menuItem);
    }

    public MenuItem editMenuItem(int menuItemId, MenuItem menuItem) {
        // Проверяем, существует ли блюдо с указанным menuItemId
        MenuItem existingMenuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Блюдо с указанным ID не найдено."));

        // Обновляем данные существующего блюда
        existingMenuItem.setName(menuItem.getName());
        existingMenuItem.setDescription(menuItem.getDescription());
        existingMenuItem.setPrice(menuItem.getPrice());

        // Сохраняем обновленное блюдо
        return menuItemRepository.save(existingMenuItem);
    }

    public void deleteMenuItem(int menuItemId) {
        // Проверяем, существует ли блюдо с указанным menuItemId
        MenuItem existingMenuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Блюдо с указанным ID не найдено."));

        // Удаляем блюдо
        menuItemRepository.delete(existingMenuItem);
    }
}
