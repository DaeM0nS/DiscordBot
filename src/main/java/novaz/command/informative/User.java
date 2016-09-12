package novaz.command.informative;

import novaz.core.AbstractCommand;
import novaz.handler.TextHandler;
import novaz.main.Config;
import novaz.main.NovaBot;
import novaz.util.Misc;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.Optional;

/**
 * !user
 * shows some info about the user
 */
public class User extends AbstractCommand {
	public User(NovaBot b) {
		super(b);
	}

	@Override
	public String getDescription() {
		return "Shows information about the user";
	}

	@Override
	public String getCommand() {
		return "user";
	}

	@Override
	public String[] getUsage() {
		return new String[]{
				"user         //info about you",
				"user @user   //info about @user"
		};
	}

	@Override
	public String[] getAliases() {
		return new String[]{};
	}

	@Override
	public String execute(String[] args, IChannel channel, IUser author) {
		IUser infoUser = null;
		if (args.length == 0) {
			infoUser = author;
		} else if (Misc.isUserMention(args[0])) {
			infoUser = bot.instance.getUserByID(Misc.mentionToId(args[0]));
		}

		if (infoUser != null) {
			StringBuilder sb = new StringBuilder();
			String nickname = infoUser.getName();
			if (!channel.isPrivate()) {
				Optional<String> nicknameForGuild = infoUser.getNicknameForGuild(channel.getGuild());
				if (nicknameForGuild.isPresent()) {
					nickname = nicknameForGuild.get();
				}
			}
			sb.append("Querying for ").append(nickname).append(Config.EOL);
			sb.append(":bust_in_silhouette: User: ").append(infoUser.getName()).append("#").append(infoUser.getDiscriminator()).append(Config.EOL);
			sb.append(":date: Account registered at ").append(infoUser.getCreationDate()).append(Config.EOL);
			sb.append(":id: : ").append(infoUser.getID()).append(Config.EOL);
			System.out.println(infoUser.getAvatar());
			if (!infoUser.getAvatarURL().endsWith("null.jpg")) {
				sb.append(":frame_photo: Avatar: ").append(infoUser.getAvatarURL());
			}
			if (infoUser.isBot()) {
				sb.append("This user is a :robot:").append(Config.EOL);
			}
			return sb.toString();
		}


		return TextHandler.get("command_user_not_found");
	}
}