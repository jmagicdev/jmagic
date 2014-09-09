package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thundermaw Hellkite")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class ThundermawHellkite extends Card
{
	public static final class ThundermawHellkiteAbility2 extends EventTriggeredAbility
	{
		public ThundermawHellkiteAbility2(GameState state)
		{
			super(state, "When Thundermaw Hellkite enters the battlefield, it deals 1 damage to each creature with flying your opponents control. Tap those creatures.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator thoseCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffect(permanentDealDamage(1, thoseCreatures, "It deals 1 damage to each creature with flying your opponents control."));
			this.addEffect(tap(thoseCreatures, "Tap those creatures."));
		}
	}

	public ThundermawHellkite(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Thundermaw Hellkite enters the battlefield, it deals 1 damage to
		// each creature with flying your opponents control. Tap those
		// creatures.
		this.addAbility(new ThundermawHellkiteAbility2(state));
	}
}
