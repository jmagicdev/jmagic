package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Verdant Embrace")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class VerdantEmbrace extends Card
{
	public static final class MakeSaprolings extends EventTriggeredAbility
	{
		public MakeSaprolings(GameState state)
		{
			super(state, "At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield.");
			this.addPattern(atTheBeginningOfEachUpkeep());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Saproling creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.SAPROLING);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class Embracing extends StaticAbility
	{
		public Embracing(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3 and has \"At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield.\"");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +3, +3));
			this.addEffectPart(addAbilityToObject(enchantedCreature, MakeSaprolings.class));
		}
	}

	public VerdantEmbrace(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +3/+3 and has
		// "At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield."
		this.addAbility(new Embracing(state));
	}
}
