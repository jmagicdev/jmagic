package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demonlord of Ashmouth")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DemonlordofAshmouth extends Card
{
	public static final class DemonlordofAshmouthAbility1 extends EventTriggeredAbility
	{
		public DemonlordofAshmouthAbility1(GameState state)
		{
			super(state, "When Demonlord of Ashmouth enters the battlefield, exile it unless you sacrifice another creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory exileIt = exile(ABILITY_SOURCE_OF_THIS, "Exile it");

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			EventFactory sacrificeAnother = sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature");

			this.addEffect(unless(You.instance(), exileIt, sacrificeAnother, "Exile it unless you sacrifice another creature."));
		}
	}

	public DemonlordofAshmouth(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Demonlord of Ashmouth enters the battlefield, exile it unless
		// you sacrifice another creature.
		this.addAbility(new DemonlordofAshmouthAbility1(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
