package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Viral Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ViralDrake extends Card
{
	public static final class ViralDrakeAbility2 extends ActivatedAbility
	{
		public ViralDrakeAbility2(GameState state)
		{
			super(state, "(3)(U): Proliferate.");
			this.setManaCost(new ManaPool("(3)(U)"));
			this.addEffect(proliferate());
		}
	}

	public ViralDrake(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// (3)(U): Proliferate. (You choose any number of permanents and/or
		// players with counters on them, then give each another counter of a
		// kind already there.)
		this.addAbility(new ViralDrakeAbility2(state));
	}
}
