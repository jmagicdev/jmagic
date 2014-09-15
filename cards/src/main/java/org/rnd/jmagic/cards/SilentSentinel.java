package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silent Sentinel")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHON})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class SilentSentinel extends Card
{
	public static final class SilentSentinelAbility1 extends EventTriggeredAbility
	{
		public SilentSentinelAbility1(GameState state)
		{
			super(state, "Whenever Silent Sentinel attacks, you may return target enchantment card from your graveyard to the battlefield.");
			this.addPattern(whenThisAttacks());

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.ENCHANTMENT), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target enchantment card from your graveyard"));
			this.addEffect(youMay(putOntoBattlefield(target, "Return target enchantment card from your graveyard to the battlefield")));
		}
	}

	public SilentSentinel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Silent Sentinel attacks, you may return target enchantment
		// card from your graveyard to the battlefield.
		this.addAbility(new SilentSentinelAbility1(state));
	}
}
