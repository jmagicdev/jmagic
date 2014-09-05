package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fists of Ironwood")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class FistsofIronwood extends Card
{
	public static final class FistsofIronwoodAbility2 extends StaticAbility
	{
		public FistsofIronwoodAbility2(GameState state)
		{
			super(state, "Enchanted creature has trample.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public static final class EnchantExplode extends EventTriggeredAbility
	{
		public EnchantExplode(GameState state)
		{
			super(state, "When Fists of Ironwood enters the battlefield, put two 1/1 green Saproling creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			CreateTokensFactory tokens = new CreateTokensFactory(2, 1, 1, "Put two 1/1 green Saproling creature tokens onto the battlefield.");
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.SAPROLING);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public FistsofIronwood(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Fists of Ironwood enters the battlefield, put two 1/1 green
		// Saproling creature tokens onto the battlefield.
		this.addAbility(new EnchantExplode(state));

		// Enchanted creature has trample.
		this.addAbility(new FistsofIronwoodAbility2(state));
	}
}
