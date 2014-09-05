package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Briarberry Cohort")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.SOLDIER})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class BriarberryCohort extends Card
{
	public static final class Pump extends StaticAbility
	{
		public Pump(GameState state)
		{
			super(state, "Briarberry Cohort gets +1/+1 as long as you control another blue creature.");
			// Dear RulesGuru,
			// Briarberry Cohort naturally has flying.
			// Do not add flying to this effect.
			// You are a moron.
			// Love, RulesGuru

			// gets +1/+1
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			// as long as you control another blue creature
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator blueCreature = Intersect.instance(HasColor.instance(Color.BLUE), CreaturePermanents.instance());
			SetGenerator anotherBlueCreature = RelativeComplement.instance(blueCreature, This.instance());
			this.canApply = Both.instance(this.canApply, Intersect.instance(youControl, anotherBlueCreature));
		}
	}

	public BriarberryCohort(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new Pump(state));
	}
}
