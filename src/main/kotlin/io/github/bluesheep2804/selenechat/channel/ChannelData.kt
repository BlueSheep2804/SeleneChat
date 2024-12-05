package io.github.bluesheep2804.selenechat.channel

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.util.ComponentSerializer
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import java.util.*

@Serializable
data class ChannelData(
        val name: String,
        @Serializable(with = ComponentSerializer::class)
        val displayName: Component = Component.text(name),
        val playerList: MutableList<String> = mutableListOf()
) {
    fun join(player: SeleneChatPlayer): Either<ChannelJoinError, SeleneChatPlayer> {
        if (playerList.contains(player.uniqueId.toString())) return ChannelJoinError.AlreadyJoins.left()
        playerList.add(player.uniqueId.toString())
        SeleneChat.channelManager.save(this)
        return player.right()
    }

    fun leave(player: SeleneChatPlayer): Either<ChannelLeaveError, SeleneChatPlayer> {
        if (!playerList.contains(player.uniqueId.toString())) return ChannelLeaveError.NotInChannel.left()
        playerList.remove(player.uniqueId.toString())
        SeleneChat.channelManager.save(this)
        return player.right()
    }

    fun sendMessage(text: Component) {
        playerList.forEach {
            SeleneChat.plugin.getPlayer(UUID.fromString(it)).sendMessage(text)  // TODO: プレイヤーのキャッシュをするべきな気がする
        }
    }

    sealed interface ChannelJoinError {
        object AlreadyJoins: ChannelJoinError
    }

    sealed interface ChannelLeaveError {
        object NotInChannel: ChannelLeaveError
    }
}
