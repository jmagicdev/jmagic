package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Greenhilt Trainee")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.WARRIOR})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class GreenhiltTrainee extends Card
{
	public static final class GreenhiltTraineeAbility0 extends ActivatedAbility
	{
		public GreenhiltTraineeAbility0(GameState state)
		{
			super(state, "(T): Target creature gets +4/+4 until end of turn. Activate this ability only if Greenhilt Trainee's power is 4 or greater.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +4, +4, "Target creature gets +4/+4 until end of turn."));
			this.addActivateRestriction(Intersect.instance(Between.instance(null, 3), PowerOf.instance(ABILITY_SOURCE_OF_THIS)));
		}
	}

	public GreenhiltTrainee(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (T): Target creature gets +4/+4 until end of turn. Activate this
		// ability only if Greenhilt Trainee's power is 4 or greater.
		this.addAbility(new GreenhiltTraineeAbility0(state));
	}
}
