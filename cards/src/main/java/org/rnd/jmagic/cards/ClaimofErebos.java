package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Claim of Erebos")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class ClaimofErebos extends Card
{
	public static final class Claiming extends ActivatedAbility
	{
		public Claiming(GameState state)
		{
			super(state, "(1)(B), (T): Target player loses 2 life.");
			this.setManaCost(new ManaPool("(1)(B)"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 2, "Target player loses 2 life."));
		}
	}

	public static final class ClaimofErebosAbility1 extends StaticAbility
	{
		public ClaimofErebosAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"(1)(B), (T): Target player loses 2 life.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), Claiming.class));
		}
	}

	public ClaimofErebos(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has "(1)(B), (T): Target player loses 2 life."
		this.addAbility(new ClaimofErebosAbility1(state));
	}
}
