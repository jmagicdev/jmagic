package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vineweft")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class Vineweft extends Card
{
	public static final class VineweftAbility1 extends StaticAbility
	{
		public VineweftAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
		}
	}

	public static final class VineweftAbility2 extends ActivatedAbility
	{
		public VineweftAbility2(GameState state)
		{
			super(state, "(4)(G): Return Vineweft from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(4)(G)"));
			this.activateOnlyFromGraveyard();
			this.addEffect(putIntoHand(ABILITY_SOURCE_OF_THIS, You.instance(), "Return Vineweft from your graveyard to your hand."));
		}
	}

	public Vineweft(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1.
		this.addAbility(new VineweftAbility1(state));

		// (4)(G): Return Vineweft from your graveyard to your hand.
		this.addAbility(new VineweftAbility2(state));
	}
}
