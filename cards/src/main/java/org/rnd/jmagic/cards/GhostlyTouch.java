package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghostly Touch")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class GhostlyTouch extends Card
{
	public static final class AttackTwiddle extends EventTriggeredAbility
	{
		public AttackTwiddle(GameState state)
		{
			super(state, "Whenever this creature attacks, you may tap or untap target permanent.");
			this.addPattern(whenThisAttacks());
			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			this.addEffect(youMay(tapOrUntap(target, "target permanent")));
		}
	}

	public static final class GhostlyTouchAbility1 extends StaticAbility
	{
		public GhostlyTouchAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"Whenever this creature attacks, you may tap or untap target permanent.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), AttackTwiddle.class));
		}
	}

	public GhostlyTouch(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "Whenever this creature attacks, you may tap or untap target permanent."
		this.addAbility(new GhostlyTouchAbility1(state));
	}
}
