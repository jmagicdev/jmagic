package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Duty-Bound Dead")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DutyBoundDead extends Card
{
	public static final class DutyBoundDeadAbility1 extends ActivatedAbility
	{
		public DutyBoundDeadAbility1(GameState state)
		{
			super(state, "(3)(B): Regenerate Duty-Bound Dead.");
			this.setManaCost(new ManaPool("(3)(B)"));
			this.addEffect(regenerate(This.instance(), "Regenerate Duty-Bound Dead."));
		}
	}

	public DutyBoundDead(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		// (3)(B): Regenerate Duty-Bound Dead. (The next time this creature
		// would be destroyed this turn, it isn't. Instead tap it, remove all
		// damage from it, and remove it from combat.)
		this.addAbility(new DutyBoundDeadAbility1(state));
	}
}
