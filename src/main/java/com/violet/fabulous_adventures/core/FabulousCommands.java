package com.violet.fabulous_adventures.core;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.violet.fabulous_adventures.attachments.FabulousAttachments;
import com.violet.fabulous_adventures.core.FabulousAdventures;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = FabulousAdventures.MODID)
public class FabulousCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("skillpoint")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.literal("add")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes(commandContext -> modifyPoints(commandContext,1)
                                                )
                                        )
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes(commandContext -> modifyPoints(commandContext,-1)
                                                )
                                        )
                                )
                                .then(Commands.literal("reset")
                                        .executes(commandContext -> {
                                            ServerPlayer target = EntityArgument.getPlayer(commandContext, "target");
                                            target.setData(FabulousAttachments.BASE_SKILL_POINTS.get(), 0);
                                            return 1;
                                        })
                                )

                        )
        );
        event.getDispatcher().register(
          Commands.literal("resetskills")
                  .then(Commands.argument("target",EntityArgument.player())
                          .executes(commandContext -> {
                              ServerPlayer target = EntityArgument.getPlayer(commandContext, "target");
                              Set<String> newSet = new HashSet<>();
                              newSet.add("root");
                              target.setData(FabulousAttachments.UNLOCKED_SKILLS.get(), newSet);
                              return 1;
                          })
                  )
        );
    }
    private static int modifyPoints(CommandContext<CommandSourceStack> context, int sign) throws CommandSyntaxException {
        ServerPlayer target = EntityArgument.getPlayer(context, "target");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        int current = target.getData(FabulousAttachments.BASE_SKILL_POINTS.get());
        target.setData(FabulousAttachments.BASE_SKILL_POINTS.get(), Math.max(0, current + amount * sign));
        return 1;
    }
}
