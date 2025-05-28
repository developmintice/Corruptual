package net.developmintice.corruptual.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class DamnedVesselItem extends Item {

    public DamnedVesselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        assert player != null;
        player.sendMessage(Text.of("More... more!"), true);

        return ActionResult.PASS;
    }



}
