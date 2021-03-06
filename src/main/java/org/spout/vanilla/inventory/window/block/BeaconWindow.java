/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
 * Vanilla is licensed under the Spout License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.vanilla.inventory.window.block;

import org.spout.api.entity.Player;
import org.spout.api.inventory.ItemStack;

import org.spout.math.vector.Vector2f;
import org.spout.vanilla.component.block.material.Beacon;
import org.spout.vanilla.component.entity.substance.Item;
import org.spout.vanilla.inventory.block.BeaconInventory;
import org.spout.vanilla.inventory.util.InventoryConverter;
import org.spout.vanilla.inventory.window.Window;
import org.spout.vanilla.inventory.window.WindowType;
import org.spout.vanilla.inventory.window.prop.BeaconProperty;

public class BeaconWindow extends Window {
	private Beacon beacon;
	private BeaconInventory inventory;

	public BeaconWindow(Player owner, BeaconInventory inventory) {
		super(owner, WindowType.BEACON, "Beacon", 1);
		addInventoryConverter(new InventoryConverter(inventory, "0", new Vector2f[0]));
		this.inventory = inventory;
	}

	public BeaconWindow(Player owner, Beacon beacon, BeaconInventory inventory) {
		this(owner, inventory);
		this.beacon = beacon;
	}

	public Beacon getBeacon() {
		return beacon;
	}

	@Override
	public void open() {
		super.open();
		int lvls = beacon.getLevels();
		setProperty(BeaconProperty.LEVELS, lvls);
		if (lvls > 0) {
			setProperty(BeaconProperty.PRIMARY_EFFECT, beacon.getPrimaryEffect().getId());
			setProperty(BeaconProperty.SECONDARY_EFFECT, beacon.getSecondaryEffect().getId());
		}
	}

	@Override
	public void close() {
		for (ItemStack item : inventory) {
			if (item != null) {
				Item.dropNaturally(beacon.getPoint(), item);
			}
		}
		super.close();
	}
}
