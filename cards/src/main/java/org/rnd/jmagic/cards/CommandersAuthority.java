package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Commander's Authority")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class CommandersAuthority extends Card
{
	public static final class BabyBoomers extends EventTriggeredAbility
	{
		public BabyBoomers(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a 1/1 white Human creature token onto the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Human creature token onto the battlefield.");
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.HUMAN);
			this.addEffect(factory.getEventFactory());
		}
	}

	public static final class CommandersAuthorityAbility1 extends StaticAbility
	{
		public CommandersAuthorityAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"At the beginning of your upkeep, put a 1/1 white Human creature token onto the battlefield.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), BabyBoomers.class));
		}
	}

	public CommandersAuthority(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "At the beginning of your upkeep, put a 1/1 white Human creature token onto the battlefield."
		this.addAbility(new CommandersAuthorityAbility1(state));
	}
}
