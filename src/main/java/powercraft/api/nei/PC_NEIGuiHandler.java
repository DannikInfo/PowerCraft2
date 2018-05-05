package powercraft.api.nei;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import powercraft.api.gres.PC_GresContainerGui;
import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;
import codechicken.nei.api.TaggedInventoryArea;

public class PC_NEIGuiHandler implements INEIGuiHandler {

	@Override
	public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {
		if(gui instanceof PC_GresContainerGui){
			currentVisibility.showNEI = false;
		}
		return currentVisibility;
	}

	@Override
	public Iterable<Integer> getItemSpawnSlots(GuiContainer arg0, ItemStack arg1) {
		return null;
	}

	@Override
	public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {
		return null;
	}

	@Override
	public boolean handleDragNDrop(GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button) {
		return false;
	}

	@Override
	public boolean hideItemPanelSlot(GuiContainer arg0, int arg1, int arg2, int arg3, int arg4) {
		return false;
	}

}
