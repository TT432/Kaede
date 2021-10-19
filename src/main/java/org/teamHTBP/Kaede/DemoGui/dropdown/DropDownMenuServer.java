package org.teamHTBP.Kaede.DemoGui.dropdown;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DustW
 *
 * 数据类, 用来保存 DropDownMenu 在服务端的数据
 */
public class DropDownMenuServer implements INBTSerializable<CompoundNBT> {
    /** 一套 menu 的标识符 */
    private final ResourceLocation id;
    private final NonNullList<ItemStack> slots;

    public DropDownMenuServer(ResourceLocation id, int size) {
        this.id = id;

        slots = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public ResourceLocation getId() {
        return id;
    }

    public ItemStack getItemFromSlot(int index) {
        return slots.get(index);
    }

    public void setItem(int index, ItemStack context) {
        slots.set(index, context);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT result = new CompoundNBT();
        ListNBT list = new ListNBT();
        slots.forEach(itemStack -> list.add(itemStack.serializeNBT()));
        result.put("list", list);
        return result;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        slots.clear();
        List<ItemStack> items = nbt.getList("list", 10)
                .stream().map(item -> ItemStack.read((CompoundNBT) item)).collect(Collectors.toList());

        for (int i = 0; i < items.size(); i++) {
            slots.set(i, items.get(i));
        }
    }
}
