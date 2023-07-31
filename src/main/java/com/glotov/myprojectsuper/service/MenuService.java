package com.glotov.myprojectsuper.service;

import com.glotov.myprojectsuper.model.MenuItem;

import java.util.List;

public interface MenuService {
    List<MenuItem> findAllMenuItems();

    MenuItem addMenuItem(MenuItem menuItem);

    MenuItem editMenuItem(int menuItemId, MenuItem menuItem);

    void deleteMenuItem(int menuItemId);
}
