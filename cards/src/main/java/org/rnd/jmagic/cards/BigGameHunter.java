package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Big Game Hunter")
@Types({Type.CREATURE})
@SubTypes({SubType.REBEL, SubType.ASSASSIN, SubType.HUMAN})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BigGameHunter extends Card
{
	public static final class BigGameHunterAbility0 extends EventTriggeredAbility
	{
		public BigGameHunterAbility0(GameState state)
		{
			super(state, "When Big Game Hunter enters the battlefield, destroy target creature with power 4 or greater. It can't be regenerated.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator targetable = Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(4, null)));
			SetGenerator target = targetedBy(this.addTarget(targetable, "target creature with power 4 or greater"));
			this.addEffects(bury(this, target, "Destroy target creature with power 4 or greater. It can't be regenerated."));
		}
	}

	public BigGameHunter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Big Game Hunter enters the battlefield, destroy target creature
		// with power 4 or greater. It can't be regenerated.
		this.addAbility(new BigGameHunterAbility0(state));

		// Madness (B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Madness(state, "(B)"));
	}
}
