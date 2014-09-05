package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Agoraphobia")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Agoraphobia extends Card
{
	public static final class AgoraphobiaAbility1 extends StaticAbility
	{
		public AgoraphobiaAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -5/-0.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchanted, -5, -0));
		}
	}

	public static final class AgoraphobiaAbility2 extends ActivatedAbility
	{
		public AgoraphobiaAbility2(GameState state)
		{
			super(state, "(2)(U): Return Agoraphobia to its owner's hand.");
			this.setManaCost(new ManaPool("(2)(U)"));

			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Agoraphobia to its owner's hand."));
		}
	}

	public Agoraphobia(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -5/-0.
		this.addAbility(new AgoraphobiaAbility1(state));

		// (2)(U): Return Agoraphobia to its owner's hand.
		this.addAbility(new AgoraphobiaAbility2(state));
	}
}
